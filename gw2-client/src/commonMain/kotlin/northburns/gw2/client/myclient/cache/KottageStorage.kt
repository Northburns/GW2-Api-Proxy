package northburns.gw2.client.myclient.cache

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.js.JsExport
import kotlin.time.Clock
import kotlin.time.Duration

expect class KottageStorage(filename: String, cacheName: String, defaultTtl: Duration) {
    suspend fun hasKey(key: String): Boolean
    suspend fun get(key: String): CachedItem?
    suspend fun getMany(keys: Collection<String>): Collection<CachedItem>
    suspend fun set(key: String, value: String)
    suspend fun setMany(items: Map<String, String>)
    suspend fun delete(key: String)
    suspend fun cleanupExpired()
    suspend fun clear()
}

@Serializable
@JsExport
data class CachedItem(
    val id: String,
    val json: String,
    val storedAt: Double,
    val expiresAt: Double,
)

// I don't have the time to debug this. But when getting the value from Dexie,
// the resulting struct is correct as per the data class,
// but it doesn't have member functions. This solution is fine:
fun CachedItem.isExpired(): Boolean =
    Clock.System.now().toEpochMilliseconds().toDouble() > expiresAt

// Also, double. Kotlin.Long doesn't "survive" the roundtrip via Dexie, it
// comes back as a JS number, but Kotlin.Long is an object.
// Using Double, as that's what JS Date is.