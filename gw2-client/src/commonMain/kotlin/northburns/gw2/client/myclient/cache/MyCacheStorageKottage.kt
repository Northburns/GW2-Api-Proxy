package northburns.gw2.client.myclient.cache

import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.log.GoonLog
import kotlin.time.Duration

/**
 * https://github.com/irgaly/kottage
 */
class MyCacheStorageKottage(
    scope: CoroutineScope,
    filename: String,
    cacheName: String,
    defaultTtl: Duration,
) {

    private val cache: KottageStorage

    init {
        try {
            cache = KottageStorage(filename, cacheName, defaultTtl)
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ init: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun getKnownKeys(): Set<String> {
        try {
            return cache.get(KEYS_KEY)?.json
                ?.let { Gw2Json.json.decodeFromString(it) }
                ?: emptySet()
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ getKnownKeys: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun isAllKeysKnown(): Boolean = hasKey(KEYS_KEY)

    suspend fun setKnownKeys(ks: Set<String>) {
        try {
            cache.set(KEYS_KEY, Gw2Json.json.encodeToString(ks))
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ setKnownKeys: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    private suspend fun addKnownKey(k: String) {
        try {
            val knownKeys = getKnownKeys()
            if (k !in knownKeys) setKnownKeys(knownKeys + k)
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ addKnownKey: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    private suspend fun addKnownKeys(ks: Collection<String>) {
        if (ks.isEmpty()) return
        try {
            val knownKeys = getKnownKeys()
            val knownKeys2 = knownKeys + ks
            if (knownKeys != knownKeys2) setKnownKeys(knownKeys2)
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ addKnownKeys: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun put(k: String, v: JsonElement) {
        try {
            addKnownKey(k)
            cache.set(k, Gw2Json.json.encodeToString(v))
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ put: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun putAll(vs: Map<String, JsonElement>) {
        try {
            addKnownKeys(vs.keys)
            cache.setMany(vs.mapValues { Gw2Json.json.encodeToString(it.value) })
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ putAll: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun getMany(ks: Collection<String>): Map<String, JsonElement> {
        try {
            return cache.getMany(ks).associate {
                it.id to Gw2Json.json.decodeFromString(it.json)
            }
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ getMany: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun get(k: String): JsonElement? {
        try {
            return cache.get(k)?.json?.let { Gw2Json.json.decodeFromString(it) }
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ get: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun getOrCalc(k: String, calc: suspend () -> JsonElement?): JsonElement? {
        try {
            return get(k) ?: calc()?.also { v -> put(k, v) }
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ getOrCalc: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun purgeAll() {
        try {
            cache.clear()
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ purgeAll: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun purgeExpiredEntries() {
        try {
            cache.cleanupExpired()
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ purgeExpiredEntries: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    suspend fun hasKey(k: String): Boolean {
        try {
            return cache.hasKey(k)
        } catch (e: Exception) {
            GoonLog["MyCache"].error("error @ hasKey: ${e.stackTraceToString()}", e)
            throw e
        }
    }

    companion object {
        private val KEYS_KEY = "_known_keys"
    }

}