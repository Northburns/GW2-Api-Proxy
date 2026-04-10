package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.html.p
import northburns.gw2.site.layout.components.TextWithIcon

fun Container.home() {
    p {
        +"Welcome to my site! You can find "
        TextWithIcon(text="AAA").also { add(it) }
        +" and "
        TextWithIcon(text="BBB", href = "https://google.com").also { add(it) }
        +" and "
        TextWithIcon(text="CCC", href = "https://google.com", external = true).also { add(it) }
    }
}
