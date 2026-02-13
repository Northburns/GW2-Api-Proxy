package northburns.gw2.site.state

import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2Item
import io.kvision.redux.RAction

sealed interface GoonAction : RAction {

    data class ResetState(val state: GoonState) : GoonAction

    data class NavigationAction(val view: Gw2GoonView) : GoonAction


    sealed interface ApiDataAction: GoonAction

    data class FetchItemsSuccess(val items: Collection<GW2v2Item>) : ApiDataAction

}
