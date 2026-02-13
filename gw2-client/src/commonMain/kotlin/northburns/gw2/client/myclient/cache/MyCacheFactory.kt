package northburns.gw2.client.myclient.cache

import io.github.aakira.napier.Napier
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.platform.KottageContext
import io.github.irgaly.kottage.platform.KottageLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.PlayerData
import kotlin.time.Duration

class MyCacheFactory(
    val scope: CoroutineScope,
) {

    val kottageEnvironment = KottageEnvironment(
        context = KottageContext(),
        logger = object : KottageLogger {
            override suspend fun debug(message: String) = Napier.d { message }

            override suspend fun error(message: String) = Napier.e { message }
        }
    )

    inline fun <reified K, reified V> create(player: PlayerData?, path: String, ttl: Duration): MyCache<K, V> {
        val kSerializer = Gw2Json.json.serializersModule.serializer<K>()
        val vSerializer = Gw2Json.json.serializersModule.serializer<V>()

        val filename = player?.key?.key ?: "_"
        val cacheName = listOfNotNull(
            player?.key?.key,
            path,
        ).joinToString(separator = "_")


        val kottage = MyCacheStorageKottage(kottageEnvironment, scope, filename, cacheName, ttl)

        return MyCacheImpl(kSerializer, vSerializer, kottage)
    }

}
