package northburns.gw2.client.myclient.cache

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import northburns.gw2.client.myclient.Gw2Json
import java.sql.Connection
import java.sql.DriverManager
import kotlin.time.Clock
import kotlin.time.Duration

actual class KottageStorage actual constructor(
    filename: String,
    cacheName: String,
    private val defaultTtl: Duration,
) {

    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:$filename.db")

    init {
        connection.createStatement().execute(
            """
            CREATE TABLE IF NOT EXISTS cache (
                id TEXT PRIMARY KEY,
                data TEXT NOT NULL,
                stored_at INTEGER NOT NULL,
                expires_at INTEGER NOT NULL
            )
        """
        )

        // Index for efficient cleanup
        connection.createStatement().execute(
            """
            CREATE INDEX IF NOT EXISTS idx_expires_at 
            ON cache(expires_at)
        """
        )
    }

    actual suspend fun get(key: String): CachedItem? = withContext(Dispatchers.IO) {
        val now = Clock.System.now().toEpochMilliseconds()

        val stmt = connection.prepareStatement(
            """
            SELECT * FROM cache 
            WHERE id = ? AND expires_at > ?
        """
        )
        stmt.setString(1, key)
        stmt.setLong(2, now)

        val rs = stmt.executeQuery()

        if (rs.next()) {
            CachedItem(
                id = rs.getString("id"),
                json = (rs.getString("data")),
                storedAt = rs.getLong("stored_at").toDouble(),
                expiresAt = rs.getLong("expires_at").toDouble()
            )
        } else {
            // Check if expired and delete
            val deleteStmt = connection.prepareStatement(
                """
                DELETE FROM cache WHERE id = ? AND expires_at <= ?
            """
            )
            deleteStmt.setString(1, key)
            deleteStmt.setLong(2, now)
            deleteStmt.executeUpdate()

            null
        }
    }

    actual suspend fun getMany(keys: Collection<String>): Collection<CachedItem> = withContext(Dispatchers.IO) {
        if (keys.isEmpty()) return@withContext emptyList()

        val now = Clock.System.now().toEpochMilliseconds()
        val placeholders = keys.joinToString(",") { "?" }

        val stmt = connection.prepareStatement(
            """
            SELECT * FROM cache 
            WHERE id IN ($placeholders) 
            AND expires_at > ?
        """
        )

        keys.forEachIndexed { index, key ->
            stmt.setString(index + 1, key)
        }
        stmt.setLong(keys.size + 1, now)

        val rs = stmt.executeQuery()
        val results = mutableListOf<CachedItem>()

        while (rs.next()) {
            results.add(
                CachedItem(
                    id = rs.getString("id"),
                    json = (rs.getString("data")),
                    storedAt = rs.getLong("stored_at").toDouble(),
                    expiresAt = rs.getLong("expires_at").toDouble()
                )
            )
        }

        results
    }

    actual suspend fun set(key: String, value: String): Unit = withContext(Dispatchers.IO) {
        val ttl = defaultTtl
        val now = Clock.System.now().toEpochMilliseconds()

        val stmt = connection.prepareStatement(
            """
            INSERT OR REPLACE INTO cache (id, data, stored_at, expires_at)
            VALUES (?, ?, ?, ?)
        """
        )

        stmt.setString(1, key)
        stmt.setString(2, value)
        stmt.setLong(3, now)
        stmt.setLong(4, now + ttl.inWholeMilliseconds)
        stmt.executeUpdate()
    }

    actual suspend fun setMany(items: Map<String, String>): Unit = withContext(Dispatchers.IO) {
        if (items.isEmpty()) return@withContext

        val now = Clock.System.now().toEpochMilliseconds()

        connection.autoCommit = false
        try {
            val stmt = connection.prepareStatement(
                """
                INSERT OR REPLACE INTO cache (id, data, stored_at, expires_at)
                VALUES (?, ?, ?, ?)
            """
            )

            items.forEach { (key, value) ->
                val ttl = defaultTtl
                stmt.setString(1, key)
                stmt.setString(2, value)
                stmt.setLong(3, now)
                stmt.setLong(4, now + ttl.inWholeMilliseconds)
                stmt.addBatch()
            }

            stmt.executeBatch()
            connection.commit()
        } catch (e: Exception) {
            connection.rollback()
            throw e
        } finally {
            connection.autoCommit = true
        }
    }

    actual suspend fun delete(key: String): Unit = withContext(Dispatchers.IO) {
        val stmt = connection.prepareStatement("DELETE FROM cache WHERE id = ?")
        stmt.setString(1, key)
        stmt.executeUpdate()
    }

    actual suspend fun cleanupExpired() = withContext(Dispatchers.IO) {
        val now = Clock.System.now().toEpochMilliseconds()

        val stmt = connection.prepareStatement(
            """
            DELETE FROM cache WHERE expires_at < ?
        """
        )
        stmt.setLong(1, now)

        val deleted = stmt.executeUpdate()
        println("Cleaned up $deleted expired cache entries")
    }

    actual suspend fun hasKey(key: String): Boolean {
        // XXX could be lighter..
        return get(key) != null
    }

    actual suspend fun clear() {
        TODO("Not yet implemented: clear")
    }
}