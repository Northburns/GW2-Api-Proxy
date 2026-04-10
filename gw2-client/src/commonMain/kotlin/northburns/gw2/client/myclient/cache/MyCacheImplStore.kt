package northburns.gw2.client.myclient.cache

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.Gw2Json

class MyCacheImplStore<K: Any, V: Any>(
    private val kSerializer: KSerializer<K>,
    private val vSerializer: KSerializer<V>,
) : MyCache<K, V> {
    var knowsAllKeys = false


    private fun keyToString(k: K): String {
        return Gw2Json.json.encodeToString(kSerializer, k)
    }

    private fun keyFromString(k: String): K {
        return Gw2Json.json.decodeFromString(kSerializer, k)
    }

    private fun valueToJson(value: V): JsonElement {
        return Gw2Json.json.encodeToJsonElement(vSerializer, value)
    }

    private fun valueFromJson(value: JsonElement): V {
        return Gw2Json.json.decodeFromJsonElement(vSerializer, value)
    }

    override suspend fun getKnownKeys(): Set<K> {
        TODO("Not yet implemented")
    }

    override suspend fun isAllKeysKnown(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setKnownKeys(ks: Set<K>) {
        TODO("Not yet implemented")
    }

    override suspend fun hasKey(k: K): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun put(k: K, v: V) {
        TODO("Not yet implemented")
    }

    override suspend fun putAll(vs: Map<K, V>) {
        TODO("Not yet implemented")
    }

    override suspend fun get(k: K): V? {
        TODO("Not yet implemented")
    }

    override suspend fun getMany(ks: Collection<K>): Map<K, V> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrCalc(k: K, calc: suspend () -> V?): V? {
        TODO("Not yet implemented")
    }

    override suspend fun purgeAll() {
        TODO("Not yet implemented")
    }

    override suspend fun purgeExpiredEntries() {
        TODO("Not yet implemented")
    }
}
