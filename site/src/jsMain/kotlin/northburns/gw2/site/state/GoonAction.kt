package northburns.gw2.site.state

import io.kvision.redux.RAction

sealed interface GoonAction : RAction {

    data class ResetState(val state: GoonState) : GoonAction

    data class NavigationAction(val view: Gw2GoonView) : GoonAction


}
