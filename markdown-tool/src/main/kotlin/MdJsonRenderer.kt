package northburns.gw2

import com.vladsch.flexmark.ast.BlockQuote
import com.vladsch.flexmark.ast.BulletList
import com.vladsch.flexmark.ast.Code
import com.vladsch.flexmark.ast.Emphasis
import com.vladsch.flexmark.ast.FencedCodeBlock
import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.ast.Image
import com.vladsch.flexmark.ast.IndentedCodeBlock
import com.vladsch.flexmark.ast.Link
import com.vladsch.flexmark.ast.LinkNodeBase
import com.vladsch.flexmark.ast.ListItem
import com.vladsch.flexmark.ast.OrderedList
import com.vladsch.flexmark.ast.Paragraph
import com.vladsch.flexmark.ast.RefNode
import com.vladsch.flexmark.ast.Reference
import com.vladsch.flexmark.ast.SoftLineBreak
import com.vladsch.flexmark.ast.StrongEmphasis
import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.ast.ThematicBreak
import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough
import com.vladsch.flexmark.ext.tables.TableBlock
import com.vladsch.flexmark.ext.tables.TableBody
import com.vladsch.flexmark.ext.tables.TableCell
import com.vladsch.flexmark.ext.tables.TableHead
import com.vladsch.flexmark.ext.tables.TableRow
import com.vladsch.flexmark.ext.tables.TableSeparator
import com.vladsch.flexmark.ext.xwiki.macros.Macro
import com.vladsch.flexmark.ext.xwiki.macros.MacroAttribute
import com.vladsch.flexmark.ext.xwiki.macros.MacroBlock
import com.vladsch.flexmark.ext.xwiki.macros.MacroClose
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock
import com.vladsch.flexmark.util.ast.DelimitedNode
import com.vladsch.flexmark.util.ast.Document
import com.vladsch.flexmark.util.ast.IRender
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.ast.TextContainer
import com.vladsch.flexmark.util.data.DataHolder
import com.vladsch.flexmark.util.data.DataKey
import com.vladsch.flexmark.util.data.DataSet
import com.vladsch.flexmark.util.sequence.BasedSequence
import com.vladsch.flexmark.util.sequence.SubSequence
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import northburns.gw2.markdown.model.JsonMd

class MdJsonRenderer : IRender {
    private val opts: DataHolder = DataSet.NULL

    override fun render(document: Node, output: Appendable) {
        require(document is Document)
        val obj = renderNode(document)
        val s = JsonMd.json.encodeToString(obj)
        output.append(s)
    }

    override fun getOptions(): DataHolder {
        return this.opts
    }

    private fun renderNode(node: Node): JsonObject {
        return buildJsonObject {
            put("_", node.nodeName)
            renderNodeInner(node)
            if (node.hasChildren()) // Other empty arrays are ok, but empty child arrays are too common to include.
                put(">", node.children.renderAny())
        }
    }

    private fun JsonObjectBuilder.renderNodeInner(n: Node) {
        if (n is TextContainer) put("text", n.collectTextSimple())
        if (n is DelimitedNode) put("text", n.text.renderAny())

        when (n) {
            is Document -> {
                // b.put("dataset", n.all.renderAny())
            }

            is LinkNodeBase -> {
                put("urlOpeningMarker", n.urlOpeningMarker.renderAny())
                put("url", n.url.renderAny())
                put("pageRef", n.pageRef.renderAny())
                put("anchorMarker", n.anchorMarker.renderAny())
                put("anchorRef", n.anchorRef.renderAny())
                put("urlClosingMarker", n.urlClosingMarker.renderAny())
                put("titleOpeningMarker", n.titleOpeningMarker.renderAny())
                put("title", n.title.renderAny())
                put("titleClosingMarker", n.titleClosingMarker.renderAny())
                when (n) {
                    is Link -> {
                        // TODO
                    }

                    is Image -> {
                        // TODO
                    }

                    is Reference -> {
                        // TODO
                    }

                    else -> throw IllegalArgumentException("Unexpected node type: ${n.javaClass}")
                }
            }

            is Paragraph -> {
                put("lineIndents", n.lineIndents.renderAny())
                put("isTrailingBlankLine", n.isTrailingBlankLine)
                put("hasTableSeparator", n.hasTableSeparator())
            }

            is BulletList -> {
                put("openingMarker", n.openingMarker.renderAny())
            }

            is ListItem -> {
                put("openingMarker", n.openingMarker.renderAny())
                put("markerSuffix", n.markerSuffix.renderAny())
                put("tight", n.isTight)
                put("hadBlankAfterItemParagraph", n.isHadBlankAfterItemParagraph)
                put("containsBlankLine", n.isContainsBlankLine)
                put("priority", n.priority)
            }

            is ThematicBreak -> {}
            is Heading -> {
                put("level", n.level)
                put("anchorRefId", n.anchorRefId)
            }

            is BlockQuote -> {
                put("openingMarker", n.openingMarker.renderAny())
            }

            is OrderedList -> {
                put("startNumber", n.startNumber)
                put("delimiter", n.delimiter.renderAny())
            }

            is IndentedCodeBlock -> {}
            is FencedCodeBlock -> {
                put("fenceIndent", n.fenceIndent)
                put("openingMarker", n.openingMarker.renderAny())
                put("info", n.info.renderAny())
                put("attributes", n.attributes.renderAny())
                put("closingMarker", n.closingMarker.renderAny())
            }

            is TableBlock -> {}
            is YamlFrontMatterBlock -> {}

            is Text -> {}

            is Emphasis -> {}
            is StrongEmphasis -> {}
            is SoftLineBreak -> {}
            is Strikethrough -> {}
            is Code -> {}
            is TableCell -> {
                put("header", n.isHeader)
                put("alignment", n.alignment?.name)
                put("span", n.span)
            }

            is TableHead -> {}
            is TableRow -> {
                put("rowNumber", n.rowNumber)
            }

            is TableSeparator -> {}
            is TableBody -> {}
            is RefNode -> {
                put("isDefined", n.isDefined)
            }

            is MacroBlock -> {
                put("macroContent", n.getMacroContentChars().asString())
            }

            is Macro -> {
                put("name", n.name.asString())
                // put("attributeText", n.attributeText.asString())
                put("macroContent", n.macroContentChars.asString())
            }

            is MacroAttribute -> {
                put("attribute", n.attribute.asString())
                put("separator", n.separator.asString())
                put("value", n.value.asString())
            }

            is MacroClose -> {
                put("name", n.name.asString())
            }

            else -> throw IllegalArgumentException("Unexpected node type: ${n.javaClass}")
        }
    }

    private fun Any.renderAny(): JsonElement {
        return when (this) {
            is JsonElement -> this
            is Boolean -> JsonPrimitive(this)
            is Char -> JsonPrimitive(this.toString())
            is String -> JsonPrimitive(this)
            is Number -> JsonPrimitive(this)
            is Node -> renderNode(this)
            is Array<*> -> JsonArray(this.map { it?.renderAny() ?: JsonNull })
            is IntArray -> JsonArray(this.map { it.renderAny() })
            is Iterable<*> -> JsonArray(this.map { it?.renderAny() ?: JsonNull })
            is Map<*, *> -> JsonObject(this.mapKeys { (k, _) -> k!!.asString() }
                .mapValues { (_, v) -> v?.renderAny() ?: JsonNull }.cleanup())

            is SubSequence -> this.asString().renderAny()
            is BasedSequence -> this.asString().renderAny()

            else -> throw IllegalArgumentException("Unexpected type: $javaClass")
        }
    }

    private fun TextContainer.collectTextSimple(): String {
        val out: ISequenceBuilder<*, BasedSequence> = SequenceBuilder.emptyBuilder(BasedSequence.EmptyBasedSequence())
        collectText(out, TextContainer.F_NODE_TEXT, null);
        return out.toSequence().asString()

        //return "TODO"
    }

    private fun Any.asString(): String = when (this) {
        is String -> this
        is DataKey<*> -> this.name
        is SubSequence -> this.toString() // TODO is this correct?
        is BasedSequence -> this.toString() // TODO is this correct?
        else -> throw IllegalArgumentException("Unexpected type: $javaClass")
    }

    private fun Map<String, JsonElement>.cleanup() =
        this.filterValues { it !is JsonNull }.filterValues { if (it is JsonArray) it.isNotEmpty() else true }

}