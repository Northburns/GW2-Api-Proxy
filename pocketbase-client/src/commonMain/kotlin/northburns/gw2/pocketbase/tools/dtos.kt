package northburns.gw2.pocketbase.tools

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement


public interface BaseModel {
    public val id: String?
}

@Serializable
public open class RecordModel : BaseModel {
    override val id: String? = null
    public val collectionId: String? = null
    public val collectionName: String? = null

    init {
        id?.let { require(it.length == 15) }
    }
}

@Serializable
public data class ListResult<T>(
    val page: Int,
    val perPage: Int,
    val totalItems: Int,
    val totalPages: Int,
    val items: List<T>,
)

@Serializable
public data class CollectionField(
    val name: String,
    val type: String,
    val system: Boolean? = null,
    val hidden: Boolean? = null,
    val presentable: Boolean? = null,
    val required: Boolean? = null,
    val min: Int? = null,
    val id: String? = null,
)

@Serializable
public sealed interface CollectionModel : BaseModel {
    public val name: String
    public val fields: List<CollectionField>?
    public val indexes: List<String>?
    public val system: Boolean?
    public val listRule: String?
    public val viewRule: String?
    public val createRule: String?
    public val updateRule: String?
    public val deleteRule: String?
}

@Serializable
@SerialName("base")
public data class BaseCollectionModel(
    override val name: String,
    override val fields: List<CollectionField>? = null,
    val schema: List<CollectionField>? = null,
    override val indexes: List<String>? = null,
    override val system: Boolean? = null,
    override val listRule: String? = null,
    override val viewRule: String? = null,
    override val createRule: String? = null,
    override val updateRule: String? = null,
    override val deleteRule: String? = null,
    override val id: String? = null,
) : CollectionModel

@Serializable
@SerialName("view")
public data class ViewCollectionModel(
    val viewQuery: String,
    override val name: String,
    override val fields: List<CollectionField>? = null,
    override val indexes: List<String>? = null,
    override val system: Boolean? = null,
    override val listRule: String? = null,
    override val viewRule: String? = null,
    override val createRule: String? = null,
    override val updateRule: String? = null,
    override val deleteRule: String? = null,
    override val id: String? = null,
) : CollectionModel

@Serializable
@SerialName("auth")
public data class AuthCollectionModel(
    override val name: String,
    override val fields: List<CollectionField>,
    override val indexes: List<String>? = null,
    override val system: Boolean? = null,
    override val listRule: String? = null,
    override val viewRule: String? = null,
    override val createRule: String? = null,
    override val updateRule: String? = null,
    override val deleteRule: String? = null,
    override val id: String? = null,

    val authRule: String? = null,
    val manageRule: String? = null,
    val authAlert: AuthAlertConfig? = null,
    val oauth2: OAuth2Config? = null,
    val passwordAuth: PasswordAuthConfig? = null,
    val mfa: MFAConfig? = null,
    val otp: OTPConfig? = null,

    val authToken: TokenConfig? = null,
    val passwordResetToken: TokenConfig? = null,
    val emailChangeToken: TokenConfig? = null,
    val verificationToken: TokenConfig? = null,
    val fileToken: TokenConfig? = null,

    val verificationTemplate: EmailTemplate? = null,
    val resetPasswordTemplate: EmailTemplate? = null,
    val confirmEmailChangeTemplate: EmailTemplate? = null,
) : CollectionModel

@Serializable
public data class TokenConfig(
    val duration: Int,
    val secret: String? = null,
)

@Serializable
public data class AuthAlertConfig(
    val enabled: Boolean,
    val emailTemplate: EmailTemplate,
)

@Serializable
public data class OTPConfig(
    val enabled: Boolean,
    val duration: Int,
    val length: Int,
    val emailTemplate: EmailTemplate,
)

@Serializable
public data class MFAConfig(
    val enabled: Boolean,
    val duration: Int,
    val rule: String,
)

@Serializable
public data class PasswordAuthConfig(
    val enabled: Boolean,
    val identityFields: List<String>,
)

@Serializable
public data class OAuth2Config(
    val enabled: Boolean,
    val mappedFields: Map<String, String>,
    val providers: List<OAuth2Provider>,
)

@Serializable
public data class OAuth2Provider(
    val pkce: Boolean?,
    val clientId: String,
    val name: String,
    val clientSecret: String,
    val authURL: String,
    val tokenURL: String,
    val userInfoURL: String,
    val displayName: String,
    val extra: Map<String, JsonElement>?,
)

@Serializable
public data class EmailTemplate(
    val subject: String,
    val body: String,
)