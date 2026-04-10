package northburns.gw2.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import kotlin.io.path.Path

class GoonSiteResourcesEnumGeneratorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val projectDir = Path(environment.options.getValue("projectDirSite"))
        return GoonSiteResourcesEnumGenerator(projectDir, environment.codeGenerator)
    }
}
