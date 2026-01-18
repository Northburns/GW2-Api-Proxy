package northburns.gw2.client.myclient

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class PlayerApiKey(val key: String)