package northburns.gw2.site.app

import kotlinx.serialization.Serializable

class GoonErrorThrowable(
    val error: GoonError,
) : Throwable(error.message, null)

@Serializable
sealed interface GoonError {
    val message: String
}

@Serializable
data class FatalGoonError(
    override val message: String,
    val description: String,
    val extra: String? = null,
    val stacktrace: String? = null,
) : GoonError
