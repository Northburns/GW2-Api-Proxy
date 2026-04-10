package northburns.gw2.site.app.sessiontracker

import northburns.gw2.site.Api
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.actions.SessionTrackerViewAction
import northburns.gw2.site.actions.SnapshotIdSelected
import northburns.gw2.site.actions.SnapshotLoaded
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.SessionTrackerState
import northburns.gw2.site.helper.redux.ActionListener
import org.reduxkotlin.TypedDispatcher

fun sessiontrackerReducer(state: SessionTrackerState, action: SessionTrackerViewAction): SessionTrackerState =
    when (action) {
        is SnapshotIdSelected -> state.copy(
            fromSnapshotId = action.snapshotId,
            fromSnapshot = null
        )

        is SnapshotLoaded -> state.copy(
            fromSnapshotId = action.snapshot.id,
            fromSnapshot = action.snapshot
        )
    }

class SessionTrackerActionListener(
    val api: Api,
) : ActionListener {
    override suspend fun afterAction(
        action: GoonAction,
        state: GoonState,
        dispatch: TypedDispatcher<GoonAction>
    ) {

        if (action is SnapshotIdSelected) {
            val snapshot = api.snapshotService.getAccountSnapshot(action.playerId, action.snapshotId)
            if (snapshot != null) dispatch(SnapshotLoaded(snapshot))
        }

    }

}
