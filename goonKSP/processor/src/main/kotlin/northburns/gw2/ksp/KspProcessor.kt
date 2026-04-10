package northburns.gw2.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import java.nio.file.Path

class KspProcessor(
    private val projectDir: Path,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return emptyList() // TODO re-implement this processor when we need it..

        val visitor = GenerateDirJsImportEnumVisitor(projectDir, codeGenerator)

        val symbols = resolver.getSymbolsWithAnnotation(GenerateDirJsImportEnum::class.qualifiedName.orEmpty())
            .filterIsInstance<KSFile>()
            .filter(KSNode::validate)

        val validatedSymbols = symbols.filter { it.validate() }

        validatedSymbols.forEach { symbol ->
            symbol.accept(visitor, Unit)
        }

        return symbols.toList() - validatedSymbols.toSet()
    }
}
