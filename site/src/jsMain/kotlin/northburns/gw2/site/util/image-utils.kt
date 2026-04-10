package northburns.gw2.site.util

import io.kvision.core.UNIT
import io.kvision.html.Image
import northburns.gw2.client.myclient.data.Gw2Image

fun Gw2Image.createImage(size: Int = defaultSize): Image {
    return Image(src = src(size)) {
        imageAttributes(size).applyToImage(this)
    }
}

fun Gw2Image.ImageAttributes.applyToImage(image: Image) {
    image.width = widthPx to UNIT.px
    image.height = heightPx to UNIT.px
    applyToImageNoSize(image)
}

fun Gw2Image.ImageAttributes.applyToImageNoSize(image: Image) {
    if (srcset.isNotBlank())
        image.setAttribute("srcset", srcset)
    // image.setAttribute("crossorigin", crossorigin)
    image.setAttribute("referrerpolicy", referrerpolicy)
    image.setAttribute("loading", loading)
    image.setAttribute("decoding", decoding)

    image.src = src
    image.alt = alt
}

fun Gw2Image.ImageAttributes.applyTo(collector: (k: String, v: String) -> Unit) {
    collector("src", src)
    collector("srcset", srcset)
    collector("width", width)
    collector("height", height)
    collector("alt", alt)
    // collector("crossorigin", crossorigin)
    collector("referrerpolicy", referrerpolicy)
    collector("loading", loading)
    collector("decoding", decoding)
}