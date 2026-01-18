package northburns.gw2.client.myclient.cache

import northburns.gw2.client.myclient.Gw2Json

interface MyCache<K, V> {

    fun getKnownKeys(): Set<K>

    /**
     * Returns false, until <setKnownKeys> is called at least once.
     */
    fun isAllKeysKnown(): Boolean
    fun setKnownKeys(ks: Set<K>)


    fun putAll(vs: Map<K, V>)

    fun getMany(ks: Collection<K>): Map<K, V>

    companion object {
        fun <K,V> create(v: V): MyCache<K, V> {
            Gw2Json.json.encodeToString(v)
            return MyCacheImpl()
        }
    }


}
