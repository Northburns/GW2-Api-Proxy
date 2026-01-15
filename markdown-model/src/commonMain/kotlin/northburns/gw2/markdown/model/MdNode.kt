package northburns.gw2.markdown.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface MdNode {

    val children: List<MdNode>

}