package northburns.gw2.site.layout.components

import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.Div
import io.kvision.html.button
import io.kvision.html.div
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.layout.components.GoonButton.GoonButtonStyle
import northburns.gw2.site.layout.style.PanelBackgrounds.PanelBackground
import northburns.gw2.site.layout.style.PanelBackgrounds.setPanelBackground
import northburns.gw2.site.util.tailwind.tw
import org.w3c.dom.events.MouseEvent

fun Container.goonButton(
    style: GoonButtonStyle = GoonButtonStyle.ACTION,
    text: String,
    className: String? = null,
    onClick: (Button.(MouseEvent) -> Unit)? = null,
): GoonButton = GoonButton(
    style = style,
    content = { +text },
    className = className,
    onClick = onClick,
).also { add(it) }

/**
 * I'm really happy with this button :D
 */
class GoonButton(
    style: GoonButtonStyle = GoonButtonStyle.ACTION,
    className: String? = null,
    content: Div.() -> Unit,
    onClick: (Button.(MouseEvent) -> Unit)? = null,
) : Div(
    className = tw {
        +"m-1"
        +"grid group"
        if (className != null) +className
    },
) {

    enum class GoonButtonStyle(
        val basic: PanelBackground,
        val hover: PanelBackground,
        val active: PanelBackground,
    ) {
        ACTION(
            basic = PanelBackground.BUTTON_GREY,
            hover = PanelBackground.BUTTON_GREY_HOVER,
            active = PanelBackground.BUTTON_GREY_ACTIVE,
        ),
        ACTION_YELLOW(
            basic = PanelBackground.BUTTON_YELLOW,
            hover = PanelBackground.BUTTON_YELLOW_HOVER,
            active = PanelBackground.BUTTON_YELLOW_ACTIVE,
        )
    }

    init {
        val borderWidth = "16px"
        // concrete unstyled button on the background
        button("", className = "row-span-full col-span-full") {
            GoonLog["GoonButton"].info("clicked")
            onClick?.also(::onClick)
        }
        // basic background
        div(className = tw {
            +"block group-hover:hidden group-active:hidden"
            +"pointer-events-none"
            +"row-span-full col-span-full"
        }) {
            setPanelBackground(panel = PanelBackground.BUTTON_GREY, borderWidth = borderWidth)
        }
        // hover background
        div(className = tw {
            +"hidden group-hover:block group-active:hidden"
            +"pointer-events-none"
            +"row-span-full col-span-full"
        }) {
            setPanelBackground(panel = PanelBackground.BUTTON_GREY_HOVER, borderWidth = borderWidth)
        }
        // active background
        div(className = tw {
            +"hidden group-active:block"
            +"pointer-events-none"
            +"row-span-full col-span-full"
        }) {
            setPanelBackground(panel = PanelBackground.BUTTON_GREY_ACTIVE, borderWidth = borderWidth)
        }
        // Content
        div(className = tw {
            +"mx-4 mt-[0.5rem] mb-[0.75rem]"
            +"group-active:mt-[0.625rem] group-active:mb-[0.625rem]"
            +"pointer-events-none"
            +"flex justify-center items-center"
            +"row-span-full col-span-full"
        }) {
            div(className = tw {
                +"pointer-events-none"
            }) {
                content()
            }
        }
    }
}