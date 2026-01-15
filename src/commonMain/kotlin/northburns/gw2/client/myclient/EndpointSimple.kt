package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

/**
 * TODO rename to EndpointSimple
 */
class EndpointSimple<T>(
    private val client: Gw2ApiClient,
    private val template: RequestTemplate<T>,
) {

    fun get(): T {
        return client.execute(template).decodingResult.getOrThrow()
    }

}