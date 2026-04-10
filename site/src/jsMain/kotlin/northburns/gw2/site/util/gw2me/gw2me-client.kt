@file:JsModule("@gw2me/client")
@file:JsNonModule
@file:Suppress("unused")

package northburns.gw2.site.util.gw2me

import js.errors.JsError
import web.abort.AbortSignal
import web.credentials.CredentialMediationRequirement
import web.crypto.CryptoKey
import web.crypto.CryptoKeyPair
import web.url.URL
import web.url.URLSearchParams
import kotlin.js.Promise

/*
 * ======================================================================
 *   api.ts
 * ======================================================================
 */
external interface UserResponse {
    val sub: String
    val user: User
    val settings: dynamic?
}

external interface User {
    val id: String
    val name: String
    val email: String?
    val emailVerified: Boolean?
}

external interface AccountsResponse {
    val accounts: Array<Account>
}

external interface Account {
    val id: String
    val name: String
    val shared: Boolean
    val verified: Boolean?
    val displayName: String?
}

external interface SubtokenOptions {
    val permissions: Array<String>?
}

external interface SubtokenResponse {
    val subtoken: String
    val expiresAt: String
}

external interface ApiOptions : Options {
    val dpop: DPoPCallback?
}

external interface Gw2MeApi {
    fun user(): Promise<UserResponse>
    fun saveSettings(settings: dynamic): Promise<Unit>
    fun accounts(): Promise<AccountsResponse>
    fun subtoken(accountId: String, options: SubtokenOptions?): Promise<SubtokenResponse>
}


/*
 * ======================================================================
 *   client.ts
 * ======================================================================
 */
external interface AuthorizationUrlParams {
    @JsName("redirect_uri")
    val redirectUri: String
    val scopes: Array<Scope>
    val state: String?

    @JsName("code_challenge")
    val codeChallenge: String?

    @JsName("code_challenge_method")
    val codeChallengeMethod: String?

    @JsName("dpop_jkt")
    val dpopJkt: String?

    /**
     * Either "none" or "consent"
     */
    val prompt: String?

    @JsName("include_granted_scopes")
    val includeGrantedScopes: Boolean?

    @JsName("verified_accounts_only")
    val verifiedAccountOnly: Boolean?
}

external interface PushedAuthorizationRequestParams : AuthorizationUrlParams {
    val dpop: DPoPCallback?
}

external interface AuthorizationUrlRequestUriParams {
    @JsName("request_uri")
    val requestUri: String
}

external interface AuthTokenParams {
    val code: String

    /**
     * Either "Bearer" or "DPoP"
     */
    @JsName("token_type")
    val tokenType: String?

    @JsName("redirect_uri")
    val redirectUri: String?

    @JsName("code_verifier")
    val codeVerifier: String?

    val dpop: DPoPCallback?
}

external interface RefreshTokenParams {
    @JsName("refresh_token")
    val refreshToken: String

    /**
     * Either "Bearer or "DPoP"
     */
    @JsName("refresh_token_type")
    val refreshTokenType: String?

    val dpop: DPoPCallback?
}

external interface TokenResponse {
    @JsName("access_token")
    val accessToken: String

    /**
     * "urn:ietf:params:oauth:token-type:access_token"
     */
    @JsName("issued_token_type")
    val issuedTokenType: String

    /**
     * Either "Bearer" or "DPoP"
     */
    @JsName("token_type")
    val tokenType: String

    @JsName("expires_in")
    val expiresIn: Number

    @JsName("refresh_token")
    val refreshToken: String?
    val scope: String
}

external interface RevokeTokenParams {
    val token: String
}

external interface IntrospectTokenParams {
    val token: String
}

// TODO export namespace IntrospectTokenResponse {
sealed external interface IntrospectTokenResponse {
    val active: Boolean
}

external interface IntrospectTokenResponseInactive : IntrospectTokenResponse {
    /**
     * Returns false
     */
    override val active: Boolean
}

sealed external interface IntrospectTokenResponseActive : IntrospectTokenResponse {
    /**
     * Returns true
     */
    override val active: Boolean

    val scope: String

    @JsName("client_id")
    val clientId: String
    val exp: Number?

    @JsName("token_type")
    val tokenType: String
}

external interface IntrospectTokenResponseActiveBearer : IntrospectTokenResponseActive {
    /**
     * Returns "Bearer"
     */
    override val tokenType: String
}

external interface IntrospectTokenResponseActiveDPoP : IntrospectTokenResponseActive {
    /**
     * Returns "DPoP"
     */
    override val tokenType: String
    val cnf: IntrospectTokenResponseActiveDPoPCnf
}

external interface IntrospectTokenResponseActiveDPoPCnf {
    val jkt: String
}

external interface PushedAuthorizationRequestResponse {
    @JsName("request_uri")
    val requestUri: String

    @JsName("expires_in")
    val expiresIn: Number
}

external class Gw2MeClient {
    constructor(client: ClientInfo, options: Options?)

    fun getAuthorizationUrl(params: AuthorizationUrlParams): String
    fun getAuthorizationUrl(params: AuthorizationUrlRequestUriParams): String
    fun pushAuthorizationRequest(params: PushedAuthorizationRequestParams): Promise<PushedAuthorizationRequestResponse>
    suspend fun getAccessToken(params: AuthTokenParams): Promise<TokenResponse>
    suspend fun refreshToken(params: RefreshTokenParams): Promise<TokenResponse>
    suspend fun revokeToken(params: RevokeTokenParams)
    suspend fun introspectToken(params: IntrospectTokenParams): IntrospectTokenResponse
    fun parseAuthorizationResponseSearchParams(searchParams: URLSearchParams): AuthorizationResponseParams
    fun api( accessToken: String, options: ApiOptions?): Gw2MeApi
}

external interface AuthorizationResponseParams {
    val code: String
    val state: String?
}

/*
 * ======================================================================
 *   dpop.ts
 * ======================================================================
 */
external fun generateDPoPKeyPair(): CryptoKeyPair
external suspend fun createDPoPJwt(params: DPoPParams, keyPair: CryptoKeyPair)
external suspend fun jwk(key: CryptoKey)
external suspend fun jwkThumbprint(key: CryptoKey): String

/*
 * ======================================================================
 *   error.ts
 * ======================================================================
 */
external interface Gw2MeError // TODO extends Error
external interface Gw2MeOAuthError : Gw2MeError {
    val error: String
    @JsName("error_description")
    val errorDescription: String?
    @JsName("error_uri")
    val errorUri: String?
}

/*
 * ======================================================================
 *   fed-cm.ts
 * ======================================================================
 */
external interface FedCMRequestOptions {
    val scopes: Array<Scope>
    val mediation: CredentialMediationRequirement?

    /**
     * Either "passive" or "active"
     */
    val mode: String?

    val signal: AbortSignal?

    @JsName("code_challenge")
    val codeChallenge: String

    @JsName("code_challenge_method")
    val codeChallengeMethod: String?

    @JsName("include_granted_scopes")
    val includeGrantedScopes: Boolean?
}

external interface Gw2MeFedCMResponse {
    val token: String

    /**
     * Has value "identity"
     */
    val type: String
}

external class Gw2MeFedCM(configUrl: URL, clientId: String) {
    fun isSupported(): Boolean
    fun request(options: FedCMRequestOptions): Promise<Gw2MeFedCMResponse?>
}

/*
 * ======================================================================
 *   types.ts
 * ======================================================================
 */
typealias Scope = String


external interface ClientInfo {
    @JsName("client_id")
    val clientId: String

    @JsName("client_secret")
    val clientSecret: String?
}

external interface Options {
    val url: String?
}

external interface DPoPParams {
    /**
     * "POST" or "GET" or (string & {})
     */
    val html: String
    val htu: String
    val nonce: String?
    val accessToken: String?
}

/**
 * Must return either String or Promise<String>
 */
typealias DPoPCallback = (params: DPoPParams) -> Any
