package northburns.gw2.client2

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.cache.KottageStorage
import kotlin.jvm.JvmName

/**
 * Wrapper for [KottageStorage]
 */
class KottageTyped<K : Any, V : Any>(
    private val kottage: KottageStorage,
    private val kSerializer: KSerializer<K>,
    private val vSerializer: KSerializer<V>,
) {

    @JvmName("convertKeyToString")
    private fun K.s(): String = Gw2Json.json.encodeToJsonElement(kSerializer, this).let { element ->
        when (element) {
            is JsonPrimitive -> element.content
            is JsonArray, is JsonObject, JsonNull -> TODO("Unexpected element type: $element")
        }
    }

    fun stringToKey(string: String): K {
        val json = string.toLongOrNull()?.let(::JsonPrimitive) ?: JsonPrimitive(string)
        return Gw2Json.json.decodeFromJsonElement(kSerializer, json)
    }

    @JvmName("converValueToString")
    private fun V.s(): String = Gw2Json.json.encodeToString(vSerializer, this)
    @JvmName("convertStringToValue")
    private fun String.v(): V = Gw2Json.json.decodeFromString(vSerializer, this)

    suspend fun put(key: K, value: V) {
        kottage.set(key.s(), value.s())
    }

    suspend fun remove(key: K) {
        kottage.delete(key.s())
    }

    suspend fun removeAll() {
        kottage.clear()
    }

    suspend fun read(key: K): V? {
        return kottage.get(key.s())?.json?.v()
    }

}