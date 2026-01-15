package northburns.gw2.client.myclient

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PlayerId(val id: String)