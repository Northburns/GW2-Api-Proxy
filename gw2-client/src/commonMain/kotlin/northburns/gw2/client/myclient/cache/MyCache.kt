package northburns.gw2.client.myclient.cache

import northburns.gw2.client.myclient.snapshot.AccountSnapshot

interface MyCache<K, V> {

    suspend fun getKnownKeys(): Set<K>

    /**
     * Returns false, until <setKnownKeys> is called at least once.
     */
    suspend fun isAllKeysKnown(): Boolean
    suspend fun setKnownKeys(ks: Set<K>)

    suspend fun hasKey(k : K): Boolean

    suspend fun put(k: K, v: V)
    suspend fun putAll(vs: Map<K, V>)

    suspend fun get(k: K): V?
    suspend fun getMany(ks: Collection<K>): Map<K, V>
    suspend fun getOrCalc(k: K, calc: suspend () -> V?): V?

    suspend fun purgeAll()
    suspend fun purgeExpiredEntries()
}
