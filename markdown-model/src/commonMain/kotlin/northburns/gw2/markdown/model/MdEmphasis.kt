package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("i")
data class MdEmphasis(
    val text: String,
    override val children: List<MdNode> =emptyList(),
) : MdNode
