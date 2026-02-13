package northburns.gw2.pocketbase

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.sse.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import northburns.gw2.pocketbase.service.BackupService
import northburns.gw2.pocketbase.service.BatchService
import northburns.gw2.pocketbase.service.CollectionService
import northburns.gw2.pocketbase.service.CronService
import northburns.gw2.pocketbase.service.FileService
import northburns.gw2.pocketbase.service.HealthService
import northburns.gw2.pocketbase.service.LogService
import northburns.gw2.pocketbase.service.RealtimeService
import northburns.gw2.pocketbase.service.RecordService
import northburns.gw2.pocketbase.service.SettingsService
import northburns.gw2.pocketbase.store.AuthStore
import northburns.gw2.pocketbase.store.InMemoryAuthStore
import northburns.gw2.pocketbase.tools.OptionsBuilder

public class PocketBaseClient(
    public val baseUrl: String,
    public val authStore: AuthStore = InMemoryAuthStore(),
) {
    private val lang: String = "en-US"

    public val settings: SettingsService = SettingsService(this)
    public val collections: CollectionService = CollectionService(this)
    public val files: FileService = FileService(this)
    public val logs: LogService = LogService(this)
    public val realtime: RealtimeService = RealtimeService(this)
    public val health: HealthService = HealthService(this)
    public val backups: BackupService = BackupService(this)
    public val crons: CronService = CronService(this)

    private val recordServices = mutableMapOf<String, RecordService>()

    public fun collection(idOrName: String): RecordService = recordServices.getOrPut(idOrName) {
        RecordService(this, idOrName)
    }

    public fun admins(): RecordService = collection("_superusers")

    public fun createBatch(): BatchService = BatchService(this)

    /**
     * For technical reasons, this is a public method. For internal use only.
     *
     * http block must set path (e.g. with encodedPath), query params, body.
     */
    public suspend inline fun <reified T> doHttpRequest(
        options: OptionsBuilder,
        http: suspend HttpRequestBuilder.() -> Unit,
    ): T {
        val response = httpClient.request {
            url { takeFrom(baseUrl) }
            contentType(ContentType.Application.Json)

            parameters {
                OptionsBuilder.asMap(options).forEach { (key, value) ->
                    if (value != null) {
                        append(key, value.toString())
                    }
                }
            }
            http()

            expectSuccess = true
            authStore.token()?.let { token ->
                header(HttpHeaders.Authorization, token)
            }
        }
        return response.body()
    }

    private val json: Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    /**
     * TODO Accept as constructor param. Must use this's JSON, and have the required plugins.
     */
    public val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout)
        install(SSE)
    }
}
