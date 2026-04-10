package northburns.gw2.ksp.reselect2

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.ksp.writeTo
import northburns.gw2.ksp.ReSelect2Generate
import northburns.gw2.ksp.reselect2.ReSelect2GenerateOptions.Companion.toOptions

class ReSelect2Generator(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    init {
        println("ReSelectGenerator")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {

        resolver.getSymbolsWithAnnotation(ReSelect2Generate::class.qualifiedName!!)
            .filterIsInstance<KSFile>()
            .forEach { declaration ->

                val originatingKSFiles = listOfNotNull(declaration.containingFile)

                val annotation = declaration.getAnnotationsByType(ReSelect2Generate::class).single()
                println("ReSelect2Generator: Found annotation: $annotation")

                val packageName = declaration.packageName.asString()
                val options = annotation.toOptions()

                (1..annotation.n).forEach { n ->
                    val ctx = ReSelectNGeneratorContext(
                        packageName = packageName,
                        n = n,
                    )
                    ctx.fileSpec
                        .writeTo(codeGenerator, false, originatingKSFiles)
                }

            }

        return emptyList()
    }

}