package northburns.gw2.client.myclient.cache

import kotlinx.serialization.Serializable

class MyCacheImpl<K, V> : MyCache<K, V> {
    var knowsAllKeys = false

    @Serializable
    data class CacheEntry(
        val i : String,
    )

    override fun getKnownKeys(): Set<K> {
        TODO()
    }

    override fun isAllKeysKnown(): Boolean {
        TODO()
    }

    override fun setKnownKeys(ks: Set<K>) {
        TODO()
    }

    override fun putAll(vs: Map<K, V>) {
        TODO()
    }

    override fun getMany(ks: Collection<K>): Map<K, V> {
        TODO()
    }

    private fun purgeExpiredEntries() {
        TODO()
    }
}
