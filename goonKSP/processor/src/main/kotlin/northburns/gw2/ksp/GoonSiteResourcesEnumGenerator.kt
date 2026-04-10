package northburns.gw2.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.Dynamic
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo
import java.nio.file.Path
import java.util.*
import kotlin.io.path.invariantSeparatorsPathString
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.relativeTo
import kotlin.io.path.walk

class GoonSiteResourcesEnumGenerator(
    private val projectDir: Path,
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    private var invoked = false

    init {
        println("GoonSiteResourcesEnumGenerator, read dir: $projectDir")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Good to know: https://github.com/google/ksp/issues/797
        if (invoked) return emptyList()
        invoked = true

        val originatingFiles = resolver.getAllFiles().toList()

        val entriesByDir: MutableMap<Path, MutableList<Path>> = mutableMapOf()

        val resourcesDir = projectDir.resolve(resourcesDir)
        resourcesDir.walk().forEach { file ->
            entriesByDir
                .getOrPut(file.parent) { mutableListOf() }
                .add(file)
        }
        entriesByDir.forEach { (dir, files) ->
            if (resourcesDir == dir) return@forEach
            val fileName = dir.relativeTo(resourcesDir).joinToString(
                prefix = "Res_",
                separator = "_",
            )
            val fileSpec = FileSpec.builder(packageName, fileName)
            val enumClass = TypeSpec.enumBuilder(fileName)
                .addSuperinterface(ClassName("northburns.gw2.ksp", "SiteResource"))
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter(
                            name = "res",
                            type = Dynamic,
                        )
                        .build()
                )
                .addProperty(
                    PropertySpec
                        .builder(
                            name = "res",
                            type = Dynamic,
                            KModifier.OVERRIDE,
                        )
                        .initializer("res")
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec.builder(Suppress::class)
                        .addMember("%S", "ClassName")
                        .build()
                )

            files.forEach { file ->
                val propertyName = "r" + UUID.randomUUID().toString().replace("-", "")

                fileSpec.addProperty(
                    PropertySpec
                        .builder(
                            propertyName,
                            Dynamic,
                            KModifier.EXTERNAL,
                            KModifier.PRIVATE,
                        )
                        .addAnnotation(
                            AnnotationSpec
                                .builder(ClassName("kotlin.js", "JsModule"))
                                .addMember("import = %S", file.relativeTo(resourcesDir).invariantSeparatorsPathString)
                                .build()
                        ).addAnnotation(
                            AnnotationSpec
                                .builder(ClassName("kotlin.js", "JsNonModule"))
                                .build()
                        )
                        .build()
                )
                val name = file.nameWithoutExtension
                enumClass.addEnumConstant(
                    name, TypeSpec.anonymousClassBuilder()
                        .addSuperclassConstructorParameter(propertyName)
                        .build()
                )
            }

            fileSpec
                .addType(enumClass.build())
                .build()
                .writeTo(codeGenerator, false, originatingKSFiles = originatingFiles)
        }

        return emptyList()
    }

    companion object {
        private const val packageName = "northburns.gw2.site.generated.resources"
        private const val resourcesDir = "src/jsMain/resources"
    }
}