package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("document")
data class MdDocument(
    override val children: List<MdNode> = emptyList(),
) : MdNode {
}