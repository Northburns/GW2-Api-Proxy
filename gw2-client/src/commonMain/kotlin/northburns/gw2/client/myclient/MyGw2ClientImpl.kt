package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.ktor.buildGw2ApiClient
import kotlinx.coroutines.CoroutineScope
import northburns.gw2.client.myclient.cache.MyCacheFactory

class MyGw2ClientImpl(
    playerInfo: (PlayerId) -> PlayerData,
    /**
     * Cache instances are closed when this completes
     */
    scope: CoroutineScope,
) : MyGw2Client {

    /**
     * Use a _single_ client to ensure the ratelimiting works as intended.
     */
    private val client = Gw2ApiClientWrapper(buildGw2ApiClient {
        json = Gw2Json.json
    })

    private val cf = MyCacheFactory(scope)

    private val api = CMap.Companion.create<PlayerId?, ApiImplementation>({ playerId ->
        ApiImplementation(client, playerId?.let(playerInfo), cf)
    })

    override val general: MyGw2Client.ApiGeneral = api.getValue(null).apiGeneral
    override val mapInformation: MyGw2Client.ApiMapInformation = api.getValue(null).apiMapInformation
    override val authenticated: CMap<PlayerId, MyGw2Client.ApiAuthenticated> =
        CMap.Companion.create { api.getValue(it).apiAuthenticated }
    override val tradingPost: MyGw2Client.ApiTradingPost = api.getValue(null).apiTradingPost
    override val tradingPostAuthenticated: CMap<PlayerId, MyGw2Client.ApiTradingPostAuthenticated> =
        CMap.Companion.create { api.getValue(it).apiTradingPostAuthenticated }

    override suspend fun invalidatePlayerCaches() {
        val x: List<com.gw2tb.gw2api.types.GW2GuildId>
        TODO("Not yet implemented")
    }

    override suspend fun invalidateTradingPostData() {
        TODO("Not yet implemented")

    }
}
