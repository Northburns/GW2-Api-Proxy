package northburns.gw2.site.state

import northburns.gw2.site.ConduitAction

fun goonReducer(state: GoonState, action: GoonAction): GoonState = when (action) {
    is GoonAction.ResetState -> action.state
    is GoonAction.NavigationAction -> state.copy(
        context = state.context.copy(
            view = action.view,
        )
    )
}
