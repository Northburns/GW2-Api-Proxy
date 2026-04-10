package northburns.gw2.gw2e

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.sse.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshotRef
import java.io.File

/**
 * Only for me!
 */
class Gw2EfficiencySnapshotDownloader(
    val cacheDir: File,
    val key: String,
) {

    val json = Json {
        prettyPrint = true
        isLenient = false
        ignoreUnknownKeys = true
    }
    val httpClient = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout)
        install(SSE)
    }

    init {
        cacheDir.mkdirs()
    }

    suspend fun downloadAll() {
        val ids = httpClient
            .get {
                url("https://api.gw2efficiency.com/account-snapshots")
                parameter("v", "2024-07-20T01:00:00.000Z")
                parameter("access_token", key)
                expectSuccess = true
            }
            .body<List<Gw2eAccountSnapshotRef>>()
            .sortedBy { it.creationDate }
    }

}