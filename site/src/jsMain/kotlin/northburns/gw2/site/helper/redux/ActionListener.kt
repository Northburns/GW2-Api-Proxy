package northburns.gw2.site.helper.redux

import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.helper.redux.MiddlewareFactory.middlewareTyped
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store
import org.reduxkotlin.TypedDispatcher

interface ActionListener {

    suspend fun afterAction(action: GoonAction, state: GoonState, dispatch: TypedDispatcher<GoonAction>)

    companion object {
        fun createMiddleware(vararg listeners: ActionListener): Middleware<GoonState> {
            return middlewareTyped { store: Store<GoonState>, next, action: GoonAction ->
                val ret = next(action)
                val state = store.state
                listeners.forEach { listener ->
                    Gw2GoonManager.launchJob {
                        listener.afterAction(action, state, store.dispatch)
                    }
                }
                ret
            }
        }
    }

}
