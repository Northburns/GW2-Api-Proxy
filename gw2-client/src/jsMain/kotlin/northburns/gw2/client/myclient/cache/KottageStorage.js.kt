package northburns.gw2.client.myclient.cache

import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.log.GoonLog
import kotlin.time.Clock
import kotlin.time.Duration

actual class KottageStorage actual constructor(
    filename: String,
    cacheName: String,
    private val defaultTtl: Duration,
) {
    private val logger = GoonLog["KottageStorageJs.$filename.$cacheName"]
    private val db = Dexie(filename).apply {
        val schema = js("{}")
        schema[cacheName] = "id, expiresAt"
        version(1).stores(schema)
    }
    private val cache = db.table<CachedItem>(cacheName)

    actual suspend fun get(key: String): CachedItem? {
        val item = cache.get(key).await() ?: return null

        return if (item.isExpired()) {
            // Lazy cleanup: delete on access
            cache.delete(key).await()
            null
        } else {
            item
        }
    }

    actual suspend fun getMany(keys: Collection<String>): Collection<CachedItem> {
        if (keys.isEmpty()) return emptyList()

        val items = cache.bulkGet(keys.toTypedArray()).await()

        val now = Clock.System.now().toEpochMilliseconds().toDouble()
        val expired = mutableListOf<String>()

        val valid = items.filterNotNull().filter { item ->
            if (item.expiresAt < now) {
                expired.add(item.id)
                false
            } else {
                true
            }
        }

        // Clean up expired items in background
        if (expired.isNotEmpty()) {
            kotlinx.coroutines.GlobalScope.launch {
                expired.forEach { cache.delete(it).await() }
            }
        }

        return valid
    }

    actual suspend fun set(key: String, value: String) {
        val ttl = defaultTtl.inWholeMilliseconds
        val now = Clock.System.now().toEpochMilliseconds().toDouble()
        val item = CachedItem(
            id = key,
            json = value,
            storedAt = now,
            expiresAt = now + ttl
        )

        cache.put(item).await()
    }

    actual suspend fun setMany(items: Map<String, String>) {
        if (items.isEmpty()) return

        val ttl = defaultTtl.inWholeMilliseconds
        val now = Clock.System.now().toEpochMilliseconds().toDouble()

        val cacheItems = items.map { (key, value) ->
            CachedItem(
                id = key,
                json = value,
                storedAt = now,
                expiresAt = now + ttl
            )
        }.toTypedArray()

        cache.bulkPut(cacheItems).await()
    }

    actual suspend fun delete(key: String) {
        cache.delete(key).await()
    }

    actual suspend fun cleanupExpired() {
        val now = Clock.System.now().toEpochMilliseconds()

        // Delete all expired items
        val deleted = cache.where("expiresAt").below(now).delete().await()

        logger.info("Cleaned up $deleted expired cache entries")
    }

    actual suspend fun hasKey(key: String): Boolean {
        logger.w { log("hasKey implemented with get") }
        return get(key) != null
    }

    actual suspend fun clear() {
        cache.clear().await()
    }
}