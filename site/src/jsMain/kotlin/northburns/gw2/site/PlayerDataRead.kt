package northburns.gw2.site

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.PlayerData
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.client.myclient.utils.myTicker
import northburns.gw2.client2.PlayerDataProvider
import northburns.gw2.site.actions.PartyChangedAction
import northburns.gw2.site.app.CustomAccount
import northburns.gw2.site.app.Gw2MeAccount
import kotlin.io.encoding.Base64
import kotlin.js.Date
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant
import kotlin.time.toKotlinInstant

class PlayerDataRead(
    scope: CoroutineScope,
) : PlayerDataProvider {

    fun initialize() {
        Gw2GoonState.party.subscribe { party ->
            Gw2GoonManager.goonStore.dispatch(
                PartyChangedAction(party?.map { it.id } ?: emptyList()))
        }
        Gw2GoonState.account.subscribe { auth ->
            playerDataHolders = auth?.accounts
                ?.associate { acc ->
                    acc.id to when (acc) {
                        is CustomAccount -> PlayerDataHolderConstant(acc)
                        is Gw2MeAccount -> PlayerDataHolderRefresh(acc)
                    }
                }
                ?: emptyMap()
        }

    }

    private val logger = GoonLog["PlayerDataRead"]
    private val ticker = scope.myTicker(delayMillis = 5.minutes.inWholeMilliseconds, initialDelayMillis = 0L)

    @Suppress("unused")
    private val job = scope.launch {
        ticker.consumeAsFlow().collect {

            playerDataHolders.values
                .filterIsInstance<PlayerDataHolderRefresh>()
                .forEach { holder ->

                    if (holder.shouldRefresh()) {
                        logger.info("Refreshing token for account", holder.playerData.name)
                        holder.refresh()
                    } else logger.info("Not refreshing token for account", holder.playerData.name)
                }

        }
    }

    private var playerDataHolders = emptyMap<PlayerId, PlayerDataHolder>()

    private sealed interface PlayerDataHolder {
        val playerData: PlayerData
        fun getCurrentKey(): PlayerApiKey
    }

    private class PlayerDataHolderConstant(
        override val playerData: CustomAccount,
    ) : PlayerDataHolder {

        override fun getCurrentKey(): PlayerApiKey = playerData.apiKey
    }

    private class PlayerDataHolderRefresh(
        override val playerData: Gw2MeAccount,
    ) : PlayerDataHolder {

        private val mutex = Mutex()
        private var playerApiKey: PlayerApiKey = PlayerApiKey(playerData.initialSubToken)
        private var refreshAt: Instant = subtokenRefreshAt(playerData.initialSubToken)

        override fun getCurrentKey(): PlayerApiKey = playerApiKey
        fun shouldRefresh() = Clock.System.now() >= refreshAt

        suspend fun refresh() {
            mutex.withLock {
                val subtoken = Gw2GoonManager.gw2me.getSubtoken(playerData)
                playerApiKey = PlayerApiKey(subtoken)
                refreshAt = subtokenRefreshAt(subtoken)
            }
        }

        private companion object {
            private val base64 = Base64.withPadding(Base64.PaddingOption.ABSENT_OPTIONAL)
            fun subtokenRefreshAt(subtoken: String): Instant {
                val payload = base64.decode(subtoken.split(".")[1]).decodeToString()
                val claims = Json.decodeFromString<JsonObject>(payload)
                val exp = (claims.getValue("exp") as JsonPrimitive).content.toLong()
                val instant = Date(exp * 1000L).toKotlinInstant()
                return instant.minus(5.minutes)
            }
        }
    }


    override fun getPlayerData(id: PlayerId): PlayerData =
        playerDataHolders.getValue(id).playerData

    override fun getPlayerKey(id: PlayerId): PlayerApiKey =
        playerDataHolders.getValue(id).getCurrentKey()

    fun playerData(id: PlayerId): PlayerData = getPlayerData(id)

    fun playerKey(id: PlayerId): PlayerApiKey = getPlayerKey(id)
}
