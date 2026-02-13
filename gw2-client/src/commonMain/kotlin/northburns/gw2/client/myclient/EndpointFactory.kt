package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.RequestConfigurer
import com.gw2tb.gw2api.client.RequestTemplate
import io.github.aakira.napier.Napier
import northburns.gw2.client.myclient.cache.MyCache

class EndpointFactory(
    private val client: Gw2ApiClientWrapper,
    private val configure: RequestConfigurer,
) {

    fun <T> simple(template: (configure: RequestConfigurer) -> RequestTemplate<T>): EndpointSimple<T> {
        Napier.w("Warning! Not cached")
        return EndpointSimple(
            client = client,
            template = template(configure),
        )
    }

    fun <T> simplePaged(
        byPage: (page: Long, pageSize: Long?, configure: RequestConfigurer) -> RequestTemplate<List<T>>,
    ): EndpointSimplePaged<T> {
        Napier.w("Warning! Not cached")
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