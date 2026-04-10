package northburns.gw2.site.actions

import northburns.gw2.site.Api
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.helper.redux.ActionListener
import org.reduxkotlin.TypedDispatcher

class GoonActionListener(
    val api: Api,
): ActionListener {
    override suspend fun afterAction(
        action: GoonAction,
        state: GoonState,
        dispatch: TypedDispatcher<GoonAction>,
    ) {
        // NOP
    }
}