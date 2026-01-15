package fi.northburns.northburns.gw2.client.myclient

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
        fun <K,V> create(): MyCache<K,V> {

            TODO()
        }
    }


}
