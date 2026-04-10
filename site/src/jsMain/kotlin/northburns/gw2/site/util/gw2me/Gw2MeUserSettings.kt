package northburns.gw2.site.util.gw2me

import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.site.app.AccountData
import northburns.gw2.site.app.AuthState
import northburns.gw2.site.app.CustomAccount
import northburns.gw2.site.app.Gw2MeAccount
import northburns.gw2.site.app.UserState
import kotlin.time.Instant

@Serializable
data class Gw2MeUserSettings(
    val custom: List<Gw2MeCustomAccount>,
    val emojisForAccounts: Map<String, String>,
    val partyAccounts: List<String>,
) {

    @Serializable
    data class Gw2MeCustomAccount(
        val name: String,
        val emoji: String,
        val apiKey: String,
    )

    companion object {
        suspend fun Gw2MeUserSettings?.toGoon(
            user: User,
            accessToken: String,
            refreshToken: String?,
            exp: Instant,
            accounts: AccountsResponse,
            accountToInitialSubtoken: suspend (String) -> SubtokenResponse,
        ): AccountData {

            val accounts1 = accounts.accounts.map { account ->
                Gw2MeAccount(
                    accountId = account.id,
                    accountName = account.name,
                    name = account.displayName ?: account.name,
                    emoji = this?.emojisForAccounts?.get(account.name) ?: "👤",
                    initialSubToken = accountToInitialSubtoken(account.id).subtoken,
                )
            }
            val accounts2 = this?.custom?.map { custom ->
                CustomAccount(
                    name = custom.name,
                    emoji = custom.emoji,
                    apiKey = PlayerApiKey(custom.apiKey),
                )
            } ?: emptyList()

            val accounts = accounts1 + accounts2

            return AccountData(
                auth = AuthState(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    exp = exp,
                ),
                user = UserState(
                    id = user.id,
                    name = user.name,
                ),
                accounts = accounts,
                partyAccountIds = this?.partyAccounts?.map { PlayerId(it) }
                    ?: accounts.map { it.id },
            )
        }

        fun fromGoon(accountData: AccountData): Gw2MeUserSettings {
            return Gw2MeUserSettings(
                custom = accountData.accounts
                    .filterIsInstance<CustomAccount>()
                    .map { custom ->
                        Gw2MeCustomAccount(
                            name = custom.name,
                            emoji = custom.emoji,
                            apiKey = custom.apiKey.key,
                        )
                    },
                emojisForAccounts = accountData.accounts
                    .filterIsInstance<Gw2MeAccount>()
                    .associate { it.accountName to it.emoji },
                partyAccounts = accountData.partyAccountIds.map { it.id },
            )
        }

    }
}
