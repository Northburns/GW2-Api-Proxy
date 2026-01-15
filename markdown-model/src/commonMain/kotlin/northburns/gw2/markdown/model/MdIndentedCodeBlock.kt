package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("IndentedCodeBlock")
data class MdIndentedCodeBlock(
    val text: String,
    override val children: List<MdNode> =emptyList(),
): MdNode
