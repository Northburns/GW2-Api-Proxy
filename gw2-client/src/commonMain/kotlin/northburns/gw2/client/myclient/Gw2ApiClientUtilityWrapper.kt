package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

class Gw2ApiClientUtilityWrapper(
    private val client: Gw2ApiClient,
) {


    fun <T> doOne(template: RequestTemplate<T>): suspend () -> T = {
        client.executeAsync(template).decodingResult.getOrThrow()
    }

    fun <K, T> doIds(template: (List<K>) -> RequestTemplate<List<T>>): suspend (List<K>) -> List<T> {
        return { ids: List<K> ->
            ids
                .chunked(200)
                .flatMap { chunk ->
                    client.executeAsync(template(chunk)).decodingResult.getOrThrow()
                }
        }
    }


}