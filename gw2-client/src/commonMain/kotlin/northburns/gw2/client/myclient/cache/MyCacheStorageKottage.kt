package northburns.gw2.client.myclient.cache

import io.github.aakira.napier.Napier
import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.KottageStorage
import io.github.irgaly.kottage.getOrNull
import io.github.irgaly.kottage.getOrPut
import io.github.irgaly.kottage.put
import io.github.irgaly.kottage.strategy.KottageFifoStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.Gw2Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

/**
 * https://github.com/irgaly/kottage
 */
class MyCacheStorageKottage(
    kottageEnvironment: KottageEnvironment,
    scope: CoroutineScope,
    filename: String,
    cacheName: String,
    ttl: Duration,
) {

    private val cache: KottageStorage

    init {
        try {
            val kottage = Kottage(
                name = filename,
                directoryPath = MyCacheSettings.cacheDir(),
                environment = kottageEnvironment,
                scope = scope,
                json = Gw2Json.json
            )
            cache = kottage.cache(cacheName) {
                strategy = KottageFifoStrategy(maxEntryCount = 1000)
                strategy = KottageFifoStrategy(maxCacheSize = 512 * 1024 * 1024)
                defaultExpireTime = ttl
            }
        } catch (e: Exception) {
            Napier.e("Kottage error @ init: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun x() {
        // cache.get<String>("s")
        cache.list("").getPageFrom("", 0).items
    }

    suspend fun getKnownKeys(): Set<String> {
        try {
            return cache.getOrNull(KEYS_KEY) ?: emptySet()
        } catch (e: Exception) {
            Napier.e("Kottage error @ getKnownKeys: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun isAllKeysKnown(): Boolean {
        try {
            return cache.exists(KEYS_KEY)
        } catch (e: Exception) {
            Napier.e("Kottage error @ isAllKeysKnown: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun setKnownKeys(ks: Set<String>) {
        try {
            cache.put(KEYS_KEY, ks)
        } catch (e: Exception) {
            Napier.e("Kottage error @ setKnownKeys: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun put(k: String, v: JsonElement) {
        try {
            cache.put(k, v, null)
        } catch (e: Exception) {
            Napier.e("Kottage error @ put: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun putAll(vs: Map<String, JsonElement>) {
        try {
            vs.forEach { (k, v) ->
                cache.put(k, v, null)
            }
        } catch (e: Exception) {
            Napier.e("Kottage error @ putAll: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun getMany(ks: Collection<String>): Map<String, JsonElement> {
        try {
            val result = mutableMapOf<String, JsonElement>()
            ks.forEach { key -> cache.getOrNull<JsonElement>(key)?.let { result[key] = it } }
            return result
        } catch (e: Exception) {
            Napier.e("Kottage error @ getMany: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun get(k: String): JsonElement? {
        try {
            return cache.getOrNull<JsonElement>(k)
        } catch (e: Exception) {
            Napier.e("Kottage error @ get: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun getOrCalc(k: String, calc: suspend () -> JsonElement?): JsonElement? {
        try {
            return get(k) ?: calc()?.also { v -> put(k, v) }
        } catch (e: Exception) {
            Napier.e("Kottage error @ getOrCalc: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun purgeAll() {
        try {
            cache.removeAll()
        } catch (e: Exception) {
            Napier.e("Kottage error @ purgeAll: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun purgeExpiredEntries() {
        try {
            cache.compact()
        } catch (e: Exception) {
            Napier.e("Kottage error @ purgeExpiredEntries: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun hasKey(k: String): Boolean {
        try {
            return cache.exists(k)
        } catch (e: Exception) {
            Napier.e("Kottage error @ hasKey: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    companion object {
        private val KEYS_KEY = "_known_keys"
    }

}