package fi.northburns.northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow

class Gw2ApiClientUtilityWrapper(
    private val client: Gw2ApiClient,
) {


    fun <T> doOne(template: RequestTemplate<T>): () -> T = {
        client.execute(template).decodingResult.getOrThrow()
    }

    fun <K, T> doIds(template: (List<K>) -> RequestTemplate<List<T>>): (List<K>) -> List<T> {
        return { ids: List<K> ->
            ids
                .chunked(200)
                .flatMap { chunk ->
                    client.execute(template(chunk)).decodingResult.getOrThrow()
                }
        }
    }


}