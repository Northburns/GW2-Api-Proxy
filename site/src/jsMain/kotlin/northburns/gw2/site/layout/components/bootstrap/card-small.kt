package northburns.gw2.site.layout.components.bootstrap

import io.kvision.core.AlignItems
import io.kvision.core.Container
import io.kvision.core.Display
import io.kvision.core.UNIT
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.html.image
import io.kvision.panel.gridPanel
import io.kvision.state.bind
import northburns.gw2.client.myclient.data.Gw2Image
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.util.applyToImageNoSize

fun Container.cardSmall(
    options: CardSmall.CardSmallOptions,
    init: Container.() -> Unit = {},
): CardSmall {
    return CardSmall(options, init).also { add(it) }
}

/**
 * CardSmall is a special case (it no longer extends Card!). It's a fixed height card, with a square image on the left side,
 * and pretty fixed content on the right side.
 * The name is a bit off, I think.
 */
class CardSmall(
    opts: CardSmallOptions,
    init: Container.() -> Unit = {},
) : Div(className = "card") {

    class CardSmallOptions(
        val imageSize: Int,
        val imageSrc: Gw2Image = Gw2Image.Placeholder,
        val imageSubGw2: ((GoonState) -> Gw2Image)? = null,
        val headerSubGw2: ((GoonState) -> String)? = null,
        val showImage: Boolean = true,
    )

    val hasHeader = opts.headerSubGw2 != null
    val headerHeight = if (hasHeader) 32 else 0
    val contentHeight = opts.imageSize - headerHeight

    init {
        require(contentHeight > 0) { "Content Height is <= 0 given imageSize ${opts.imageSize} and header($hasHeader)" }
        val imageWidth = if(opts.showImage) opts.imageSize else 0
        gridPanel(
            templateColumns = "${imageWidth}px auto",
            templateRows = "${headerHeight}px ${contentHeight}px",
            templateAreas = listOf(
                "img header",
                "img content",
            ),
        ) {
            height = opts.imageSize to UNIT.px
            minWidth = (3 * opts.imageSize) to UNIT.px
            options(area = "img", alignSelf = AlignItems.CENTER) {
                div {
                    val f: Div.(Gw2Image) -> Unit = {
                        image(
                            src = opts.imageSrc.src(opts.imageSize),
                            className = "img-fluid rounded-start",
                        )
                    }
                    if (opts.showImage && opts.imageSubGw2 != null) {
                        bind(goonStore, opts.imageSubGw2) { gw2Image ->
                            image(
                                src = gw2Image.src(opts.imageSize),
                                className = "img-fluid rounded-start",
                            ) {
                                width = opts.imageSize to UNIT.px
                                height = null
                                gw2Image.imageAttributes(opts.imageSize).applyToImageNoSize(this)

                                // TODO SILLY, not like this
                                if (gw2Image is Gw2Image.Gw2TreasuresIcon) {
                                    gw2Image.classNames.split(" ").forEach { addCssClass(it) }
                                    gw2Image.styleProperties.forEach { (k, v) -> setStyle(k, v) }
                                }
                            }
                        }
                    } else {
                        image(
                            src = opts.imageSrc.src(opts.imageSize),
                            className = "img-fluid rounded-start",
                        ) {
                            width = opts.imageSize to UNIT.px
                            height = null
                            if(!opts.showImage)
                                display = Display.NONE
                        }
                    }
                }
            }
            if (opts.headerSubGw2 != null)
                options(area = "header") {
                    div(className = "card-header p-1").bind(goonStore, opts.headerSubGw2) {
                        content = it
                    }
                }
            options(area = "content") {
                div(className = "p-1") {
                    init()
                }
            }
        }
//        flexPanel(
//            direction = FlexDirection.ROW,
//            wrap = FlexWrap.NOWRAP,
//            justify = JustifyContent.START,
//            alignItems = AlignItems.START,
//            alignContent = AlignContent.START,
//            spacing = 0,
//        ) {
//            options(alignSelf = AlignItems.CENTER) {
//
//            }
//            options(grow = 1) {
//                div {
//                    minWidth = 2 * opts.imageSize to UNIT.px
//                    height = opts.imageSize to UNIT.px
//                    if (opts.headerSubGw2 != null)
//
//                    CardBody(init).also { add(it) }
//
//                }
//            }
//        }
        /*div(className = "row") {

            div(className = "col-4 align-self-center") {

            }
            div(className = "col-8") {
                this@CardHorizontal.internalContainer = this
                this@CardHorizontal.init()
            }
        }*/
    }
}
