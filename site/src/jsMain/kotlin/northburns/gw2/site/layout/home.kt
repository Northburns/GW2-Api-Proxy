package northburns.gw2.site.layout

import com.gw2tb.gw2api.types.GW2ItemId
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.span
import northburns.gw2.site.layout.components.ItemComponent

fun Container.home() {
    div {
        span("hello home kt!")
    }
    div {
        ItemComponent(GW2ItemId(103815)).also { add(it) }
    }
}
