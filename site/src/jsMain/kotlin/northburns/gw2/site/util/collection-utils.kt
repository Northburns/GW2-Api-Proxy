package northburns.gw2.site.util

import js.collections.MapLike
import js.collections.ReadonlyMap
import js.iterable.toSet

/**
 * Gets all values in order. Returns null for missing keys,
 * and throws is map contains keys not read.
 */
fun <K:Any,V: Any> Map<K,V>.requireAll(vararg keys: K): List<V?> {
    val notRead = this.keys - keys.toSet()
    require(notRead.isEmpty()) { "Not all keys were read: $notRead" }
    return keys.map { get(it) }
}


fun <K:Any,V: Any> ReadonlyMap<K, V>.requireAll(vararg keys: K): List<V?> {
    val notRead = this.keys().toSet() - keys.toSet()
    require(notRead.isEmpty()) { "Not all keys were read: $notRead" }
    return keys.map { get(it) }
}
