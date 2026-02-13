package northburns.gw2.client.myclient.snapshot

import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2AccountBankSlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountInventorySlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountLegendaryArmoryUnlock
import com.gw2tb.gw2api.types.v2.GW2v2AccountMaterial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot.Character
import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * My version of [Gw2eAccountSnapshot], but this is very limited in scope compared to that.
 * I am only interested in items (incl. materials, legendaries) and currencies - for now at least
 */
@Serializable
data class AccountSnapshot(
    val id: AccountSnapshotId,
    val creationDate: Instant,
    val account: Account,
) {
    @Serializable
    sealed interface AccountSnapshotId {}

    @JvmInline
    @Serializable
    @SerialName("gw2e")
    value class AccountSnapshotIdGw2e(val value: String) : AccountSnapshotId

    @JvmInline
    @Serializable
    @SerialName("goon")
    value class AccountSnapshotIdGoon
    @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid) : AccountSnapshotId

    @Serializable
    data class Account(
        val age: Duration,
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

    companion object {

        fun fromGw2e(id: AccountSnapshotIdGw2e, gw2e: Gw2eAccountSnapshot?): AccountSnapshot? {
            return if (gw2e == null) null else
                AccountSnapshot(
                    id = id,
                    creationDate = gw2e.creationDate,
                    account = Account(
                        age = gw2e.account.account.age.seconds,
                        wallet = gw2e.account.wallet.associate { it.id to it.value },
                        stock = stockFromGw2e(gw2e),
                    ),
                )
        }

        private fun stockFromGw2e(gw2e: Gw2eAccountSnapshot): Map<GW2ItemId, Stock> {
            return createStock(
                shared = gw2e.account.shared,
                bank = gw2e.account.bank,
                materials = gw2e.account.materials,
                legendaryarmory = gw2e.account.legendaryarmory,
                characters = gw2e.account.characters,
            )
        }

        fun createStock(
            shared: List<GW2v2AccountInventorySlot?>,
            bank: List<GW2v2AccountBankSlot?>,
            materials: List<GW2v2AccountMaterial>,
            legendaryarmory: List<GW2v2AccountLegendaryArmoryUnlock>,
            characters: List<Character>,
        ): Map<GW2ItemId, Stock> {
            val m = mutableMapOf<GW2ItemId, Stock>()
            fun stock(id: GW2ItemId, mutate: Stock.() -> Stock) = m
                .getOrPut(id) { Stock(id, 0, sources = emptySet()) }
                .also { stock -> m[id] = mutate(stock) }

            // Shared
            shared.asSequence().filterNotNull().forEach { slot ->
                stock(slot.id) { copyPlusCount(slot.count, "Shared Inventory") }
            }

            // Bank
            bank.asSequence().filterNotNull().forEach { slot ->
                stock(slot.id) { copyPlusCount(slot.count, "Bank") }
            }

            // Material Storage
            materials.asSequence().forEach { slot ->
                stock(slot.id) { copyPlusCount(slot.count, "Material Storage") }
            }

            // Legendary Armory
            legendaryarmory.forEach { slot ->
                stock(slot.id) { copyPlusCount(slot.count, "Legendary Armory") }
            }

            // Characters
            characters.asSequence().forEach { slot ->
                val name = slot.name
                slot.equipment?.forEach { equipmentSlot ->
                    stock(equipmentSlot.id) { copyPlusCount(1, "Character (equipment): $name") }
                }
                // TODO equipment_tabs

                slot.bags?.forEach { bagSlot ->
                    bagSlot?.inventory?.filterNotNull()?.forEach { slot ->
                        stock(slot.id) { copyPlusCount(slot.count, "Character (bags): $name") }
                    }
                }
            }

            return m
        }


    }
}
