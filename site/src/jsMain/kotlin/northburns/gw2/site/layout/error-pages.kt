package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.html.TAG
import io.kvision.html.code
import io.kvision.html.h1
import io.kvision.html.tag

@Suppress("FunctionName")
fun Container._404(params: Map<String, String>) {
    val message = params["message"] ?: ""
    h1("404 - Not found")
    tag(TAG.PRE) {
        code(message)
    }
}
