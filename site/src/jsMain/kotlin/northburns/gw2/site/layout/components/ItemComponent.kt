package northburns.gw2.site.layout.components

import com.gw2tb.gw2api.types.GW2ItemId
import io.kvision.html.span
import io.kvision.panel.SimplePanel

class ItemComponent(
    val itemId: GW2ItemId,
): SimplePanel("item") {


    init {
        span(itemId.toString())
    }

}
