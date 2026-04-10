package northburns.gw2.site.layout.style

import io.kvision.KVManager
import io.kvision.core.Container
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.snabbdom.VNode

fun Container.svg(svg: SvgInline, className: String? = null) =
    Svg(svg, className).also { add(it) }


class Svg(
    viewBox: String,
    svgContent: String,
    className: String? = null,
) : Tag(
    type = TAG.SVG,
    className = className,
    attributes = mapOf("viewBox" to viewBox),
    content = svgContent,
    rich = true,
) {
    constructor(
        svg: SvgInline,
        className: String? = null
    ) : this(viewBox = svg.viewBox, svgContent = svg.pathElement, className = className)

    init    {
    }

    override fun render(): VNode {
        val html = requireNotNull(content) { "Content must contain the SVG" }
        return render("svg", arrayOf(KVManager.virtualize(html)))
    }
}

