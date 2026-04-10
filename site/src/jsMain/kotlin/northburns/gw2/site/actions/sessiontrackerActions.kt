package northburns.gw2.site.actions

import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot

sealed interface SessionTrackerViewAction: GoonAction

@Serializable
data class SnapshotIdSelected(
    val playerId: PlayerId,
    val snapshotId: AccountSnapshot.AccountSnapshotId,
) : SessionTrackerViewAction
@Serializable
data class SnapshotLoaded(val snapshot: AccountSnapshot) : SessionTrackerViewAction
