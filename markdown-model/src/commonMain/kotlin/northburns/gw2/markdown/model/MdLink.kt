package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("link")
data class MdLink(
    val text: String,
    val achorRef: String? = null,
    val pageRef: String? = null,
    val url: String? = null,
    override val children: List<MdNode> = emptyList(),
) : MdNode

