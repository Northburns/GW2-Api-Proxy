package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ol")
data class MdOrderedList(
    val startNumber: Int,
    val delimiter : Char,
    override val children: List<MdNode> = emptyList(),
): MdNode

