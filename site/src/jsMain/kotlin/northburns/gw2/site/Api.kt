package northburns.gw2.site

import com.gw2tb.gw2api.types.GW2ItemId
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.sse.*
import io.ktor.serialization.kotlinx.json.*
import io.kvision.core.StringPair
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.PlayerDataHardCoded
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.MyCacheFactory
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId
import northburns.gw2.client.myclient.snapshot.AccountSnapshotService
import northburns.gw2.client.myclient.utils.chunked
import northburns.gw2.site.state.GoonAction
import northburns.gw2.site.state.GoonState
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

class Api(scope: CoroutineScope) {

    @OptIn(DelicateCoroutinesApi::class)
    val client = MyGw2Client.create(scope)

    val cacheFactory = MyCacheFactory(scope)

    @OptIn(DelicateCoroutinesApi::class)
    val cache = cacheFactory.create<String, JsonElement>(
        PlayerDataHardCoded.playerData(PlayerId("aki")),
        "joku-polku-yesssss",
        1000.days,
    )

    private val json = Json {
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

    val snapshotService = AccountSnapshotService(client, cacheFactory, httpClient, json)

    suspend fun getSnapshotIds() = snapshotService.getAccountSnapshotIds(PlayerDataHardCoded.AKI)
    suspend fun getSnapshot(id: AccountSnapshotId) = snapshotService.getAccountSnapshot(PlayerDataHardCoded.AKI, id)
    suspend fun isSnapshotInCache(id: AccountSnapshotId) =
        snapshotService.isSnapshotInCache(PlayerDataHardCoded.AKI, id)


    suspend fun getCacheStuff(): String {
        cache.purgeAll()
        val v = cache.getKnownKeys().joinToString("|")
        Napier.e(v)
        return v
    }

    // Actual operations

    private val itemRequestChannel = Channel<GW2ItemId>(Channel.UNLIMITED)
    private val channels = Gw2GoonManager.launch {
        itemRequestChannel.consumeAsFlow()
            .chunked(1_000.milliseconds)
            .collect { itemIds ->
                if (itemIds.isNotEmpty()) {
                    fetchItemsBatch(itemIds.distinct())
                }
            }
    }

    fun requestItem(itemId: GW2ItemId) {
        itemRequestChannel.trySend(itemId)
    }

    private suspend fun fetchItemsBatch(itemIds: List<GW2ItemId>) {
        val items = client.general.items.getMany(itemIds)
        Gw2GoonManager.goonStore.dispatch(GoonAction.FetchItemsSuccess(items.values))
    }
}
