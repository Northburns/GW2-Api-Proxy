package northburns.gw2.site.app

import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.PlayerData
import northburns.gw2.client.myclient.PlayerId
import kotlin.time.Instant

@Serializable
data class AccountData(
    val auth: AuthState,
    val user: UserState,
    val accounts: List<Account>,
    val partyAccountIds: List<PlayerId>,
) {
    val accountsById: Map<PlayerId, Account> by lazy { accounts.associateBy { it.id } }
    val party: List<Account> by lazy { partyAccountIds.map { accountsById.getValue(it) } }
}


@Serializable
data class AuthState(
    val accessToken: String,
    val refreshToken: String?,
    val exp: Instant,
)

@Serializable
data class UserState(
    val id: String,
    val name: String,
)


@Serializable
sealed interface Account: PlayerData {
    override val id: PlayerId
    override val name: String
    override val emoji: String
}

@Serializable
data class Gw2MeAccount(
    val accountId: String,
    val accountName: String,
    override val name: String,
    override val emoji: String,
    val initialSubToken: String,
) : Account {
    override val id: PlayerId by lazy { PlayerId(accountName) }

}

@Serializable
data class CustomAccount(
    override val name: String,
    override val emoji: String,
    val apiKey: PlayerApiKey,
) : Account {
    override val id: PlayerId = PlayerId(name)
}

// ==================================
/*
@Serializable
data class AuthenticationGw2Me(
    val accessToken: String,
    val refreshToken: String?,
    val exp: Instant,
    val userId: String,
    val userName: String,
    val accounts: List<Gw2MeAccount>,
    val extra: Gw2MeExtra?,
) {
    val allAccounts: List<AccountHolder> by lazy {
        (accounts + (extra?.accounts ?: emptyList()))
            .sortedBy { extra?.accountOrder?.get(it.accountId) }
            .mapIndexed { index, account ->
                AccountHolder(
                    account = account,
                    name = extra?.accountNames?.getOrNull(index) ?: account.name,
                    emoji = extra?.accountEmojis?.getOrNull(index) ?: "👤",
                )
            }
    }

    val accountById: Map<String, AccountHolder> by lazy {
        allAccounts.associateBy { it.account.accountId }
    }

    val party: List<AccountHolder> by lazy {
        extra?.party?.map { accountId -> accountById.getValue(accountId) }
            ?: allAccounts.take(1)
    }
}

data class AccountHolder(
    val account: Gw2Account,
    override val name: String,
    override val emoji: String,
) : PlayerData {
    override val id: PlayerId = PlayerId(account.accountId)
}

@Serializable
sealed interface Gw2Account {
    val accountId: String
    val name: String
}

@Serializable
data class Gw2MeAccount(
    override val accountId: String,
    override val name: String,
    val initialSubtoken: String,
) : Gw2Account {
}

@Serializable
data class CustomAccount(
    override val name: String,
    val apiKey: String,
) : Gw2Account {
    override val accountId: String = name
}

@Serializable
data class Gw2MeExtra(
    val name: String,
    val accounts: List<CustomAccount>,
    val accountOrder: Map<String, Int>,
    val accountNames: List<String?>,
    val accountEmojis: List<String>,
    val party: List<String>,
)
*/