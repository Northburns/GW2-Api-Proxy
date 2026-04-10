package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.getOrThrow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.cache.MyCache

/**
 * TODO rename to EndpointSimple
 */
class EndpointSimple<T>(
    private val client: Gw2ApiClientWrapper,
    private val template: RequestTemplate<T>,
    private val cache: MyCache<String, JsonElement>,
    private val serializer: KSerializer<T>,
) {

    suspend fun get(): T {
        val get = cache.getOrCalc(template.path) {
            Gw2Json.json.encodeToJsonElement(serializer, client.executeAsync(template).decodingResult.getOrThrow())
        }
        val result = requireNotNull(get) { "Cache (and its calc) returned null for (${template.path})" }
        return Gw2Json.json.decodeFromJsonElement(serializer, result)
    }
}
