package northburns.gw2.site.app.snapshotstatus

import arrow.core.getOrElse
import arrow.core.toOption
import arrow.optics.copy
import arrow.optics.dsl.at
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Error
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Fresh
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.SnapshotSource
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.SnapshotSource.Companion.empty
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Stale
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.UpdateRequested
import northburns.gw2.client.myclient.snapshot.account
import northburns.gw2.client.myclient.snapshot.achievements
import northburns.gw2.client.myclient.snapshot.bank
import northburns.gw2.client.myclient.snapshot.characterNames
import northburns.gw2.client.myclient.snapshot.characters
import northburns.gw2.client.myclient.snapshot.legendaryarmory
import northburns.gw2.client.myclient.snapshot.materials
import northburns.gw2.client.myclient.snapshot.shared
import northburns.gw2.client.myclient.snapshot.wallet
import northburns.gw2.site.actions.SnapshotAction
import northburns.gw2.site.actions.SnapshotActionType
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.SnapshotsContainer
import northburns.gw2.site.app.snapshots

fun snapshotReducer(state: GoonState, action: SnapshotAction<*>): GoonState =
    state.copy {
        inside(GoonState.snapshots) {
            SnapshotsContainer.snapshots.at(action.playerId) transform { snapshot ->
                snapshot.getOrElse { AccountSnapshot.new() }.copy {
                    when (action) {
                        // Simple
                        is SnapshotAction.TargetAccount -> AccountSnapshot.account.transform(action.modify())
                        is SnapshotAction.TargetAchievements -> AccountSnapshot.achievements.transform(action.modify())
                        is SnapshotAction.TargetBank -> AccountSnapshot.bank.transform(action.modify())
                        is SnapshotAction.TargetLegendary -> AccountSnapshot.legendaryarmory.transform(action.modify())
                        is SnapshotAction.TargetMaterials -> AccountSnapshot.materials.transform(action.modify())
                        is SnapshotAction.TargetShared -> AccountSnapshot.shared.transform(action.modify())
                        is SnapshotAction.TargetWallet -> AccountSnapshot.wallet.transform(action.modify())

                        // Complex
                        is SnapshotAction.TargetCharacternames -> {
                            AccountSnapshot.characterNames.transform(action.modify())
                            (action.type as? SnapshotActionType.ActionSetUpdate)?.value?.let { characterNames ->
                                AccountSnapshot.characters.transform {
                                    val oldNames = it.keys
                                    val newNames = characterNames.toSet()
                                    it
                                        .minus(oldNames - newNames)
                                        .plus((newNames - oldNames).associateWith { empty() })
                                }
                            }
                        }

                        is SnapshotAction.TargetCharacter -> {
                            val characterName = action.characterName
                            AccountSnapshot.characters.at(characterName) transform {
                                action.modify()(it.getOrElse { empty() }).toOption()
                            }
                        }

                    }
                }.toOption()
            }
        }
    }

private fun <T : Any> SnapshotAction<T>.modify(): (SnapshotSource<T>) -> SnapshotSource<T> {
    return { source ->
        @Suppress("UNCHECKED_CAST")
        when (val t = type) {
            is SnapshotActionType.ActionSetError<*> -> source.copy(state = Error(t.message))
            is SnapshotActionType.ActionSetRefresh<*> -> source.copy(state = UpdateRequested(t.timestamp))
            is SnapshotActionType.ActionSetStale<*> -> source.copy(state = Stale)
            is SnapshotActionType.ActionSetUpdate<*> -> source.copy(
                value = t.value as T?,
                creationDate = t.timestamp,
                state = Fresh
            )
        }
    }
}
