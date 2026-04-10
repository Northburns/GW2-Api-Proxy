@file:JsModule("@gw2me/client/pkce")
@file:JsNonModule
@file:Suppress("unused")

package northburns.gw2.site.util.gw2me

import web.abort.AbortSignal
import web.credentials.CredentialMediationRequirement
import web.crypto.CryptoKey
import web.crypto.CryptoKeyPair
import web.url.URL
import kotlin.js.Promise

/*
 * ======================================================================
 *   pkce.ts
 * ======================================================================
 */
external interface PKCEChallenge {
    @JsName("code_challenge")
    val codeChallenge: String

    /**
     * Value of "S256"
     */
    @JsName("code_challenge_method")
    val codeChallengeMethod: String
}

external interface PKCEPair {
    val challenge: PKCEChallenge

    @JsName("code_verifier")
    val codeVerifier: String
}

external suspend fun generatePKCEPair(): Promise<PKCEPair>
