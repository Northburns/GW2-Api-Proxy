package northburns.gw2.client.myclient.cache

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.Gw2Json

class MyCacheImplKottage<K, V>(
    private val kSerializer: KSerializer<K>,
    private val vSerializer: KSerializer<V>,
    private val kottage: MyCacheStorageKottage,
) : MyCache<K, V> {
    var knowsAllKeys = false


    override suspend fun getKnownKeys(): Set<K> {
        return kottage.getKnownKeys().map(::keyFromString).toSet()
    }

    override suspend fun isAllKeysKnown(): Boolean {
        return kottage.isAllKeysKnown()
    }

    override suspend fun setKnownKeys(ks: Set<K>) {
        kottage.setKnownKeys(ks.map(::keyToString).toSet())
    }

    override suspend fun hasKey(k : K): Boolean {
        return kottage.hasKey(keyToString(k))
    }

    override suspend fun put(k: K, v: V) {
        kottage.put(keyToString(k), valueToJson(v))
    }

    override suspend fun putAll(vs: Map<K, V>) {
        kottage.putAll(vs.mapValues { valueToJson(it.value) }.mapKeys { keyToString(it.key) })
    }

    override suspend fun getMany(ks: Collection<K>): Map<K, V> {
        return kottage.getMany(ks.map { keyToString(it) })
            .mapValues { valueFromJson(it.value) }
            .mapKeys { keyFromString(it.key) }
    }

    override suspend fun get(k: K): V? {
        return kottage.get(keyToString(k))?.let { valueFromJson(it) }
    }

    override suspend fun getOrCalc(k: K, calc: suspend () -> V?): V? {
        return kottage
            .getOrCalc(keyToString(k), { calc()?.let { valueToJson(it) } })
            ?.let { valueFromJson(it) }
    }

    override suspend fun purgeAll() {
        kottage.purgeAll()
    }

    override suspend fun purgeExpiredEntries() {
        kottage.purgeExpiredEntries()
    }

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
}
