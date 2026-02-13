package northburns.gw2.site.layout.components

import com.gw2tb.gw2api.types.GW2ItemId
import io.kvision.html.image
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons.Gw2tIconSize.`32`
import northburns.gw2.site.Gw2GoonManager

class ItemComponent(
    val itemId: GW2ItemId,
) : SimplePanel("item") {


    init {
        bind(Gw2GoonManager.goonStore, { it.api.items[itemId] }) { item ->
            if(item==null) {
                span("Loading item[id=${itemId.raw}]...")
                Gw2GoonManager.api.requestItem(itemId)
            } else {
                imageGw2Treasures(item.icon!!, `32`)
                span(item.name)
            }
        }
    }

}
