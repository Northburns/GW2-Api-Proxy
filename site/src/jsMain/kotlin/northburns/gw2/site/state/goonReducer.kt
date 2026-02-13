package northburns.gw2.site.state

import northburns.gw2.site.ConduitAction
import northburns.gw2.site.state.GoonAction.ApiDataAction
import northburns.gw2.site.state.GoonAction.FetchItemsSuccess

fun goonReducer(state: GoonState, action: GoonAction): GoonState = when (action) {
    is GoonAction.ResetState -> action.state
    is GoonAction.NavigationAction -> state.copy(
        context = state.context.copy(
            view = action.view,
        )
    )
    is ApiDataAction -> goonReducerApiAction(state, action)
}

fun goonReducerApiAction(state: GoonState, action: ApiDataAction): GoonState = when (action) {
    is FetchItemsSuccess -> state.run {
        copy(
            api = api.run {
                copy(
                    items = items.plus(action.items.associateBy { it.id })
                )
            }
        )
    }
}
