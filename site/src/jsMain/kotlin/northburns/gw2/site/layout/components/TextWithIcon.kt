package northburns.gw2.site.layout.components

import io.kvision.html.Image
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.html.TextNode
import io.kvision.html.tag
import io.kvision.html.textNode
import northburns.gw2.client.myclient.data.Gw2Image
import northburns.gw2.site.layout.style.SvgInline
import northburns.gw2.site.layout.style.svg
import northburns.gw2.site.util.applyToImage
import northburns.gw2.site.util.tailwind.tw

/**
 */
class TextWithIcon(
    text: String,
    image: Gw2Image? = null,
    href: String? = null,
    external: Boolean = false,
) : Tag(
    type = TAG.SPAN,
    className = tw {
        +"inline-block"
        +"gap-1"
        if (href != null)
            +"underline text-blue-600 hover:text-blue-800"
    },
) {
    private val imgSize = 32
    private val img: Image? = if (image == null) null else Image(
        src = image.src(imgSize),
        className = tw {
            +"inline"
            +"size-[2rem]"
            // align-middle = image's middle is on "lowercase text's middle", nice
            // align-bottom = image's bottom is on text's bottom, nice
            +"align-bottom"
            +"mr-1" // change to ml if image on other side
        })

    private val txt: TextNode = TextNode(text)

    init {
        require(text.isNotBlank()) { "text is blank, likely an error" }
        rebuild(href, external)
    }

    private fun rebuild(href: String?, external: Boolean) {
        inline fun maybeLink(crossinline build: Tag.() -> Unit) {
            if (href != null) {
                tag(type = TAG.A) {
                    setAttribute("href", href)
                    if (external) setAttribute("target", "_blank")
                    this@tag.build()
                }
            } else this@TextWithIcon.build()
        }

        // Split into two links, so that the "space" above (And maybe below) text isn't a link.
        img?.also {
            maybeLink { add(it) }
        }
        maybeLink {
            add(this@TextWithIcon.txt)
            if (href != null && external) {
                textNode(" ")
                svg(SvgInline.EXTERNALLINK, className = tw {
                    +"inline align-baseline"
                    +"fill-transparent stroke-1"
                    +"stroke-blue-600 hover:stroke-blue-800"
                    +"size-[1.0rem]"
                })
            }
        }
    }

    fun updateText(text: String) {
        txt.content = text
    }

    fun udpateImage(image: Gw2Image) {
        requireNotNull(img) { "updateImage: img==null. Please provide Placeholder in constructor." }
        image.imageAttributes(size = imgSize).applyToImage(img)
    }

    fun updateLink(href: String?, external: Boolean = false) {
        removeAll()
        rebuild(href, external)
    }

}
