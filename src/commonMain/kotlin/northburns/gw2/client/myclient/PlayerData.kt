package northburns.gw2.client.myclient

import fi.northburns.northburns.gw2.client.myclient.northburns.gw2.client.myclient.PlayerApiKey

data class PlayerData(
    val id: PlayerId,
    val name: String,
    val key: PlayerApiKey,
)
