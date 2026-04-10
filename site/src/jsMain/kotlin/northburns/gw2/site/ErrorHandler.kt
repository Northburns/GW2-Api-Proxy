package northburns.gw2.site

import kotlinx.coroutines.CoroutineExceptionHandler
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.actions.FatalError
import northburns.gw2.site.app.FatalGoonError
import northburns.gw2.site.app.GoonErrorThrowable
import kotlin.coroutines.CoroutineContext

object ErrorHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler
    private val logger = GoonLog["ErrorHandler"]

    override fun handleException(context: CoroutineContext, exception: Throwable): Unit =
        handleException(exception)

    fun handleException(exception: Throwable) {
        logger.warn("Handling exception")
        if (exception is GoonErrorThrowable) {
            when (val error = exception.error) {
                is FatalGoonError -> halt(error)
            }
        } else {
            // TODO make a generic exception handling in UI.
            //   Actually, I think I should have explicit non-Fatal error type!
            //   Anything unexpected is fatal. Yeah, that's the way to go here!
            logger.error(exception)
        }
    }

    private fun halt(error: FatalGoonError) {
        logger.error("""
            
            =========================================================
               Fatal Error: ${error.message}
            =========================================================
        """.trimIndent(), error)
        Gw2GoonManager.goonStore.dispatch(FatalError(error))
        Gw2GoonManager.halt()
    }
}
