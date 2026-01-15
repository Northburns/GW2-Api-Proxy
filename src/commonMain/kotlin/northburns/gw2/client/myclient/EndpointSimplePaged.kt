package fi.northburns.northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

/**
 */
class EndpointSimplePaged<T>(
    private val client: Gw2ApiClient,
    private val template: (page: Int, pageSize: Int?) -> RequestTemplate<List<T>>,
) {

    fun get(): List<T> {
        return getPaged(client, template)
    }

    companion object {
        private const val PAGE_SIZE = 200

        fun <V> getPaged(
            client: Gw2ApiClient,
            byPage: (page: Int, pageSize: Int?) -> RequestTemplate<List<V>>,
        ): List<V> {
            val values = mutableListOf<V>()

            var page = 0
            do {
                val response = client.execute(byPage(page, PAGE_SIZE))
                values.addAll(response.decodingResult.getOrThrow())
                val pageTotal = response.headers["X-Page-Total"]?.single()?.toInt() ?: 1
                page++
            } while (page < pageTotal)

            return values
        }
    }

}