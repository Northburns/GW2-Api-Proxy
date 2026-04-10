package northburns.gw2.site.layout.style

import kotlinx.dom.addClass
import kotlinx.dom.hasClass
import kotlinx.dom.removeClass
import org.w3c.dom.Element

fun Element.toggleClass(className: String): Boolean =
    if (hasClass(className))
        removeClass(className)
    else addClass(className)
