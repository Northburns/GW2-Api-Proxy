package northburns.gw2.site.util.markdown

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.html.Br
import io.kvision.html.Code
import io.kvision.html.Div
import io.kvision.html.Em
import io.kvision.html.Image
import io.kvision.html.Li
import io.kvision.html.Ol
import io.kvision.html.P
import io.kvision.html.Strong
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.html.TextNode
import io.kvision.html.Ul
import io.kvision.html.p
import io.kvision.html.tag
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_1
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_2
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_3
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_4
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_5
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ATX_6
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_AUTOLINK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_BLOCK_QUOTE
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_CODE_BLOCK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_CODE_FENCE
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_CODE_SPAN
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_EMPH
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_FULL_REFERENCE_LINK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_HTML_BLOCK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_IMAGE
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_INLINE_LINK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LINK_DEFINITION
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LINK_DESTINATION
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LINK_LABEL
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LINK_TEXT
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LINK_TITLE
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_LIST_ITEM
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_MARKDOWN_FILE
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_ORDERED_LIST
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_PARAGRAPH
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_SETEXT_1
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_SETEXT_2
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_SHORT_REFERENCE_LINK
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_STRONG
import northburns.gw2.markdown.GoonMarkElementType.ELEMENT_UNORDERED_LIST
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_ATX_CONTENT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_ATX_HEADER
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_AUTOLINK
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_BACKTICK
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_BAD_CHARACTER
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_BLOCK_QUOTE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_CODE_FENCE_CONTENT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_CODE_FENCE_END
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_CODE_FENCE_START
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_CODE_LINE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_COLON
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_DOUBLE_QUOTE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_EMAIL_AUTOLINK
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_EMPH
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_EOL
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_ESCAPED_BACKTICKS
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_EXCLAMATION_MARK
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_FENCE_LANG
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_GT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_HARD_LINE_BREAK
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_HORIZONTAL_RULE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_HTML_BLOCK_CONTENT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_HTML_TAG
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LBRACKET
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LINK_ID
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LINK_TITLE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LIST_BULLET
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LIST_NUMBER
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LPAREN
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_LT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_RBRACKET
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_RPAREN
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_SETEXT_1
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_SETEXT_2
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_SETEXT_CONTENT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_SINGLE_QUOTE
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_TEXT
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_URL
import northburns.gw2.markdown.GoonMarkElementType.TOKEN_WHITE_SPACE
import northburns.gw2.markdown.GoonMarkGeneratorContext
import northburns.gw2.markdown.GoonMarkNode
import northburns.gw2.site.layout.components.CommonComponents.createGoonH1
import northburns.gw2.site.layout.components.CommonComponents.createGoonH2
import northburns.gw2.site.layout.components.CommonComponents.createGoonH3
import northburns.gw2.site.layout.components.CommonComponents.createGoonH4
import northburns.gw2.site.layout.components.CommonComponents.createGoonH5
import northburns.gw2.site.layout.components.CommonComponents.createGoonH6
import northburns.gw2.site.layout.components.TextWithIcon
import northburns.gw2.site.util.markdown.GoonMarkdownMacros.CODESPAN_PREFIX

/**
 * The work is ongoing. I should do some comparisons to reference CommonMark renderings.
 */
object KVMarkdownGeneratorContext : GoonMarkGeneratorContext<Component> {
    override fun render(node: GoonMarkNode): Component =
        requireNotNull(renderInner(node)) { "Error: rendering resulted in null component" }

    fun renderInner(node: GoonMarkNode): Component? {
        val kids = node.createChildrenConsumer()
        val component = renderComponent(node, kids)
            ?: return null // If renderComponent says its null, we short-circuit rendering of the node with no questions.

        if (!kids.consumed) {
            node.children.forEach {
                renderInner(it)?.also { child ->
                    add(node, component, child)
                }
            }
        }

        return component
    }

    private fun renderComponent(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component? =
        when (node.type) {
            ////////////////////////////////////////////////////////////////////////////////////
            //   Simple
            ////////////////////////////////////////////////////////////////////////////////////
            ELEMENT_MARKDOWN_FILE -> Div()
            ELEMENT_UNORDERED_LIST -> Ul()
            ELEMENT_LIST_ITEM -> Li()
            ELEMENT_ORDERED_LIST -> Ol()
            ELEMENT_EMPH -> Em()
            ELEMENT_PARAGRAPH -> P()
            ELEMENT_STRONG -> Strong()
            TOKEN_TEXT -> TextNode(node.txt())
            TOKEN_EMPH -> Em()
            TOKEN_BACKTICK -> null
            TOKEN_ESCAPED_BACKTICKS -> TextNode("`")
            ELEMENT_LINK_TEXT -> TextNode(node.txt())
            ELEMENT_LINK_DESTINATION -> TextNode(node.txt())
            TOKEN_SINGLE_QUOTE -> TextNode("'")
            TOKEN_DOUBLE_QUOTE -> TextNode("\"")
            TOKEN_LPAREN -> TextNode("(")
            TOKEN_RPAREN -> TextNode(")")
            TOKEN_LBRACKET -> TextNode("[")
            TOKEN_RBRACKET -> TextNode("]")
            TOKEN_LT -> TextNode("<")
            TOKEN_GT -> TextNode(">")
            TOKEN_COLON -> TextNode(":")
            TOKEN_EXCLAMATION_MARK -> TextNode("!")
            TOKEN_HARD_LINE_BREAK -> Br()
            TOKEN_HORIZONTAL_RULE -> Tag(TAG.HR)
            TOKEN_BAD_CHARACTER -> TextNode("█")
            TOKEN_WHITE_SPACE -> TextNode(" ")
            TOKEN_LIST_BULLET -> null
            TOKEN_EOL -> TextNode("\n", rich = false)
            TOKEN_LIST_NUMBER -> null
            TOKEN_CODE_LINE -> TextNode(node.txt())

            ////////////////////////////////////////////////////////////////////////////////////
            //   Complex
            ////////////////////////////////////////////////////////////////////////////////////
            ELEMENT_INLINE_LINK -> renderInlineLink(node, kids)
            ELEMENT_IMAGE -> renderImage(node, kids)

            ELEMENT_CODE_SPAN -> renderCodeSpan(node, kids)
            ELEMENT_CODE_BLOCK -> renderCodeBlock(node, kids)
            ELEMENT_CODE_FENCE -> renderCodeFence(node, kids)
            ELEMENT_SETEXT_1 -> renderHeaderExt(node, kids, { h -> createGoonH1 { +h } })
            ELEMENT_SETEXT_2 -> renderHeaderExt(node, kids, { h -> createGoonH2 { +h } })
            ELEMENT_ATX_1 -> renderHeaderAtx(node, kids, { h -> createGoonH1 { +h } })
            ELEMENT_ATX_2 -> renderHeaderAtx(node, kids, { h -> createGoonH2 { +h } })
            ELEMENT_ATX_3 -> renderHeaderAtx(node, kids, { h -> createGoonH3 { +h } })
            ELEMENT_ATX_4 -> renderHeaderAtx(node, kids, { h -> createGoonH4 { +h } })
            ELEMENT_ATX_5 -> renderHeaderAtx(node, kids, { h -> createGoonH5 { +h } })
            ELEMENT_ATX_6 -> renderHeaderAtx(node, kids, { h -> createGoonH6 { +h } })
            ELEMENT_BLOCK_QUOTE -> renderElementBlockQuote(node, kids)
            TOKEN_BLOCK_QUOTE -> renderTokenBlockQuote(node, kids)

            ////////////////////////////////////////////////////////////////////////////////////
            //   Not implemented / not encountered
            ////////////////////////////////////////////////////////////////////////////////////
            TOKEN_SETEXT_1 -> TODO("Generate: TOKEN_SETEXT_1")
            TOKEN_SETEXT_2 -> TODO("Generate: TOKEN_SETEXT_2")
            TOKEN_ATX_HEADER -> TODO("Generate: TOKEN_ATX_HEADER")
            TOKEN_ATX_CONTENT -> TODO("Generate: TOKEN_ATX_CONTENT")
            TOKEN_SETEXT_CONTENT -> TODO("Generate: TOKEN_SETEXT_CONTENT")
            ELEMENT_HTML_BLOCK -> TODO("Generate: ELEMENT_HTML_BLOCK")
            ELEMENT_LINK_DEFINITION -> TODO("Generate: ELEMENT_LINK_DEFINITION")
            ELEMENT_LINK_LABEL -> TODO("Generate: ELEMENT_LINK_LABEL")
            ELEMENT_LINK_TITLE -> TODO("Generate: ELEMENT_LINK_TITLE")
            ELEMENT_FULL_REFERENCE_LINK -> TODO("Generate: ELEMENT_FULL_REFERENCE_LINK")
            ELEMENT_SHORT_REFERENCE_LINK -> TODO("Generate: ELEMENT_SHORT_REFERENCE_LINK")
            ELEMENT_AUTOLINK -> TODO("Generate: ELEMENT_AUTOLINK")
            TOKEN_HTML_BLOCK_CONTENT -> TODO("Generate: TOKEN_HTML_BLOCK_CONTENT")
            TOKEN_LINK_ID -> TODO("Generate: TOKEN_LINK_ID")
            TOKEN_URL -> TODO("Generate: TOKEN_URL")
            TOKEN_FENCE_LANG -> TODO("Generate: TOKEN_FENCE_LANG")
            TOKEN_CODE_FENCE_START -> TODO("Generate: TOKEN_CODE_FENCE_START")
            TOKEN_CODE_FENCE_CONTENT -> TODO("Generate: TOKEN_CODE_FENCE_CONTENT")
            TOKEN_CODE_FENCE_END -> TODO("Generate: TOKEN_CODE_FENCE_END")
            TOKEN_LINK_TITLE -> TODO("Generate: TOKEN_LINK_TITLE")
            TOKEN_AUTOLINK -> TODO("Generate: TOKEN_AUTOLINK")
            TOKEN_EMAIL_AUTOLINK -> TODO("Generate: TOKEN_EMAIL_AUTOLINK")
            TOKEN_HTML_TAG -> TODO("Generate: TOKEN_HTML_TAG")
        }

    private fun renderHeaderExt(
        node: GoonMarkNode,
        kids: GoonMarkNode.NodeChildrenConsumer,
        h: (String) -> Component
    ): Component {
        val (content, _, _) = kids.expectChildren(TOKEN_SETEXT_CONTENT, TOKEN_EOL, null)
        return h(content.txt())
    }

    private fun renderHeaderAtx(
        node: GoonMarkNode,
        kids: GoonMarkNode.NodeChildrenConsumer,
        h: (String) -> Component
    ): Component {
        val (_, content, _) = kids.expectChildren(TOKEN_ATX_HEADER, TOKEN_ATX_CONTENT)
        return h(content.txt())
    }

    private fun renderElementBlockQuote(
        node: GoonMarkNode,
        kids: GoonMarkNode.NodeChildrenConsumer,
    ): Component {
        require(node.children.first().type == TOKEN_BLOCK_QUOTE) { "Element BlockQuote expected to start with matching token" }
        val c = Tag(TAG.BLOCKQUOTE)
        node.children.drop(1).forEach { child ->
            renderInner(child)?.also { c.add(it) }
        }
        kids.consumed = true
        return c
    }

    private fun renderTokenBlockQuote(
        node: GoonMarkNode,
        kids: GoonMarkNode.NodeChildrenConsumer,
    ): Component? {
        kids.expectNoChildren()
        return null
    }

    private fun renderInlineLink(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component {
        val (linkText, _, linkDestination, _) = kids.expectChildren(
            ELEMENT_LINK_TEXT,
            TOKEN_LPAREN,
            ELEMENT_LINK_DESTINATION,
            TOKEN_RPAREN,
        )
        // TODO link text's formatting
        val textToDisplay = linkText.txt().removePrefix("[").removeSuffix("]")

        val href = linkDestination.txt()
        return TextWithIcon(
            text = textToDisplay,
            href = href,
            external = !href.startsWith("#"),
        )
    }

    private fun renderImage(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component {
        val (_, inlineLink) = kids.expectChildren(TOKEN_EXCLAMATION_MARK, ELEMENT_INLINE_LINK)
        val (text, destination, title) = readInlineLinkElements(inlineLink)

        return Image(src = destination, alt = title) {
            if (text != null)
                setAttribute("render-text", text.joinToString(separator = "|") { it.txt() })
        }
    }

    private fun readInlineLinkElements(node: GoonMarkNode): Triple<List<GoonMarkNode>?, String?, String?> {
        // ![text](destination "title")
        var text: List<GoonMarkNode>? = null
        var destination: String? = null
        var title: String? = null

        node.children.forEach { child ->
            if (child.type == ELEMENT_LINK_TEXT) {
                text = child.children.drop(1).dropLast(1)
            }
            if (child.type == ELEMENT_LINK_DESTINATION) destination = child.txt()
            if (child.type == ELEMENT_LINK_TITLE) title = child.txt()
        }

        return Triple(text, destination, title)
    }

    private fun renderCodeSpan(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component {
        // Drop TOKEN_BACKTICK tokens:
        val txt = node.children.drop(1).dropLast(1).joinToString(separator = "") { it.txt() }
        kids.consumed = true
        return if (txt.startsWith(CODESPAN_PREFIX))
            GoonMarkdownMacros.render(txt)
        else Code(txt)
    }

    private fun renderCodeBlock(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component {
        val content = node.children.mapNotNull { if (it.type == TOKEN_CODE_LINE) it.txt() else null }
        kids.consumed = true
        return renderCode(null, content)
    }

    private fun renderCodeFence(node: GoonMarkNode, kids: GoonMarkNode.NodeChildrenConsumer): Component {
        val lang = node.children.singleOrNull { it.type == TOKEN_FENCE_LANG }?.txt()
        val content = node.children.mapNotNull { if (it.type == TOKEN_CODE_FENCE_CONTENT) it.txt() else null }
        kids.consumed = true
        return renderCode(lang, content)
    }

    private fun renderCode(lang: String?, content: List<String>): Component {
        // How  to render fenced code blocks?
        return Div {
            if (lang != null)
                p(lang)
            tag(TAG.PRE, content.joinToString(separator = "\n").trimIndent())
        }
    }


    private fun add(node: GoonMarkNode, parent: Component, child: Component) {
        when (parent) {
            is TextNode if child is TextNode -> parent.content += child.content
            is Container -> parent.add(child)
            else -> throw IllegalStateException("Node[${node.type}]: Can't add child (${child::class}) to non-Container: ${parent::class}")
        }
    }

}
