package northburns.gw2.site.layout.components

import io.kvision.html.Div
import io.kvision.html.span
import northburns.gw2.site.util.tailwind.tw

class FactSheet(): Div(
    className = tw {
        +"border-1 border-black rounded"
        +"m-2 shadow-md"
    }
) {


    init {
        span("Hello factsheet")
    }

}
