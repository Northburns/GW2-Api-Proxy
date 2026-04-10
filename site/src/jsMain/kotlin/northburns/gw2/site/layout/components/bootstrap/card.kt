package northburns.gw2.site.layout.components.bootstrap

import io.kvision.core.AlignContent
import io.kvision.core.AlignItems
import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
import io.kvision.core.UNIT
import io.kvision.html.Div
import io.kvision.html.Image
import io.kvision.html.Link
import io.kvision.html.Ul
import io.kvision.html.div
import io.kvision.html.image
import io.kvision.html.link
import io.kvision.html.ul
import io.kvision.panel.flexPanel
import io.kvision.state.bind
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.util.applyToImageNoSize

/**
 * [Bootstrap Documentation: Cards](https://getbootstrap.com/docs/5.3/components/card/)
 */
fun Container.card(
    init: Card.() -> Unit = {}
): Card {
    return CardVertical(init).also { add(it) }
}



sealed interface Card {
    val internalContainer: Container

    fun addImgTop(src: String, init: Image.() -> Unit = {}) =
        internalContainer.image(src = src, className = "card-img-top", init = init)

    fun addImgBottom(src: String, init: Image.() -> Unit = {}) =
        internalContainer.image(src = src, className = "card-img-bottom", init = init)

    fun addImgAndOverlay(src: String, initImage: Image.() -> Unit = {}, initOverlay: Div.() -> Unit) {
        internalContainer.image(src = src, className = "card-img", init = initImage)
        internalContainer.div(className = "card-img-overlay", init = initOverlay)
    }

    fun addBody(init: CardBody.() -> Unit) = CardBody(init = init).also { internalContainer.add(it) }

    fun addList(init: Ul.(liClassName: String) -> Unit) =
        internalContainer.ul(className = "list-group list-group-flush") {
            init("list-group-item")
        }

    /**
     * TODO these could be h1-h5 as well, instead of plain div
     */
    fun addCardHeader(init: Div.() -> Unit) = internalContainer.div(className = "card-header", init = init)
    fun addCardFooter(init: Div.() -> Unit) = internalContainer.div(className = "card-footer", init = init)
}

class CardVertical(
    init: Card.() -> Unit = {},
) : Div(className = "card"), Card {
    override val internalContainer: Container = this

    init {
        init()
    }
}



class CardBody(
    init: CardBody.() -> Unit = {},
) : Div(className = "card-body") {
    init {
        init()
    }

    fun addLink(label: String, url: String? = null, icon: String? = null, init: Link.() -> Unit = {}) =
        link(label = label, url = url, icon = icon, className = "card-link", init = init)
}