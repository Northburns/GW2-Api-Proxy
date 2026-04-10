package northburns.gw2.site.util.gw2me

import js.buffer.ArrayBuffer
import js.iterable.toList
import js.typedarrays.Uint32Array
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.actions.TlsLoggedIn
import northburns.gw2.site.app.AccountData
import northburns.gw2.site.app.FatalGoonError
import northburns.gw2.site.app.GoonErrorThrowable
import northburns.gw2.site.app.GoonRoute
import northburns.gw2.site.app.Gw2MeAccount
import northburns.gw2.site.util.gw2me.Gw2MeUserSettings.Companion.toGoon
import northburns.gw2.site.util.gw2me.MyGw2MeClient.Gw2MeApiAccess.Companion.toApiAccess
import northburns.gw2.site.util.gw2me.MyGw2MeClient.Pkce.Companion.toPkce
import web.crypto.crypto
import web.history.history
import web.storage.sessionStorage
import web.url.URLSearchParams
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant


class MyGw2MeClient(
    val clientId: String = "ee2b9945-77be-4665-9de8-745911a3a0de",
    val redirectUri: String = "http://localhost:3000/"
) {
    val scopes = arrayOf(
        Scope_Identify,
        Scope_Accounts,
        Scope_Accounts_DisplayName,
        Scope_GW2_Account,
        Scope_GW2_Inventories,
        Scope_GW2_Characters,
        Scope_GW2_Tradingpost,
        Scope_GW2_Wallet,
        Scope_GW2_Unlocks,
        Scope_GW2_Pvp,
        Scope_GW2_Wvw,
        Scope_GW2_Builds,
        Scope_GW2_Progression,
        Scope_GW2_Guilds,
    )

    /**
     * If you're calling this, you've determined the user has to log in.
     * Call this method after building the UI.
     * Don't let the UI building go parts of code that assume a user is logged in.
     */
    fun handleNotLoggedIn(promptConsent: Boolean = false) {
        when (val authCallbackParams = getAuthCallbackParams()) {
            // Not logged in + not auth-callback -> start login flow
            null -> Gw2GoonManager.launchJob { doLogin(promptConsent) }
            // Not logged in + auth-callback -> create user object
            else -> Gw2GoonManager.launchJob { onAuthCallback(authCallbackParams) }
        }
    }

    suspend fun getSubtoken(account: Gw2MeAccount): String {
        return access.api.subtoken(account.accountId, null).await().subtoken
    }

    private suspend fun doLogin(promptConsent: Boolean) {
        storeLocation()
        val pkce = generateAndWritePkce() // As per OAuth spec, PKCE generated at the start of auth flow
        val authUrl = getAuthUrl(pkce, promptConsent)
        window.location.href = authUrl
    }

    private suspend fun onAuthCallback(authCallbackParams: AuthorizationResponseParams) {
        val pkce = readPkce() // Make sure the PKCE generated in `doLogin` are in memory
        requireNotNull(pkce) { "Unexpected null Pkce" }
        if (pkce.loginState != authCallbackParams.state) {
            throw IllegalStateException("`state` didn't match -> CSRF")
        }
        access = getAccess(authCallbackParams, pkce)
        readGw2MeData()
    }

    private suspend fun readGw2MeData() {
        val user: UserResponse = access.api.user().await()
        val accounts = access.api.accounts().await()

        val extra: Gw2MeUserSettings? = try {
            val settings = user.settings
            if (settings != null) {
                val load = js("JSON").stringify(settings)
                logger.info("Loading user settings: ", load)
                Gw2Json.json.decodeFromString<Gw2MeUserSettings>(load)
            } else null
        } catch (t: Throwable) {
            logger.error(
                "Error decoding gw2me user settings",
                user.settings, t.stackTraceToString(), t,
            )
            null
        }

        val auth: AccountData = extra.toGoon(
            user = user.user,
            accessToken = access.accessToken,
            refreshToken = access.refreshToken,
            exp = access.exp,
            accounts = accounts,
            accountToInitialSubtoken = { access.api.subtoken(it, null).await() },
        )

        removeGetParams()
        restoreLocation()
        Gw2GoonManager.goonStore.dispatch(TlsLoggedIn(auth))
    }

    @OptIn(ExperimentalWasmJsInterop::class)
    private fun removeGetParams() {
        history.replaceState(null, document.title, window.location.pathname);
    }

    suspend fun saveSettings(extra: Gw2MeUserSettings, initial: AccountData) {
        val settings = Gw2Json.json.encodeToString(extra)
        logger.info("Saving user settings: ", settings)
        val save = js("JSON").parse(settings)
        client.api(initial.auth.accessToken, null).saveSettings(save).await()
        readGw2MeData()
    }

    private fun storeLocation() {
        val loc = Gw2Json.json.encodeToString(Gw2GoonManager.goonStore.store.state.currentView)
        sessionStorage.setItem("navigation", loc)
    }

    private fun restoreLocation() {
        sessionStorage.getItem("navigation")?.let { loc ->
            val lastView = Gw2Json.json.decodeFromString<GoonRoute>(loc)
            logger.info("Restoring session location: ", loc, lastView)
            Gw2GoonManager.goonNav.navigateTo(lastView)
        }
    }

    /*
     * ======================================================================
     *   Private:
     * ======================================================================
     */

    @OptIn(ExperimentalWasmJsInterop::class)
    private fun getAuthCallbackParams(): AuthorizationResponseParams? {
        val searchParams = URLSearchParams(window.location.search)

        // Detect that we are in auth callback:
        val isCallback = searchParams.has("iss") // code param not when error
        if (!isCallback) return null

        // Use gw2me client to read (and validate) callback params
        try {
            val params = client.parseAuthorizationResponseSearchParams(searchParams)
            logger.info("Auth Callback Params: ", params)
            return params
        } catch (e: dynamic) {
            val e = e.unsafeCast<Gw2MeOAuthError>()
            throw GoonErrorThrowable(
                FatalGoonError(
                    message = e.error,
                    description = e.errorDescription ?: "",
                    extra = JSON.stringify(e, emptyArray(), 2)
                )
            )
        }
    }

    private val logger = GoonLog["MyGw2MeClient"]
    private val client: Gw2MeClient = Gw2MeClient(
        client = object : ClientInfo {
            override val clientId = this@MyGw2MeClient.clientId
            override val clientSecret = null
        },
        options = null,
    )
    private lateinit var access: Gw2MeApiAccess

    private fun readPkce(): Pkce? {
        val challenge = sessionStorage.getItem("pkce-challenge")
        val method = sessionStorage.getItem("pkce-challenge-method")
        val verifier = sessionStorage.getItem("pkce-challenge-verifier")
        val loginState = sessionStorage.getItem("oauth-login-state")
        if (challenge == null || method == null || verifier == null || loginState == null) return null
        return Pkce(challenge = challenge, challengeMethod = method, verifier = verifier, loginState = loginState)
    }

    private suspend fun generateAndWritePkce(): Pkce {
        /*
         * If/when we use PocketBase backend, I think we can reuse everything here,
         * but just take PKCEPair values from listAuthenticationMethods
         * https://pocketbase.io/docs/api-records/#list-auth-methods
         * so that authWithOAuth2Code uses the correct PKCE values
         * https://pocketbase.io/docs/authentication/#authenticate-with-oauth2
         *
         * Uhm, but is that cool? Using PKCE values fetched from outside browser?...
         * Not ideal, but should be fine. PocketBase doesn't store the generated PKCE values.
         * Also, because of that, I can just override them with values I generate my self.
         */
        val array = Uint32Array<ArrayBuffer>(16)
        crypto.getRandomValues(array)
        val loginState = array.keys().toList()
            .joinToString(separator = "") { array[it].toString(16) }
        val pkce = generatePKCEPair().await().toPkce(loginState)
        sessionStorage.setItem("pkce-challenge", pkce.challengeMethod)
        sessionStorage.setItem("pkce-challenge-method", pkce.challengeMethod)
        sessionStorage.setItem("pkce-challenge-verifier", pkce.verifier)
        sessionStorage.setItem("oauth-login-state", loginState)
        return pkce
    }

    private class Pkce(
        val challenge: String,
        val challengeMethod: String,
        val verifier: String,
        /**
         * Included in this class strictly as a convenience
         */
        val loginState: String,
    ) {
        companion object {
            fun PKCEPair.toPkce(loginState: String) = Pkce(
                challenge = challenge.codeChallenge,
                challengeMethod = challenge.codeChallengeMethod,
                verifier = codeVerifier,
                loginState = loginState,
            )
        }
    }

    private class Gw2MeApiAccess(
        val accessToken: String,
        val refreshToken: String?,
        val exp: Instant,
        private val client: Gw2MeClient,
    ) {
        val api: Gw2MeApi by lazy { client.api(accessToken, null) }

        suspend fun refresh(): Gw2MeApiAccess {
            val refresh = refreshToken
            requireNotNull(refresh) { "Refresh token is null" }
            return client.refreshToken(object : RefreshTokenParams {
                override val refreshToken: String = refresh
                override val refreshTokenType = null
                override val dpop = null
            }).await().toApiAccess(client)
        }

        companion object {
            fun TokenResponse.toApiAccess(client: Gw2MeClient): Gw2MeApiAccess {
                val exp = Clock.System.now() + expiresIn.toLong().seconds
                return Gw2MeApiAccess(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    exp = exp,
                    client = client,
                )
            }
        }
    }

    private fun getAuthUrl(pkce: Pkce, promptConsent: Boolean): String {
        return client.getAuthorizationUrl(
            object : AuthorizationUrlParams {
                override val redirectUri = this@MyGw2MeClient.redirectUri
                override val scopes = this@MyGw2MeClient.scopes
                override val state = pkce.loginState
                override val codeChallenge = pkce.challenge
                override val codeChallengeMethod = pkce.challengeMethod
                override val dpopJkt = null
                override val prompt = if (promptConsent) "consent" else "none"
                override val includeGrantedScopes = null
                override val verifiedAccountOnly = null
            })
    }

    private suspend fun getAccess(
        authCallbackParams: AuthorizationResponseParams,
        pkce: Pkce,
    ): Gw2MeApiAccess {
        return client.getAccessToken(object : AuthTokenParams {
            override val code = authCallbackParams.code
            override val tokenType = null
            override val redirectUri = this@MyGw2MeClient.redirectUri
            override val codeVerifier = pkce.verifier
            override val dpop = null
        }).await().toApiAccess(client)
    }


}

