package northburns.gw2.site.layout.components

import io.kvision.core.Container
import io.kvision.html.image
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons

fun Container.imageGw2Treasures(
    iconUrl: String,
    size: Gw2TreasuresIcons.Gw2tIconSize,
) {

    val attrs = Gw2TreasuresIcons.gw2tIcon(iconUrl, size)
    requireNotNull(attrs) { "Attrs can't be null" }

    image(
        src = attrs.src,
        alt = attrs.alt,
    ) {

        setAttribute("width", attrs.width)
        setAttribute("height", attrs.height)
        setAttribute("crossorigin", attrs.crossorigin)
        setAttribute("referrerpolicy", attrs.referrerpolicy)
        setAttribute("loading", attrs.loading)
        setAttribute("decoding", attrs.decoding)
        attrs.srcset?.let { setAttribute("srcset", it) }

        refresh()
    }

}
