package northburns.gw2.markdown.model

import kotlinx.serialization.Serializable

@Serializable
data class MdThematicBreak(
    override val children: List<MdNode> = emptyList(),
) : MdNode
