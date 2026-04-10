package northburns.gw2.site

import io.kvision.redux.TypedReduxStore
import io.kvision.redux.createTypedReduxStore
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.markdown.GoonMarkGenerator
import northburns.gw2.site.actions.ApiDataAction
import northburns.gw2.site.actions.CommonActions
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.actions.GoonActionListener
import northburns.gw2.site.actions.NavigationAction
import northburns.gw2.site.actions.SnapshotAction
import northburns.gw2.site.app.GoonErrorThrowable
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.RequestService
import northburns.gw2.site.app.goonReducer
import northburns.gw2.site.app.sessiontracker.SessionTrackerActionListener
import northburns.gw2.site.app.snapshotstatus.SnapshotService
import northburns.gw2.site.helper.redux.ActionListener
import northburns.gw2.site.helper.redux.ReduxLogger
import northburns.gw2.site.util.gw2me.MyGw2MeClient
import northburns.gw2.site.util.markdown.KVMarkdownGeneratorContext
import northburns.gw2.site.util.reselect.ReSelectGlobalState
import org.w3c.dom.ErrorEvent

object Gw2GoonManager :
    CoroutineScope by CoroutineScope(
        Dispatchers.Default + SupervisorJob() + ErrorHandler,
    ) {

    private val logger = GoonLog["Gw2GoonManager"]

    val gw2me = MyGw2MeClient()
    val playerDataRead = PlayerDataRead(this)

    val api = Api(this)
    val localStorageService = LocalStorageService()

    val goonStore: TypedReduxStore<GoonState, GoonAction> = createTypedReduxStore(
        ::goonReducer,
        GoonState(),
        ReduxLogger.create(
            predicate = { state, action ->
                when (action) {
                    is SnapshotAction<*> -> false
                    is NavigationAction -> true
                    is CommonActions -> false
                    is ApiDataAction -> false
                    else -> true
                }
            },
            diff = false,
        ),
        ActionListener.createMiddleware(
            GoonActionListener(api),
            SessionTrackerActionListener(api),
        ),
    )

    val r = RequestService(this, api.client)
    val snapshotService = SnapshotService(this, goonStore, r)

    val goonNav = GW2GoonNavigator()

    val markdown = GoonMarkGenerator(KVMarkdownGeneratorContext)

    fun initialize() {
        setupGlobalErrorHandler()
        ReSelectGlobalState.gs = goonStore
        goonNav.resolve()
        playerDataRead.initialize()
    }

    fun halt() {
        // TODO Halt all flows, services, etc
        console.warn("TODO halt everything")
    }


    // PROGRESS


    fun launchJob(block: suspend CoroutineScope.() -> Unit): Job {
        // https://kotlinlang.org/docs/exception-handling.html#exception-propagation
        // "propagating exceptions automatically (launch) or exposing them to users (async and produce)"
        return launch(ErrorHandler) {
            try {
                block()
            } catch (e: Throwable) {
                GoonLog["Gw2GoonManager"].error("Exception in 'GoonManager.launchJob': ${e.stackTraceToString()}", e)
                throw e
            }
        }
    }
//    var progressCount = 0
//
//    fun withProgress(block: suspend CoroutineScope.() -> Unit): Job {
//        return withProgressValue(block)
//    }
//
//    fun <T> withProgressValue(block: suspend CoroutineScope.() -> T): Deferred<T> {
//        Pace.show()
//        progressCount++
//        return async {
//            try {
//                block().also {
//                    progressCount--
//                    if (progressCount <= 0) Pace.hide()
//                }
//            } catch (e: Throwable) {
//                Napier.e("Exception in 'withProgress'", e)
//                progressCount--
//                if (progressCount <= 0) Pace.hide()
//                throw e
//            }
//        }
//    }

    fun setupGlobalErrorHandler() {
        window.addEventListener("error", { event ->
            ((event as? ErrorEvent)?.error as? GoonErrorThrowable)?.also {
                ErrorHandler.handleException(it)
            } ?: logger.error("Global error: ", event)
        })

        window.addEventListener("unhandledrejection", { event ->
            ((event as? ErrorEvent)?.error as? GoonErrorThrowable)?.also {
                ErrorHandler.handleException(it)
            } ?: logger.error("Unhandled promise rejection: ", event)
        })
    }
}
