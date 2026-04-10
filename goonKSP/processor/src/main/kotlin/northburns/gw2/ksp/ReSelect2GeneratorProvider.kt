package northburns.gw2.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import northburns.gw2.ksp.reselect2.ReSelect2Generator
import kotlin.io.path.Path

class ReSelect2GeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        // val projectDir = Path(environment.options.getValue("projectDirSite"))
        return ReSelect2Generator(environment.codeGenerator)
    }
}
