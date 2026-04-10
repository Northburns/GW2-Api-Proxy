package northburns.gw2.client.myclient.snapshot

import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2AccountBankSlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountInventorySlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountLegendaryArmoryUnlock
import com.gw2tb.gw2api.types.v2.GW2v2AccountMaterial
import com.gw2tb.gw2api.types.v2.GW2v2Character
import northburns.gw2.client.myclient.gw2e.Gw2eAccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotIdGoon
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotIdGw2e
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Fresh
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.SnapshotSource
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Stock
import northburns.gw2.client2.MyGw2Client2
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
suspend fun fromClient(api: MyGw2Client2.Authenticated): AccountSnapshot {
    val characterNames = SnapshotSource(api.characters.getIds().sortedBy { it }, Clock.System.now(), Fresh)
    return AccountSnapshot(
        id = AccountSnapshotIdGoon(Uuid.random()),
        creationDate = Clock.System.now(),
        account = SnapshotSource(api.account.get(), Clock.System.now(), Fresh),
        wallet = SnapshotSource(api.wallet.get().associateBy { it.id }, Clock.System.now(), Fresh),
        shared = SnapshotSource(api.inventory.get(), Clock.System.now(), Fresh),
        bank = SnapshotSource(api.bank.get(), Clock.System.now(), Fresh),
        materials = SnapshotSource(api.materials.get().associateBy { it.id }, Clock.System.now(), Fresh),
        legendaryarmory = SnapshotSource(api.legendaryArmory.get().associateBy { it.id }, Clock.System.now(), Fresh),
        characterNames = characterNames,
        characters = api.characters.getAll().mapValues {
            SnapshotSource(it.value, Clock.System.now(), Fresh)
        },
        /*
      account = Account(
          wallet = wallet.associate { it.id to it.value },
          stock = createStock(
              shared = api.inventory.get(),
              bank = api.bank.get(),
              materials = api.materials.get(),
              legendaryarmory = api.legendaryArmory.get(),
              characters = api.characters.getAll().values.map { Character.fromApi(it) },
          ),
      ),*/
    )
}

fun fromGw2e(id: AccountSnapshotIdGw2e, gw2e: Gw2eAccountSnapshot?): AccountSnapshot? {
    return if (gw2e == null) null else
        AccountSnapshot(
            id = id,
            creationDate = gw2e.creationDate,
            account = SnapshotSource(
                value = gw2e.account.account,
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            wallet = SnapshotSource(
                value = gw2e.account.wallet.associateBy { it.id },
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            shared = SnapshotSource(
                value = gw2e.account.shared,
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            bank = SnapshotSource(
                value = gw2e.account.bank,
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            materials = SnapshotSource(
                value = gw2e.account.materials.associateBy { it.id },
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            legendaryarmory = SnapshotSource(
                value = gw2e.account.legendaryarmory.associateBy { it.id },
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            characterNames = SnapshotSource(
                value = gw2e.account.characters.map { it.name },
                creationDate = gw2e.creationDate,
                state = Fresh,
            ),
            characters = gw2e.account.characters.associate {
                it.name to SnapshotSource(
                    value = it,
                    creationDate = gw2e.creationDate,
                    state = Fresh,
                )
            },
            //         account = Account (
            //        age =
            //           wallet = gw2e.account.wallet.associate { it.id to it.value },
            //stock = stockFromGw2e(gw2e),
        )

}

private fun stockFromGw2e(gw2e: Gw2eAccountSnapshot): Map<GW2ItemId, Stock> {
    return createStock(
        shared = gw2e.account.shared,
        bank = gw2e.account.bank,
        materials = gw2e.account.materials.associateBy { it.id },
        legendaryarmory = gw2e.account.legendaryarmory.associateBy { it.id },
        characters = gw2e.account.characters,
    )
}

fun createStock(
    shared: List<GW2v2AccountInventorySlot?>,
    bank: List<GW2v2AccountBankSlot?>,
    materials: Map<GW2ItemId, GW2v2AccountMaterial>,
    legendaryarmory: Map<GW2ItemId, GW2v2AccountLegendaryArmoryUnlock>,
    characters: List<GW2v2Character>,
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
    materials.asSequence().forEach { (_, slot) ->
        stock(slot.id) { copyPlusCount(slot.count, "Material Storage") }
    }

    // Legendary Armory
    legendaryarmory.asSequence().forEach { (_, slot) ->
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
