package northburns.gw2.site.app.markdownview

import io.kvision.core.Container
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.layout.components.DebugData

fun Container.markdownview(params: Map<String, String>) {
    val file = params["file"] ?: throw IllegalArgumentException("file param is missing")
    val tree = when (file) {
        "guides-legendary-vision" -> northburns.gw2.site.generated.md.`guides_legendary-vision`
        else -> throw IllegalArgumentException("unexpected file param: $file")
    }

    DebugData()

    Gw2GoonManager.markdown.generate(tree()).also { add(it) }
//    bind(goonStore, {it.currentView}) { view ->
//        if(view !is MarkdownGuidesViewerRoute) span("Error! State's route is not expected")
//        else if(view.mdString==null) span("Loading markdown... ${view.mdRes.res}")
//        else Gw2GoonManager.markdown.generate(view.mdString).also { add(it) }
//    }

}
