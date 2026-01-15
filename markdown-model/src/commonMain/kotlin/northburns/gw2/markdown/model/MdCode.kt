package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("code")
data class MdCode(
    val text: String,
    override val children: List<MdNode> = emptyList(),
) : MdNode
