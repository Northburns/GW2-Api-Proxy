package northburns.gw2

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.sse.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.PlayerDataHardCoded
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.MyCacheFactory
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshotService

fun main() = runBlocking {
    val cacheFactory = MyCacheFactory(this)
    val cache = cacheFactory.create<String, Gw2eAccountSnapshot>(
        PlayerDataHardCoded.playerData(PlayerId("aki")),
        "joku-polku-yesssss",
    )

    // TOSI NOPEE TESTI että onnaaks GW2Efficiency'n snapshotien haku.

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

    val client = MyGw2Client.create(this)

    val snapshotService = AccountSnapshotService(client, cacheFactory, httpClient, json)

    val snapshotIds = snapshotService.getAccountSnapshotIds(PlayerDataHardCoded.AKI)
    snapshotIds.forEach { id ->
        val body = snapshotService.getAccountSnapshot(PlayerDataHardCoded.AKI, id)
        println(body)
    }


}
