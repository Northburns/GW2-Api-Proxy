package northburns.gw2.client.myclient.gw2e

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Gw2eAccountSnapshotRef(
    @SerialName("_id")
    val id: String,
    val creationDate: Instant,
)
