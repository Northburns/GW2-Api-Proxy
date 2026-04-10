package northburns.gw2.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.nio.file.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension

class GenerateDirJsImportEnumVisitor(
    private val projectDir: Path,
    private val codeGenerator: CodeGenerator,
) : KSVisitorVoid() {

    @OptIn(KspExperimental::class)
    override fun visitFile(file: KSFile, data: Unit) {
        //println("HELLO FILE!")

        val packageName = file.packageName.asString()
        val annotation = file.getAnnotationsByType(GenerateDirJsImportEnum::class).single()
        val fileName = file.fileName.removeSuffix(".kt")

        //println("name: $fileName")

        val fileSpec = FileSpec.builder(packageName, fileName)
        val enumClass = TypeSpec.enumBuilder(fileName)

        val dir = projectDir
            .resolve(annotation.base)
            .resolve(annotation.dir)
        dir.listDirectoryEntries().map { entry ->
            if (entry.isRegularFile()) {
                val name = entry.nameWithoutExtension
                enumClass.addEnumConstant(name)
                //println("entry: $name")
            }
        }

        val t = FunSpec.constructorBuilder().addParameter(fileName, Int::class)
            .build()


        fileSpec
            .addType(enumClass.build())
            .build()
        //     .writeTo(codeGenerator, false)
        // TODO Yeah, this is cool. We'll definitely find use for annotation processing!
    }
}
