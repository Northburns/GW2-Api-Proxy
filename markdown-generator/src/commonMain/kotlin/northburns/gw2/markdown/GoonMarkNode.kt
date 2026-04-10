package northburns.gw2.markdown

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.CompositeASTNode

class GoonMarkNode(
    val type: GoonMarkElementType,
    private val markdownText: () -> String,
    val startOffset: Int,
    val endOffset: Int,
    val children: List<GoonMarkNode>,
) {

    val srcPosAttributeName = "md-src-pos"
    val srcPosAttributeValue
        get() = "${startOffset}..${endOffset}"

    fun txt() = markdownText().substring(startOffset, endOffset)

    fun createChildrenConsumer() = NodeChildrenConsumer()

    inner class NodeChildrenConsumer {
        var consumed = false

        fun expectNoChildren() = expectChildren()
        fun expectChildren(vararg types: GoonMarkElementType?): List<GoonMarkNode> {
            require(types.size == children.size) { "Node[$type] expected to have ${types.size} children, has ${children.size}: ${children.map { it.type }}" }
            types.forEachIndexed { index, expectedType ->
                if (expectedType != null) {
                    val actualType = children[index].type
                    require(expectedType == actualType) { "Node[$type].child[$index], expected type $expectedType, actual types: ${children.map { it.type }}" }
                }
            }
            consumed = true
            return children
        }
    }


    companion object {
        internal fun fromASTNode(node: ASTNode, markdownText: () -> String): GoonMarkNode {
            val type = GoonMarkElementType.get(node.type)
            return GoonMarkNode(
                type,
                markdownText,
                node.startOffset,
                node.endOffset,
                if (node is CompositeASTNode) node.children.map { fromASTNode(it, markdownText) } else emptyList(),
            )
        }
    }

}