package northburns.gw2.client.myclient.snapshot

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.PlayerDataHardCoded
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.MyCache
import northburns.gw2.client.myclient.cache.MyCacheFactory
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot.Character
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshotRef
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AccountSnapshotService(
    private val client: MyGw2Client,
    private val cacheFactory: MyCacheFactory,
    private val httpClient: HttpClient,
    private val json: Json,
) {


    private val caches = mutableMapOf<PlayerId, MyCache<AccountSnapshotId, JsonElement>>()
    private fun cache(playerId: PlayerId) = caches.getOrPut(playerId) {
        cacheFactory.create(PlayerDataHardCoded.playerData(playerId), "account-snapshot", 1000.days)
    }

    suspend fun getAccountSnapshotIds(playerId: PlayerId): List<AccountSnapshotId> {
        return httpClient
            .get {
                url("https://api.gw2efficiency.com/account-snapshots")
                parameter("v", "2024-07-20T01:00:00.000Z")
                parameter("access_token", PlayerDataHardCoded.playerData(playerId).key.key)
                expectSuccess = true
            }
            .body<List<Gw2eAccountSnapshotRef>>()
            .sortedBy { it.creationDate }
            .map { AccountSnapshot.AccountSnapshotIdGw2e(it.id) }
    }

    suspend fun getAccountSnapshot(playerId: PlayerId, id: AccountSnapshotId): AccountSnapshot? {
        require(id is AccountSnapshot.AccountSnapshotIdGw2e) { "Only Gw2e snapshots supported for now." }
        val c = cache(playerId).getOrCalc(id) {
            println("No cache entry for '$id', downloading")
            httpClient
                .get {
                    url("https://api.gw2efficiency.com/account-snapshots/${id.value}")
                    parameter("v", "2024-07-20T01:00:00.000Z")
                    parameter("access_token", PlayerDataHardCoded.playerData(playerId).key.key)
                    expectSuccess = true
                    timeout {
                        socketTimeoutMillis = 5.minutes.inWholeMilliseconds
                        requestTimeoutMillis = 5.minutes.inWholeMilliseconds
                        connectTimeoutMillis = 5.minutes.inWholeMilliseconds
                    }
                }
                .body<JsonElement>()
        }
        if (c == null) return null
        val gw2e = json.decodeFromJsonElement<Gw2eAccountSnapshot>(c)
        return AccountSnapshot.fromGw2e(id, gw2e)
    }


    suspend fun getCurrentAccountSnapshot(playerId: PlayerId): AccountSnapshot {
        val c = client.authenticated.getValue(playerId)

        val account = c.account.get()
        val wallet = c.wallet.get()
        val bank = c.bank.get()
        val materials = c.materials.get()
        val legendaryArmory = c.legendaryArmory.get()
        val characters = c.characters.getAll()
        val shared = c.inventory.get()

        return AccountSnapshot(
            id = AccountSnapshot.AccountSnapshotIdGoon(Uuid.generateV4()),
            creationDate = Clock.System.now(),
            account = AccountSnapshot.Account(
                age = account.age.seconds,
                wallet = wallet.associate { it.id to it.value },
                stock = AccountSnapshot.createStock(
                    shared= shared,
                    bank = bank,
                    materials = materials,
                    legendaryarmory = legendaryArmory,
                    characters = characters.values.sortedBy { it.name }.map { Character.fromApi(it)},
                ),
            ),
        )
    }

    suspend fun getAccountSnapshots(playerId: PlayerId): List<AccountSnapshot> {
        return getAccountSnapshotIds(playerId)
            .mapNotNull { getAccountSnapshot(playerId, it) }
            .sortedBy { it.creationDate }
    }

    suspend fun isSnapshotInCache(playerId: PlayerId, id: AccountSnapshotId): Boolean {
        return cache(playerId).hasKey(id)
    }

}