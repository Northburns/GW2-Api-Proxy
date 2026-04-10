package northburns.gw2.site.actions

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
import northburns.gw2.client.myclient.PlayerId
import kotlin.time.Clock
import kotlin.time.Instant


sealed interface SnapshotAction<T : Any> : GoonAction {
    val playerId: PlayerId
    val type: SnapshotActionType<T>

    data class TargetAccount(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<GW2v2Account>
    ) : SnapshotAction<GW2v2Account>

    data class TargetAchievements(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<Map<GW2AchievementId, GW2v2AccountAchievement>>,
    ) : SnapshotAction<Map<GW2AchievementId, GW2v2AccountAchievement>>

    data class TargetWallet(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<Map<GW2CurrencyId, GW2v2AccountWalletCurrency>>,
    ) : SnapshotAction<Map<GW2CurrencyId, GW2v2AccountWalletCurrency>>

    data class TargetShared(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<List<GW2v2AccountInventorySlot?>>,
    ) : SnapshotAction<List<GW2v2AccountInventorySlot?>>

    data class TargetBank(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<List<GW2v2AccountBankSlot?>>,
    ) : SnapshotAction<List<GW2v2AccountBankSlot?>>

    data class TargetMaterials(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<Map<GW2ItemId, GW2v2AccountMaterial>>,
    ) : SnapshotAction<Map<GW2ItemId, GW2v2AccountMaterial>>

    data class TargetLegendary(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<Map<GW2ItemId, GW2v2AccountLegendaryArmoryUnlock>>,
    ) : SnapshotAction<Map<GW2ItemId, GW2v2AccountLegendaryArmoryUnlock>>

    data class TargetCharacternames(
        override val playerId: PlayerId,
        override val type: SnapshotActionType<List<String>>,
    ) : SnapshotAction<List<String>>

    data class TargetCharacter(
        override val playerId: PlayerId,
        val characterName: String,
        override val type: SnapshotActionType<GW2v2Character>,
    ) : SnapshotAction<GW2v2Character>
}

sealed interface SnapshotActionType<T : Any> {
    val timestamp: Instant

    data class ActionSetStale<T : Any>(
        override val timestamp: Instant = Clock.System.now(),
    ) : SnapshotActionType<T>

    data class ActionSetRefresh<T : Any>(
        override val timestamp: Instant = Clock.System.now(),
    ) : SnapshotActionType<T>

    data class ActionSetUpdate<T : Any>(
        val value: T,
        override val timestamp: Instant = Clock.System.now(),
    ) : SnapshotActionType<T>

    data class ActionSetError<T : Any>(
        val message: String,
        override val timestamp: Instant = Clock.System.now(),
    ) : SnapshotActionType<T>
}
