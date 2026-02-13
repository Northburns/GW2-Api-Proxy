package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

/**
 */
class EndpointSimplePaged<T>(
    private val client: Gw2ApiClientWrapper,
    private val template: (Long, Long?) -> RequestTemplate<List<T>>,
) {

    suspend fun get(): List<T> {
        return getPaged(
            client,
            template
        )
    }

    companion object {
        private const val PAGE_SIZE = 200L

        suspend fun <V> getPaged(
            client: Gw2ApiClientWrapper,
            byPage: (Long, Long?) -> RequestTemplate<List<V>>,
        ): List<V> {
            val values = mutableListOf<V>()

            var page = 0L
            do {
                val response = client.executeAsync(byPage(page, PAGE_SIZE))
                values.addAll(response.decodingResult.getOrThrow())
                val pageTotal = response.headers["X-Page-Total"]?.single()?.toInt() ?: 1
                page++
            } while (page < pageTotal)

            return values
        }
    }

}