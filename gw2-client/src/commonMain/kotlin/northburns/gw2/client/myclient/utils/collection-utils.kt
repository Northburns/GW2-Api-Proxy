package northburns.gw2.client.myclient.utils

inline fun <K, V> Map<K, V>.replaceKeys(keys: Collection<K>, missing: (K) -> V): Map<K, V> {
    return keys.associateWith { get(it) ?: missing(it) }
}
