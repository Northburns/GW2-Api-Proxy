package northburns.gw2.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import kotlin.io.path.Path

class ProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val projectDir = Path(environment.options.getValue("projectDir"))
        return KspProcessor(projectDir,environment.codeGenerator)
    }
}