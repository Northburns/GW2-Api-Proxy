package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("p")
data class MdParagraph(
    val lineIndents: List<Int>,
    val trailingBlankLine: Boolean,
    val tableSeparator: Boolean,
    override val children: List<MdNode> =emptyList(),
): MdNode
