package northburns.gw2.markdown.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("h")
data class MdHeading(
    val text : String,
    val level : Int,
    val anchorRefId : String,
    override val children: List<MdNode> = emptyList(),
) : MdNode

