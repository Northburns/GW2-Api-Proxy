package northburns.gw2.site

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.sse.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.MyCacheFactory
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotMeta
import northburns.gw2.client.myclient.snapshot.AccountSnapshotService
import northburns.gw2.client.myclient.snapshot.fromClient
import northburns.gw2.client.myclient.utils.chunked
import northburns.gw2.client2.MyGw2Client2
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.Gw2GoonManager.playerDataRead
import northburns.gw2.site.actions.FetchSnapshotIdsSuccess
import northburns.gw2.site.actions.FetchSnapshotSuccess
import kotlin.time.Duration.Companion.milliseconds

class Api(private val scope: CoroutineScope) {

    val cacheFactory = MyCacheFactory(scope)

    @OptIn(DelicateCoroutinesApi::class)
    //val client = MyGw2Client.create(scope)
    val client = MyGw2Client2.create(scope, playerDataRead)


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

    suspend fun getSnapshotIds(playerId: PlayerId) = snapshotService.getAccountSnapshotIds(playerId)


    // Actual operations


    // Old actuals below:

    fun requestSnapshotIds(playerId: PlayerId) = snapshotIdsRequestChannel.request(playerId)
    fun requestSnapshot(playerId: PlayerId) = snapshotRequestChannel.request(playerId)

    // Private implementation

    private val snapshotIdsRequestChannel = Channel<PlayerId>(Channel.UNLIMITED)
    private val snapshotRequestChannel = Channel<PlayerId>(Channel.UNLIMITED)

    private fun <T> Channel<T>.handleChunked(collector: suspend (List<T>) -> Unit): Job = Gw2GoonManager.launch {
        consumeAsFlow()
            .chunked(2, 1_000.milliseconds)
            .collect { if (it.isNotEmpty()) collector(it.distinct()) }
    }

    private val channels = listOf(
        snapshotIdsRequestChannel.handleChunked { playerIds ->
            playerIds.forEach { playerId ->
                val snapshotIds = getSnapshotIds(playerId).associate { (id, ref) ->
                    id to AccountSnapshotMeta(
                        id,
                        ref.creationDate,
                        ref.creationDate.toString(),
                    )
                }
                goonStore.dispatch(FetchSnapshotIdsSuccess(playerId, snapshotIds))
            }
        },
        snapshotRequestChannel.handleChunked { playerIds ->
            playerIds.forEach { playerId ->
                val c = client.auth(playerId)
                val snap = fromClient(c)
                goonStore.dispatch(FetchSnapshotSuccess(playerId, snap))
            }
        },
    )


    private fun <T> Channel<T>.request(element: T) {
        trySend(element).onFailure { GoonLog["Api"].error(it?.message, it) }
    }
}


