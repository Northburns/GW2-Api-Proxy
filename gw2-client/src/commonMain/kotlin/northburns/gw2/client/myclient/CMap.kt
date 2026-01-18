package northburns.gw2.client.myclient

/**
 * This class resembles Map a lot, but doesn't follow its conventions, so don't implement that interface.
 */
class CMap<K, V> private constructor(
    private val calc: (K) -> V?,
    private val m: MutableMap<K, V> = mutableMapOf(),
) {

    fun getValue(key: K): V = requireNotNull(get(key))
    fun get(key: K): V? {
        if (!m.containsKey(key)) {
            val value = calc(key)
            if (value != null)
                m[key] = value
        }
        return m[key]
    }

    fun <U> mapValues(transform: (Map.Entry<K, V>) -> U): Map<K, U> = m.mapValues { transform(it) }

    companion object {

        fun <K, V> create(calc: (K) -> V?): CMap<K, V> {
            return CMap(calc)
        }

    }
}
