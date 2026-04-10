package northburns.gw2.client.myclient.snapshot

import arrow.optics.optics
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2Account
import com.gw2tb.gw2api.types.v2.GW2v2AccountAchievement
import com.gw2tb.gw2api.types.v2.GW2v2AccountBankSlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountInventorySlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountLegendaryArmoryUnlock
import com.gw2tb.gw2api.types.v2.GW2v2AccountMaterial
import com.gw2tb.gw2api.types.v2.GW2v2AccountWalletCurrency
import com.gw2tb.gw2api.types.v2.GW2v2Character
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * My version of [Gw2eAccountSnapshot], but this is very limited in scope compared to that.
 * I am only interested in items (incl. materials, legendaries) and currencies - for now at least
 */
@Serializable
@JsExport
@optics
data class AccountSnapshot(
    val id: AccountSnapshotId,
    val creationDate: Instant,
    val autoUpdate: Boolean = false,
    val account: SnapshotSource<GW2v2Account> = SnapshotSource.empty(),
    val achievements: SnapshotSource<Map<GW2AchievementId, GW2v2AccountAchievement>> = SnapshotSource.empty(),
    val wallet: SnapshotSource<Map<GW2CurrencyId, GW2v2AccountWalletCurrency>> = SnapshotSource.empty(),
    val shared: SnapshotSource<List<GW2v2AccountInventorySlot?>> = SnapshotSource.empty(),
    val bank: SnapshotSource<List<GW2v2AccountBankSlot?>> = SnapshotSource.empty(),
    val materials: SnapshotSource<Map<GW2ItemId, GW2v2AccountMaterial>> = SnapshotSource.empty(),
    val legendaryarmory: SnapshotSource<Map<GW2ItemId, GW2v2AccountLegendaryArmoryUnlock>> = SnapshotSource.empty(),
    val characterNames: SnapshotSource<List<String>> = SnapshotSource.empty(),
    val characters: Map<String, SnapshotSource<GW2v2Character>> = emptyMap(),
) {

    fun missingCharacterNames(): Set<String> = characterNames.value?.minus(characters.keys)?.toSet() ?: emptySet()

    companion object {
        @JsName("getAccountAchievement")
        operator fun AccountSnapshot?.get(id: GW2AchievementId) = this?.achievements?.value?.get(id)

        @JsName("getAccountWalletCurrency")
        operator fun AccountSnapshot?.get(id: GW2CurrencyId) = this?.wallet?.value?.get(id)

        // operator fun AccountSnapshot?.get(id: GW2ItemId) = too many to search from
        @JsName("getAccountCharacter")
        operator fun AccountSnapshot?.get(id: String) = this?.characters?.get(id)?.value

        fun new() = AccountSnapshot(
            id = AccountSnapshot.AccountSnapshotIdGoon.random(),
            creationDate = Clock.System.now(),
        )

    }

    val totals: Totals by lazy {
        Totals(
            wallet = wallet.value?.mapValues { (_, v) -> v.value } ?: emptyMap(),
            stock = createStock(
                shared = shared.value ?: emptyList(),
                bank = bank.value ?: emptyList(),
                materials = materials.value ?: emptyMap(),
                legendaryarmory = legendaryarmory.value ?: emptyMap(),
                characters = characters.values.mapNotNull { it.value },
            )
        )
    }

    @Serializable
    data class AccountSnapshotMeta(
        val id: AccountSnapshotId,
        val creationDate: Instant,
        val displayName: String,
    )

    @Serializable
    data class SnapshotSource<T : Any>(
        val value: T?,
        val creationDate: Instant,
        val state: Status,
    ) {

        companion object {
            fun <T : Any> empty() = SnapshotSource<T>(
                value = null, creationDate = Instant.DISTANT_PAST, state = Stale
            )

            operator fun <K : Any, T : Any> SnapshotSource<Map<K, T>>?.get(key: K): T? =
                this?.value?.get(key)

        }
    }

    @Serializable
    sealed interface Status

    @Serializable
    object Fresh : Status

    @Serializable
    object Stale : Status
    @Serializable data class Error (val message :String): Status

    @Serializable
    data class UpdateRequested(val at: Instant) : Status

    @Serializable
    sealed interface AccountSnapshotId {}

    @Serializable
    @SerialName("gw2e")
    data class AccountSnapshotIdGw2e(val value: String) : AccountSnapshotId

    @Serializable
    @SerialName("goon")
    data class AccountSnapshotIdGoon
    @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid) : AccountSnapshotId {
        companion object {
            @OptIn(ExperimentalUuidApi::class)
            fun random() = AccountSnapshotIdGoon(Uuid.random())
        }
    }

    @Serializable
    data class Totals(
        val wallet: Map<GW2CurrencyId, Long>,
        val stock: Map<GW2ItemId, Stock>,
    ) {
        fun currency(id: GW2CurrencyId): Long = wallet[id] ?: 0L
        fun items(id: GW2ItemId): Long = stock[id]?.count ?: 0L
    }

    @Serializable
    data class Stock(
        val itemId: GW2ItemId,
        val count: Long,
        val sources: Set<String>,
    ) {
        fun copyPlusCount(count: Long, source: String) = copy(
            count = this.count + count,
            sources = this.sources + source,
        )
    }

}
