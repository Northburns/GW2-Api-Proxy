package northburns.gw2.site.app

import arrow.optics.optics
import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot

@JsExport
@Serializable
@optics
data class SnapshotsContainer(
    val snapshots: Map<PlayerId, AccountSnapshot> = emptyMap(),
) {

    val achievementsByPlayer by lazy { snapshots.mapValues { (_, v) -> v.achievements.value } }


    companion object
}
