package northburns.gw2.site.app

import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId

@Serializable
data class SessionTrackerState(
    val fromSnapshotId: AccountSnapshotId? = null,
    val fromSnapshot: AccountSnapshot? = null,
)
