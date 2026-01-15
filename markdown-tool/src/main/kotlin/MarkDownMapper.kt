package northburns.gw2

import com.vladsch.flexmark.ast.BlockQuote
import com.vladsch.flexmark.ast.BulletList
import com.vladsch.flexmark.ast.Code
import com.vladsch.flexmark.ast.Emphasis
import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.ast.IndentedCodeBlock
import com.vladsch.flexmark.ast.Link
import com.vladsch.flexmark.ast.ListItem
import com.vladsch.flexmark.ast.OrderedList
import com.vladsch.flexmark.ast.Paragraph
import com.vladsch.flexmark.ast.SoftLineBreak
import com.vladsch.flexmark.ast.StrongEmphasis
import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.ast.ThematicBreak
import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough
import com.vladsch.flexmark.util.ast.Document
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.sequence.BasedSequence
import northburns.gw2.markdown.model.MdBlockQuote
import northburns.gw2.markdown.model.MdBulletList
import northburns.gw2.markdown.model.MdCode
import northburns.gw2.markdown.model.MdDocument
import northburns.gw2.markdown.model.MdEmphasis
import northburns.gw2.markdown.model.MdHeading
import northburns.gw2.markdown.model.MdIndentedCodeBlock
import northburns.gw2.markdown.model.MdLink
import northburns.gw2.markdown.model.MdListItem
import northburns.gw2.markdown.model.MdNode
import northburns.gw2.markdown.model.MdOrderedList
import northburns.gw2.markdown.model.MdParagraph
import northburns.gw2.markdown.model.MdSoftLineBreak
import northburns.gw2.markdown.model.MdStrikethrough
import northburns.gw2.markdown.model.MdStrongEmphasis
import northburns.gw2.markdown.model.MdText
import northburns.gw2.markdown.model.MdThematicBreak

object MarkDownMapper {

    fun node(n: Node): MdNode = when (n) {
        is Document -> document(n)
        is Paragraph -> paragraph(n)
        is Text -> text(n)
        is BulletList -> bulletList(n)
        is ListItem -> bulletListIem(n)
        is Emphasis -> emphasis(n)
        is StrongEmphasis -> strongEmphasis(n)
        is ThematicBreak -> MdThematicBreak(children = children(n))
        is Link -> link(n)
        is SoftLineBreak -> MdSoftLineBreak(children = children(n))
        is Heading -> heading(n)
        is Strikethrough -> strikethrough(n)
        is BlockQuote -> blockQuote(n)
        is Code -> code(n)
        is OrderedList -> orderedList(n)
        is IndentedCodeBlock -> indentedCodeBlock(n)
        else -> throw IllegalArgumentException("Unexpected node type: ${n.javaClass}")
    }

    private fun indentedCodeBlock(n: IndentedCodeBlock) = MdIndentedCodeBlock(
        text = n.chars.toString(),
        children = children(n),
    )

    private fun orderedList(n: OrderedList) = MdOrderedList(
        startNumber = n.startNumber,
        delimiter = n.delimiter,
        children = children(n),
    )

    private fun code(n: Code) = MdCode(
        text = n.text.basedSequenceText(),
        children = children(n),
    )

    private fun blockQuote(n: BlockQuote) = MdBlockQuote(
        children = children(n),
    )

    private fun strikethrough(n: Strikethrough) = MdStrikethrough(
        text = n.text.basedSequenceText(),
        children = children(n),
    )

    private fun heading(n: Heading) = MdHeading(
        text = n.text.basedSequenceText(),
        level = n.level,
        anchorRefId = n.anchorRefId,
        children = children(n),
    )

    private fun link(n: Link) = MdLink(
        text = n.text.basedSequenceText(),
        achorRef = n.anchorRef.basedSequenceText(),
        pageRef = n.pageRef.basedSequenceText(),
        url = n.url.basedSequenceText(),
        children = children(n),
    )

    private fun emphasis(n: Emphasis) = MdEmphasis(
        text = n.text.basedSequenceText(),
        children = children(n),
    )


    private fun strongEmphasis(n: StrongEmphasis) = MdStrongEmphasis(
        text = n.text.basedSequenceText(),
        children = children(n),
    )


    private fun bulletListIem(n: ListItem) = MdListItem(
        children = children(n),
    )

    private fun bulletList(n: BulletList) = MdBulletList(
        openingMarker = n.openingMarker,
        children = children(n),
    )

    private fun text(n: Text): MdText {
        return MdText(
            text = n.chars.basedSequenceText(),
            children = children(n),
        )
    }

    private fun BasedSequence.basedSequenceText(): String {
        return toString() // TODO is this ok?
    }

    private fun paragraph(n: Paragraph) = MdParagraph(
        lineIndents = n.lineIndents.toList(),
        trailingBlankLine = n.isTrailingBlankLine,
        tableSeparator = n.hasTableSeparator(),
        children = children(n),
    )

    private fun children(n: Node) = n.children.map { node(it) }


    fun document(n: Document) = MdDocument(
        children = children(n),
    )


}
