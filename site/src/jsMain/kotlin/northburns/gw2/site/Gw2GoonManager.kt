package northburns.gw2.site

import io.github.aakira.napier.Napier
import io.kvision.pace.Pace
import io.kvision.redux.TypedReduxStore
import io.kvision.redux.createTypedReduxStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import northburns.gw2.site.state.GoonAction
import northburns.gw2.site.state.GoonState
import northburns.gw2.site.state.Gw2GoonNavigation
import northburns.gw2.site.state.goonReducer


object Gw2GoonManager : CoroutineScope by CoroutineScope(Dispatchers.Default + SupervisorJob()) {

    val api = Api(this)


    val goonStore: TypedReduxStore<GoonState, GoonAction> = createTypedReduxStore(::goonReducer, GoonState())

    val goonNav = Gw2GoonNavigation()

    fun initialize() {
        goonNav.initialize()
    }


    // PROGRESS

    var progressCount = 0

    fun withProgress(block: suspend CoroutineScope.() -> Unit): Job {
        return withProgressValue(block)
    }

    fun <T> withProgressValue(block: suspend CoroutineScope.() -> T): Deferred<T> {
        Pace.show()
        progressCount++
        return async {
            try {
                block().also {
                    progressCount--
                    if (progressCount <= 0) Pace.hide()
                }
            } catch (e: Throwable) {
                Napier.e("Exception in 'withProgress'", e)
                progressCount--
                if (progressCount <= 0) Pace.hide()
                throw e
            }
        }
    }

}

