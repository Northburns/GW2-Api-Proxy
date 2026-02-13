package northburns.gw2.site

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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.PlayerDataHardCoded
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.MyCacheFactory
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId
import northburns.gw2.client.myclient.snapshot.AccountSnapshotService
import kotlin.time.Duration.Companion.days

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

    // ============================================================================================

    private fun authRequest(): List<StringPair> {
        return emptyList()
    }

    suspend fun login(email: String?, password: String?): User {
        return User("user1")
    }

    suspend fun register(username: String?, email: String?, password: String?): User {
        return User("user1")
    }

    suspend fun user(): User {
        return User("user1")
    }

    suspend fun settings(image: String?, username: String?, bio: String?, email: String?, password: String?): User {
        return User("user1")
    }

    suspend fun tags(): List<String> {
        return emptyList()
    }

    suspend fun articles(
        tag: String?,
        author: String?,
        favorited: String?,
        offset: Int = 0,
        limit: Int = 10
    ): ArticlesDto {
        return ArticlesDto(emptyList())
    }

    suspend fun feed(offset: Int = 0, limit: Int = 10): ArticlesDto {
        return ArticlesDto(emptyList())
    }

    suspend fun article(slug: String): Article {
        return Article("article1", User("user1"))
    }

    suspend fun articleComments(slug: String): List<Comment> {
        return emptyList()
    }

    suspend fun articleComment(slug: String, comment: String?): Comment {
        return Comment(1)
    }

    suspend fun articleCommentDelete(slug: String, id: Int) {
    }

    suspend fun articleFavorite(slug: String, favorite: Boolean = true): Article {
        return Article("article1", User("user1"))
    }

    suspend fun profile(username: String): User {
        return User("user1")
    }

    suspend fun profileFollow(username: String, follow: Boolean = true): User {
        return User("user1")
    }

    suspend fun createArticle(title: String?, description: String?, body: String?, tags: List<String>): Article {
        return Article("article1", User("user1"))
    }

    suspend fun updateArticle(
        slug: String,
        title: String?,
        description: String?,
        body: String?,
        tags: List<String>
    ): Article {
        return Article("article1", User("user1"))
    }

    suspend fun deleteArticle(slug: String) {

    }


}
