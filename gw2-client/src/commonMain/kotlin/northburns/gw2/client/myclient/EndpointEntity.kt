package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

/**
 * TODO implement caching logic!
 */
class EndpointEntity<K, V>(
    private val client: Gw2ApiClient,
    private val cache: MyCache<K, V>,
    private val allIds: RequestTemplate<List<K>>,
    private val byIds: (List<K>) -> RequestTemplate<List<V>>,
    private val byPage: (page: Int, pageSize: Int?) -> RequestTemplate<List<V>>,
    private val getId: (V) -> K?,
) {

    suspend fun getIds(): Set<K> {
        return if (cache.isAllKeysKnown()) cache.getKnownKeys()
        else {
            val ids = client.executeAsync(allIds).decodingResult.getOrThrow().toSet()
            cache.setKnownKeys(ids)
            return ids;
        }
    }

    suspend fun getAll(): Map<K, V> {
        // val values = EndpointSimplePaged.getPaged(client, byPage)
        // return valuesToMap(values)
        // It's easier to implement the caching this way.
        // We'd need the byPage only if the cache was empty.
        // The difference to this implementation would be the "getIds" call,
        // so it's totally fine to do it this way
        return getMany(getIds())
    }

    suspend fun getMany(ids: Collection<K>): Map<K, V> {
        val result = cache.getMany(ids).toMutableMap()
        val idsMissing = ids.minus(result.keys)

        idsMissing.chunked(200).forEach { chunk ->
            val vs = client.executeAsync(byIds(chunk)).decodingResult.getOrThrow().let(::valuesToMap)
            cache.putAll(vs)
            result.putAll(vs)
        }

        return result
    }

    private fun valuesToMap(values: List<V>): Map<K, V> {
        return values.associateBy { v ->
            requireNotNull(getId(v))
        }
    }


}