package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.html.h1
import io.kvision.html.p

@Suppress("FunctionName")
fun Container._404(message: String) {
    h1("404 - Not found")
    p(message)
}
