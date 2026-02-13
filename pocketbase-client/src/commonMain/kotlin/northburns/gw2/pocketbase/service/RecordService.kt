package northburns.gw2.pocketbase.service

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.tools.OptionsBuilder
import northburns.gw2.pocketbase.tools.OptionsBuilder.Companion.create
import northburns.gw2.pocketbase.tools.RecordModel

public class RecordService(
    public override val client: PocketBaseClient,
    idOrName: String,
) : CrudService<RecordModel>() {
    public val baseCollectionPath: List<String> = listOf("api", "collections", idOrName)
    override val baseCrudPath: List<String> = baseCollectionPath.plus("records")

    public val isSuperusers: Boolean = "_superusers" == idOrName || "_pbc_2773867675" == idOrName

    public suspend inline fun <reified T : RecordModel> authWithPassword(
        usernameOrEmail: String,
        password: String,
        noinline options: OptionsBuilder.() -> Unit = {},
    ): RecordAuthResponse<T> {
        val authData = client.doHttpRequest<RecordAuthResponse<T>>(options.create()) {
            method = HttpMethod.Post
            url {
                appendPathSegments(baseCollectionPath)
                appendPathSegments("auth-with-password")
            }
            setBody(
                mapOf(
                    "identity" to usernameOrEmail,
                    "password" to password,
                )
            )
        }

        client.authStore.save(authData.token)

        // TODO autoRefreshThreshold && isSuperusers ?

        return authData
    }

    public suspend fun <T : RecordModel> authWithOAuth2(
        options: OAuth2AuthConfig,
    ): RecordAuthResponse<T> {
        return doAuthWithOAuth2(
            self = this,
            client = client,
            options = options,
        )
    }

    public suspend fun listAuthMethods(
        options: OptionsBuilder.() -> Unit,
    ): AuthMethodList {
        TODO()
    }


    public suspend fun <T : RecordModel> subscribe(
        topic: String,
        // options: RecordSubscribeOptions? = null,
        callback: suspend (RecordSubscription<T>) -> Unit
    ): UnsubscribeFunc {
        TODO()
    }

    public suspend fun unsubscribe(topic: String? = null) {
        if (topic != null) {
            TODO()
        } else {
            TODO()
        }
    }

    public data class RecordSubscription<T : RecordModel>(
        /**
         * TODO enum!
         */
        val action: String,
        val record: T,
    )

    public data class OAuth2AuthConfig(
        val provider: String,
        val scopes: List<String>? = null,
        val createData: JsonElement? = null,
        /**
         * The request identifier that can be used to cancel pending requests.
         */
        val requestKey: String? = null,
        val urlCallback: (suspend (url: String) -> Unit)? = null,
        val query: OptionsBuilder.() -> Unit = {},
    )

    @Serializable
    public data class RecordAuthResponse<T : RecordModel>(
        val record: T,
        val token: String,
        val meta: Map<String, JsonElement>? = null,
    )

    @Serializable
    public data class AuthMethodList(
        val mfa: AuthMethodMfa,
        val otp: AuthMethodOtp,
        val password: AuthMethodPassword,
        val oauth2: AuthMethodOAuth2,
    )

    @Serializable
    public data class AuthMethodMfa(
        val enabled: Boolean,
        val duration: Int,
    )

    @Serializable
    public data class AuthMethodOtp(
        val enabled: Boolean,
        val duration: Int,
    )

    @Serializable
    public data class AuthMethodPassword(
        val enabled: Boolean,
        val identityFields: List<String>,
    )

    @Serializable
    public data class AuthMethodOAuth2(
        val enabled: Boolean,
        val providers: List<AuthProviderInfo>,
    )

    @Serializable
    public data class AuthProviderInfo(
        val name: String,
        val displayName: String,
        val state: String,
        val authURL: String,
        val codeVerifier: String,
        val codeChallenge: String,
        val codeChallengeMethod: String,
    )
}