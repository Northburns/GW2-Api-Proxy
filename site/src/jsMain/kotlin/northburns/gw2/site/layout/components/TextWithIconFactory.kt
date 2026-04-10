package northburns.gw2.site.layout.components

import com.gw2tb.gw2api.types.GW2ItemId
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Placeholder
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.resolve
import northburns.gw2.site.util.reselect.goonBindReselect

object TextWithIconFactory {


    fun itemText(id: GW2ItemId) = empty()
        .apply {
            setAttribute("item-id", id.raw.toString())
        }
        .goonBindReselect { it: GoonState -> it.api.resolve(id) }
        .render { (it) ->
            updateText(it?.name ?: loadingTxt)
            udpateImage(it.gw2Image())
            // updateLink() TODO wiki link
        }

    private fun empty() = TextWithIcon(text = loadingTxt, image = Placeholder)
    private val loadingTxt = "🔄️"

}
