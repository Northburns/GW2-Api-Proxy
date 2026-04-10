package northburns.gw2.client.myclient

import kotlinx.serialization.json.JsonElement

external interface PlayersJsonData {
    val name: String?
    val emoji: String?
    val apiKey: String?
}


@JsModule("./players/players.json")
@JsNonModule
external val playerJson: Map<String, PlayersJsonData>

@JsModule("./players/players-secrets.json")
@JsNonModule
external val playerSecretsJson: Map<String, PlayersJsonData>

actual suspend fun initializePlayerDataRead(): Pair<String, String> {
    return JSON.stringify(playerJson ) to JSON.stringify(playerSecretsJson)
}
