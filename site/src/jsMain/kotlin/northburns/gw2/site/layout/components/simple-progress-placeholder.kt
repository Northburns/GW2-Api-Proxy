package northburns.gw2.site.layout.components

import io.kvision.core.Container
import io.kvision.html.icon
import kotlinx.coroutines.CoroutineScope
import northburns.gw2.site.Gw2GoonManager.withProgress

fun <C : Container, V> C.progressPlaceHolder(
    block: suspend CoroutineScope.() -> V,
    init: Container.(V) -> Unit = {},
) {
    val i = icon("fa-solid fa-spinner fa-spin")
    withProgress {
        val v = block()
        i.parent?.remove(i)
        init(v)
    }
}
