package northburns.gw2.markdown.model

import kotlinx.serialization.Serializable

@Serializable
data class MdSoftLineBreak(
    override val children: List<MdNode> = emptyList(),
) : MdNode
