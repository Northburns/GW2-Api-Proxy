package northburns.gw2.client.myclient.cache

import io.github.aakira.napier.Napier
import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.KottageStorage
import io.github.irgaly.kottage.platform.KottageContext
import io.github.irgaly.kottage.platform.KottageLogger
import io.github.irgaly.kottage.strategy.KottageFifoStrategy
import northburns.gw2.client.myclient.Gw2Json
import kotlin.time.Duration.Companion.days

/**
 * https://github.com/irgaly/kottage
 */
class MyCacheStorageKottage {

    val cache: KottageStorage

    init {
        val kottageEnvironment = KottageEnvironment(
            context = KottageContext(),
            logger = object : KottageLogger {
                override suspend fun debug(message: String) = Napier.d { message }

                override suspend fun error(message: String) = Napier.e { message }
            }
        )
        val kottage = Kottage(
            name = "kottage-store-name", // This will be database file name
            directoryPath = databaseDirectory,
            environment = kottageEnvironment,
            scope = scope, // This kottage instance will be automatically close on this CoroutineScope completion
            json = Gw2Json.json // kotlinx.serialization's json object
        )
        cache = kottage.cache("timeline_item_cache") {
            // There are some options
            strategy = KottageFifoStrategy(maxEntryCount = 1000) // default strategy in cache mode
            //strategy = KottageFifoStrategy(maxCacheSize = 512 * 1024 * 1024) // FIFO strategy with cache sized based eviction (bytes)
            //strategy = KottageFifoStrategy(maxEntryCount = 1000, maxCacheSize = 512 * 1024 * 1024) // FIFO strategy with item count and cache sized based eviction (bytes)
            //strategy = KottageLruStrategy(maxEntryCount = 1000) // LRU cache strategy
            //strategy = KottageLruStrategy(maxCacheSize = 512 * 1024 * 1024) // LRU strategy with cache sized based eviction (bytes)
            //strategy = KottageLruStrategy(maxEntryCount = 1000, maxCacheSize = 512 * 1024 * 1024) // LRU strategy with item count and cache sized based eviction (bytes)
            defaultExpireTime = 30.days // cache item expiration time in kotlin.time.Duration
        }


    }

    suspend fun x() {
        cache.get<String>("s")
        cache.list("").getPageFrom("", 0).items
    }

}