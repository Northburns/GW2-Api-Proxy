package fi.northburns.northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.ktor.buildGw2ApiClient
import northburns.gw2.client.myclient.CMap
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.MyGw2Client.ApiAuthenticated
import northburns.gw2.client.myclient.MyGw2Client.ApiGeneral
import northburns.gw2.client.myclient.MyGw2Client.ApiMapInformation
import northburns.gw2.client.myclient.MyGw2Client.ApiTradingPost
import northburns.gw2.client.myclient.MyGw2Client.ApiTradingPostAuthenticated
import northburns.gw2.client.myclient.PlayerData
import northburns.gw2.client.myclient.PlayerId

class MyGw2ClientImpl(
    playerInfo: (PlayerId) -> PlayerData,
) : MyGw2Client {

    /**
     * Use a _single_ client to ensure the ratelimiting works as intended.
     */
    private val client = buildGw2ApiClient {}


    private val api = CMap.create<PlayerId?, ApiImplementation>({ playerId ->
        ApiImplementation(client, playerId?.let(playerInfo))
    })

    override val general: ApiGeneral = api.getValue(null).apiGeneral
    override val mapInformation: ApiMapInformation = api.getValue(null).apiMapInformation
    override val authenticated: CMap<PlayerId, ApiAuthenticated> = CMap.create { api.getValue(it).apiAuthenticated }
    override val tradingPost: ApiTradingPost = api.getValue(null).apiTradingPost
    override val tradingPostAuthenticated: CMap<PlayerId, ApiTradingPostAuthenticated> =
        CMap.create { api.getValue(it).apiTradingPostAuthenticated }

    override suspend fun invalidatePlayerCaches() {
        val x : kotlin.collections.List<com.gw2tb.gw2api.types.GW2GuildId>
        TODO("Not yet implemented")
    }

    override suspend fun invalidateTradingPostData() {
        TODO("Not yet implemented")

    }
}
