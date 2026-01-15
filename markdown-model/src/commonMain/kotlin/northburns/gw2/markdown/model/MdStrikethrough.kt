package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("strikethrough")
data class MdStrikethrough(
    val text: String,
    override val children: List<MdNode> =emptyList(),
) : MdNode
