package northburns.gw2.client.myclient

import kotlinx.serialization.json.JsonElement

interface EntityCache {

    fun getOrPut(store: String, key: String, calc: () -> List<JsonElement>): JsonElement



}
