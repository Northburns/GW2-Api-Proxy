package northburns.gw2.client.myclient

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import northburns.gw2.client.myclient.utils.deepMerge
import northburns.gw2.client.myclient.utils.map


expect suspend fun initializePlayerDataRead(): Pair<String, String>

object PlayerDataReadOld {

    /**
     * TODO move this to GoonState!!! We read it from somewhere nice. Current solution is a hack.
     */
    lateinit var playerData: Map<PlayerId, PlayerData>

    suspend fun initialize() {
        val foo = initializePlayerDataRead()
        val (playersJson, playersSecretsJson) = foo.map(
            { Gw2Json.json.decodeFromString<JsonElement>(it) },
            { Gw2Json.json.decodeFromString<JsonElement>(it) },
        )

        val players = playersJson.deepMerge(playersSecretsJson)
            // A stupid hack to have the 'id' in player objects
            .let { playerConfig ->
                require(playerConfig is JsonObject)
                JsonObject(
                    playerConfig.entries.associate { (k, v) ->
                        require(v is JsonObject)
                        k to JsonObject(
                            v.plus("id" to JsonPrimitive(k))
                        )
                    }
                )
            }
        playerData = Gw2Json.json.decodeFromJsonElement(players)
    }

    fun playerData(id: PlayerId): PlayerData {
        return playerData.getValue(id)
    }

}
