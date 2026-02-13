package northburns.gw2.pocketbase.service

import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.service.RecordService.OAuth2AuthConfig
import northburns.gw2.pocketbase.service.RecordService.RecordAuthResponse
import northburns.gw2.pocketbase.tools.RecordModel
import org.w3c.dom.Window

public actual suspend fun <T : RecordModel> doAuthWithOAuth2(
    self: RecordService,
    client: PocketBaseClient,
    options: OAuth2AuthConfig
): Deferred<RecordAuthResponse<T>> {
    // This implementation follow's Pocketbase SDK's implementation very closely.

    // Pocketbase SDK:
    // > open a new popup window in case config.urlCallback is not set
    // >
    // > note: it is opened before any async calls due to Safari restrictions
    // > (see https://github.com/pocketbase/pocketbase/discussions/2429#discussioncomment-5943061)
    val eagerDefaultPopup =
        if (options.urlCallback == null) {
            openBrowserPopup("")
        } else null

    // initialize a one-off realtime service
    val realtime = RealtimeService(client)

    val cleanup: () -> Unit = {
        eagerDefaultPopup?.close()
        realtime.unsubscribe()
    }

    try {
        val authMethods = self.listAuthMethods {
            requestKey = options.requestKey
        }

        val provider = requireNotNull(
            authMethods.oauth2.providers.find { it.name == options.provider }
        ) { "Missing or invalid provider '${options.provider}'" }

        val redirectURL = buildUrl {
            takeFrom(client.baseUrl)
            appendPathSegments("api", "oauth2-redirect")
        }

        val deferred: Deferred<String> = coroutineScope {
            async {

                // TODO cancelController to cancel this..
                // TODO onDisconnect to cancel this..


                try {
                    //realtime.subscribe("@oauth2") { e ->
                    //}
                    TODO("Totally not implemented yet...")
                } catch (t: Throwable) {
                    cleanup()
                    throw t
                }
            }
        }

    } catch (t: Throwable) {
        cleanup()
        throw t
    }

    TODO("Not yet implemented")
}

private fun openBrowserPopup(url: String): Window {
    val windowWidth = window.innerWidth
    val windowHeight = window.innerHeight

    val width = minOf(1024, windowWidth)
    val height = minOf(768, windowHeight)

    val left = windowWidth / 2 - width / 2
    val top = windowHeight / 2 - height / 2

    // From Pocketbase SDK's implementation:
    // > note: we don't use the noopener and noreferrer attributes since
    // > for some reason browser blocks such windows then url is undefined/blank
    val w = window.open(
        url = url,
        target = "popup_window",
        features = "width=$width,height=$height,top=$top,left=$left,resizable,menubar=no",
    )
    return requireNotNull(w) { "Opening popup window failed for some reason." }
}
