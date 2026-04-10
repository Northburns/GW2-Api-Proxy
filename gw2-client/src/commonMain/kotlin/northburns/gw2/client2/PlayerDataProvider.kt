package northburns.gw2.client2

import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.PlayerData
import northburns.gw2.client.myclient.PlayerId

/**
 * Returned values should not be cached. Especially Key can change.
 */
interface PlayerDataProvider {
    fun getPlayerData(id: PlayerId): PlayerData
    fun getPlayerKey(id: PlayerId): PlayerApiKey

    companion object {
        val EMPTY = object : PlayerDataProvider {
            override fun getPlayerData(id: PlayerId): PlayerData {
                throw IllegalArgumentException("Empty PlayerDataProvider")
            }

            override fun getPlayerKey(id: PlayerId): PlayerApiKey {
                throw IllegalArgumentException("Empty PlayerDataProvider")
            }
        }
    }
}