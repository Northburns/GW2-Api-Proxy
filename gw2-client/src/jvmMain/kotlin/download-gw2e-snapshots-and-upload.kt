package northburns.gw2

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.sse.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshotRef
import northburns.gw2.client2.MyGw2Client2
import northburns.gw2.client2.PlayerDataProvider
import java.io.File
import kotlin.time.Duration.Companion.minutes

fun main(): Unit = runBlocking {
    Napier.base(DebugAntilog())

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

    val key = PlayerApiKey("5CA7C4ED-5981-8247-A3ED-6FE2C9680F79426E1575-53CE-4179-BEB5-F9698734B362")
    val client = MyGw2Client2.create(this, PlayerDataProvider.EMPTY)

    val cacheDir = File("cache-gw2e-snapshots")
    cacheDir.mkdirs()

    val targetFile = File("G:\\My Drive\\VG_Stuff\\Guild Wars 2\\data\\gw2e-snapshot-aggregate.csv")

    val ids = httpClient
        .get {
            url("https://api.gw2efficiency.com/account-snapshots")
            parameter("v", "2024-07-20T01:00:00.000Z")
            parameter("access_token", key.key)
            expectSuccess = true
        }
        .body<List<Gw2eAccountSnapshotRef>>()
        .sortedBy { it.creationDate }

    val wallet = mutableMapOf<GW2CurrencyId, Long>()
    val inventory = mutableMapOf<GW2ItemId, Long>()
    fun inventoryPlus(id: GW2ItemId, amount: Long) {
        inventory[id] = inventory.getOrDefault(id, 0L) + amount
    }

    val snapshots = ids
        .map { (id, _) ->
            val file = cacheDir.resolve("$id.json")
            if (file.exists()) {
                json.decodeFromString<Gw2eAccountSnapshot>(file.readText())
            } else {
                val element = httpClient
                    .get {
                        url("https://api.gw2efficiency.com/account-snapshots/$id")
                        parameter("v", "2024-07-20T01:00:00.000Z")
                        parameter("access_token", key.key)
                        expectSuccess = true
                        timeout {
                            socketTimeoutMillis = 5.minutes.inWholeMilliseconds
                            requestTimeoutMillis = 5.minutes.inWholeMilliseconds
                            connectTimeoutMillis = 5.minutes.inWholeMilliseconds
                        }
                    }
                    .body<JsonElement>()
                file.writeText(element.toString())
                json.decodeFromJsonElement<Gw2eAccountSnapshot>(element)
            }
        }
        .sortedBy { it.creationDate }
        .onEach { snapshot ->
            snapshot.account.wallet.forEach { c -> wallet[c.id] = c.value }
            snapshot.account.bank.filterNotNull().forEach { inventoryPlus(it.id, it.count) }
            snapshot.account.shared.filterNotNull().forEach { inventoryPlus(it.id, it.count) }
            snapshot.account.materials.forEach { inventoryPlus(it.id, it.count) }
            snapshot.account.characters.forEach { character ->
                character.bags?.forEach { bag ->
                    bag?.inventory?.filterNotNull()?.forEach {
                        inventoryPlus(it.id, it.count)
                    }
                }
            }
        }

    fun snapshotCountOf(snapshot: Gw2eAccountSnapshot, itemId: GW2ItemId): Long {
        val bank = snapshot.account.bank.filter { it?.id == itemId }.sumOf { it?.count ?: 0L }
        val shared = snapshot.account.shared.filter { it?.id == itemId }.sumOf { it?.count ?: 0L }
        val materials = snapshot.account.materials.filter { it.id == itemId }.sumOf { it.count }
        val characters = snapshot.account.characters.sumOf { character ->
            character.bags?.sumOf { bag ->
                bag?.inventory?.filter { it?.id == itemId }?.sumOf { bagItem ->
                    bagItem?.count ?: 0L
                } ?: 0L
            } ?: 0L
        }
        return bank + shared + materials + characters
    }

    val currencies = client.general.currencies.getMany(wallet.keys).associateBy { it.id }
    val items = client.general.items.getMany(inventory.keys).associateBy { it.id }

    // We assume there are no currencies with duplicate names
    currencies.values.groupBy { it.name }
        .filterValues { it.size > 1 }
        .let { currenciesDuplicateNames ->
            require(currenciesDuplicateNames.isEmpty()) {
                "Following currency names are not unique: ${currenciesDuplicateNames.keys}"
            }
        }

    // Collect all names which are not unique
    val duplicateNames = (currencies.values.map { it.name } + items.values.map { it.name })
        .groupBy { it }
        .filterValues { it.size > 1 }
        .keys

    // Currency keys are always the name, but
    // Items keys are item name, but if it's not unique, append item id
    fun key(id: GW2CurrencyId) = currencies.getValue(id).name
    fun key(id: GW2ItemId) = items.getValue(id).let { item ->
        val name = item.name
        if (name !in duplicateNames) name
        else "$name (${item.id.raw})"
    }

    // FI Locale GSheet format
    val tsFormat = LocalDateTime.Format {
        date(LocalDate.Format{
            day(Padding.NONE); char('.')
            monthNumber(Padding.NONE) ; char('.')
            year()
        })
        chars(" klo ")
        time(LocalTime.Format {
            hour(); char('.'); minute(); char('.'); second()
        })
    }

    val currencyIdsSorted = currencies.values.sortedBy { it.order }.map { it.id }
    val itemIdsSorted = items.values.sortedBy { it.name }.map { it.id }

    csvWriter().open(targetFile) {
        writeRow(
            "timestamp",
            *currencyIdsSorted.map(::key).toTypedArray(),
            *itemIdsSorted.map(::key).toTypedArray(),
        )
        snapshots.forEach { snapshot ->
            val timestamp = snapshot.creationDate
                .toLocalDateTime(TimeZone.of("Europe/Helsinki"))
                .format(tsFormat)
            println(timestamp)
            writeRow(
                timestamp,
                *currencyIdsSorted.map { id ->
                    (snapshot.account.wallet.singleOrNull { it.id == id }?.value ?: 0L)
                }.toTypedArray(),
                *itemIdsSorted.map { id -> snapshotCountOf(snapshot, id) }.toTypedArray(),
            )
        }
    }

    println("DONE!!!")

}
