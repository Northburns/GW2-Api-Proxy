package fi.northburns.northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestConfigurer
import com.gw2tb.gw2api.client.RequestTemplate
import northburns.gw2.client.myclient.EndpointEntity
import northburns.gw2.client.myclient.EndpointSimple

class EndpointFactory(
    private val client: Gw2ApiClient,
    private val configure: RequestConfigurer,
) {

    fun <T> simple(template: (configure: RequestConfigurer) -> RequestTemplate<T>): EndpointSimple<T> {
        return EndpointSimple(
            client = client,
            template = template(configure),
        )
    }

    fun <T> simplePaged(
        byPage: (page: Int, pageSize: Int?, configure: RequestConfigurer) -> RequestTemplate<List<T>>,
    ): EndpointSimplePaged<T> {
        return EndpointSimplePaged(
            client = client,
            template = { page: Int, pageSize: Int? -> byPage(page, pageSize, configure) },
        )
    }

    fun <K, V> entity(
        cache: MyCache<K,V> = TODO(),
        allIds: (configure: RequestConfigurer) -> RequestTemplate<List<K>>,
        byIds: (ids: List<K>, configure: RequestConfigurer) -> RequestTemplate<List<V>>,
        byPage: (page: Int, pageSize: Int?, configure: RequestConfigurer) -> RequestTemplate<List<V>>,
        getId: (V) -> K,
    ): EndpointEntity<K, V> {
        return EndpointEntity(
            client = client,
            allIds = allIds(configure),
            byIds = { ids -> byIds(ids, configure) },
            byPage = { page: Int, pageSize: Int? -> byPage(page, pageSize, configure) },
            getId = getId,
            cache = cache,
        )
    }

}