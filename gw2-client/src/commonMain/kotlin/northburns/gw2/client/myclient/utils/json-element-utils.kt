package northburns.gw2.client.myclient.utils

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun JsonElement?.deepMerge(other: JsonElement?): JsonElement {
    return when (other) {
        is JsonObject -> {
            val thisKeys = (this as? JsonObject)?.keys ?: emptySet()
            JsonObject(
                (thisKeys + other.keys).associateWith { key ->
                    if (key in other) (this as? JsonObject)?.get(key).deepMerge(other[key])
                    else (this as? JsonObject)?.get(key) ?: JsonNull
                }
            )

        }

        is JsonArray, is JsonPrimitive, is JsonNull -> other
        null -> JsonNull
    }
}
