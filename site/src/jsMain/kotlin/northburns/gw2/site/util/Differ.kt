package northburns.gw2.site.util

import io.kvision.utils.Serialization.toObj
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object Differ {

    fun <T: Any> x(a: T, b: T): JsonObject {
        a.asDynamic().getOwnPropertyNames()
        TODO()
    }

    fun diff(a: JsonElement, b: JsonElement): JsonElement? {
        if (a == b) return null

        return when (a) {
            is JsonPrimitive -> b
            JsonNull -> b
            is JsonArray -> b // TODO We could analyze what element were removed and what were added - and order?..
            is JsonObject -> if (b is JsonObject) diffObject(a, b) else b
        }
    }

    private fun diffObject(a: JsonObject, b: JsonObject): JsonObject {
        val content = mutableMapOf<String, JsonElement>()

        val keysA = a.keys
        val keysB = b.keys

        val removedKeys = keysA - keysB
        val addedKeys = keysB - keysA
        val sameKeys = keysA.intersect(keysB)

        removedKeys.forEach { key -> content[key] = JsonNull }
        addedKeys.forEach { key -> content[key] = b.getValue(key) }

        sameKeys.forEach { key ->
            val d = diff(a.getValue(key), b.getValue(key))
            if (d != null) content[key] = d
        }

        return JsonObject(content)
    }

}
