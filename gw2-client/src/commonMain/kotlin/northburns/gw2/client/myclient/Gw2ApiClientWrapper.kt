package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.Gw2ApiResponse
import com.gw2tb.gw2api.client.RequestTemplate
import northburns.gw2.client.myclient.log.GoonLog

class Gw2ApiClientWrapper(
    private val client: Gw2ApiClient,
) {

    private val logger = GoonLog["🌍 GW2 API"]

    suspend fun <T> executeAsync(template: RequestTemplate<T>): Gw2ApiResponse<T> {
        logger.i { log("GET", template.path, "auth", template.apiKey != null) }
        return client.executeAsync(template)
    }

}
