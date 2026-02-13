package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.Gw2ApiResponse
import com.gw2tb.gw2api.client.RequestTemplate
import io.github.aakira.napier.Napier

class Gw2ApiClientWrapper(
    private val client: Gw2ApiClient,
) {

    suspend fun <T> executeAsync(template: RequestTemplate<T>): Gw2ApiResponse<T> {
        Napier.e(tag = "GW2 API") { "GET ${template.path} auth=${template.apiKey != null}" }
        return client.executeAsync(template)
    }

}