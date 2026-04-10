package northburns.gw2.client.myclient.cache

import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.PlayerData
import northburns.gw2.client.myclient.PlayerId
import kotlin.time.Duration

class MyCacheFactory(
    val scope: CoroutineScope,
) {

    inline fun <reified K, reified V> create(playerId: PlayerId?, path: String, ttl: Duration): MyCache<K, V> {
        val kSerializer = Gw2Json.json.serializersModule.serializer<K>()
        val vSerializer = Gw2Json.json.serializersModule.serializer<V>()

        val filename = playerId?.id ?: "_"
        val cacheName = listOfNotNull(
            playerId?.id,
            path,
        ).joinToString(separator = "_")


        val kottage = MyCacheStorageKottage(scope, filename, cacheName, ttl)

        return MyCacheImplKottage(kSerializer, vSerializer, kottage)
    }

}
