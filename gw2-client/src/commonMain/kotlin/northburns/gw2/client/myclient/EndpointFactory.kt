package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.RequestConfigurer
import com.gw2tb.gw2api.client.RequestTemplate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.cache.MyCache
import northburns.gw2.client.myclient.log.GoonLog

class EndpointFactory(
    private val client: Gw2ApiClientWrapper,
    private val configure: RequestConfigurer,
) {
    private val logger = GoonLog["EndpointFactory"]

    inline fun <reified T> simple(
        cache: MyCache<String, JsonElement>,
        noinline template: (configure: RequestConfigurer) -> RequestTemplate<T>,
    ): EndpointSimple<T> {
        return simple(cache, serializer<T>(), template)
    }

    fun <T> simple(
        cache: MyCache<String, JsonElement>,
        serializer: KSerializer<T>,
        template: (configure: RequestConfigurer) -> RequestTemplate<T>,
    ): EndpointSimple<T> {
        return EndpointSimple(
            client = client,
            template = template(configure),
            cache = cache,
            serializer = serializer,
        )
    }

    fun <T> simplePaged(
        byPage: (page: Long, pageSize: Long?, configure: RequestConfigurer) -> RequestTemplate<List<T>>,
    ): EndpointSimplePaged<T> {
        logger.warn("Warning! Paged operations are not cached")
        return EndpointSimplePaged(
            client = client,
            template = { page: Long, pageSize: Long? -> byPage(page, pageSize, configure) },
        )
    }

    fun <K, V> entity(
        cache: MyCache<K, V>,
        allIds: (configure: RequestConfigurer) -> RequestTemplate<List<K>>,
        byIds: (ids: List<K>, configure: RequestConfigurer) -> RequestTemplate<List<V>>,
        byPage: (page: Long, pageSize: Long?, configure: RequestConfigurer) -> RequestTemplate<List<V>>,
        getId: (V) -> K,
    ): EndpointEntity<K, V> {
        return EndpointEntity(
            client = client,
            allIds = allIds(configure),
            byIds = { ids -> byIds(ids, configure) },
            byPage = { page: Long, pageSize: Long? -> byPage(page, pageSize, configure) },
            getId = getId,
            cache = cache,
        )
    }

}