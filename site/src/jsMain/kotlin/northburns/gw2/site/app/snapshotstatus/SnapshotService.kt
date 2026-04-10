package northburns.gw2.site.app.snapshotstatus

import io.kvision.redux.TypedReduxStore
import io.kvision.state.ObservableState
import io.kvision.state.sub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Fresh
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.SnapshotSource
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Stale
import northburns.gw2.client.myclient.utils.ChunkedChannel
import northburns.gw2.client.myclient.utils.myTicker
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.actions.SnapshotAction.TargetAccount
import northburns.gw2.site.actions.SnapshotAction.TargetAchievements
import northburns.gw2.site.actions.SnapshotAction.TargetBank
import northburns.gw2.site.actions.SnapshotAction.TargetCharacter
import northburns.gw2.site.actions.SnapshotAction.TargetCharacternames
import northburns.gw2.site.actions.SnapshotAction.TargetLegendary
import northburns.gw2.site.actions.SnapshotAction.TargetMaterials
import northburns.gw2.site.actions.SnapshotAction.TargetShared
import northburns.gw2.site.actions.SnapshotAction.TargetWallet
import northburns.gw2.site.actions.SnapshotActionType.ActionSetRefresh
import northburns.gw2.site.actions.SnapshotActionType.ActionSetStale
import northburns.gw2.site.actions.SnapshotActionType.ActionSetUpdate
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.RequestService
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class SnapshotService(
    private val scope: CoroutineScope,
    private val store: TypedReduxStore<GoonState, GoonAction>,
    private val r: RequestService,
) : AutoCloseable {
    /*
        This service handles current snapshot's fields

        1. [SnapshotStalenessDetector] runs every 10 seconds, and marks as STALE any snapshot older than 5 minutes
        2. [SnapshotUpdateRequestActuator] subscribes to STALE snapshots, and does:
          a. marks UPDATE_REQUESTED on snapshot
          b. requests update from [SnapshotRequestOps]
        3. [SnapshotRequestOps] batches update requests together, and updates FRESH data

        All fields are private. It runs on its own and updates current snapshot.
     */

    private val request = SnapshotRequestOps()
    private val updateActuator = SnapshotUpdateRequestActuator()
    private val staleDetector = SnapshotStalenessDetector(10.seconds, 5.minutes)

    override fun close() {
        updateActuator.cancel()
        staleDetector.close()
    }

    inner class SnapshotRequestOps {
        val account = ChunkedChannel(scope, "SnapshotService_account") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).account.get()
                store.dispatch(TargetAccount(playerId, ActionSetUpdate(v)))
            }
        }
        val achievements = ChunkedChannel(scope, "SnapshotService_achievements") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).achievements.get().associateBy { it.id }
                store.dispatch(TargetAchievements(playerId, ActionSetUpdate(v)))
            }
        }
        val wallet = ChunkedChannel(scope, "SnapshotService_wallet") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).wallet.get().associateBy { it.id }
                store.dispatch(TargetWallet(playerId, ActionSetUpdate(v)))
            }
        }
        val shared = ChunkedChannel(scope, "SnapshotService_shared") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).inventory.get()
                store.dispatch(TargetShared(playerId, ActionSetUpdate(v)))
            }
        }
        val bank = ChunkedChannel(scope, "SnapshotService_bank") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).bank.get()
                store.dispatch(TargetBank(playerId, ActionSetUpdate(v)))
            }
        }
        val materials = ChunkedChannel(scope, "SnapshotService_materials") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).materials.get().associateBy { it.id }
                store.dispatch(TargetMaterials(playerId, ActionSetUpdate(v)))
            }
        }
        val legendaryarmory = ChunkedChannel(scope, "SnapshotService_legendaryarmory") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).legendaryArmory.get().associateBy { it.id }
                store.dispatch(TargetLegendary(playerId, ActionSetUpdate(v)))
            }
        }
        val characterNames = ChunkedChannel(scope, "SnapshotService_characterNames") { playerIds ->
            playerIds.forEach { playerId ->
                val v = Gw2GoonManager.api.client.auth(playerId).characters.getIds().sortedBy { it }
                store.dispatch(TargetCharacternames(playerId, ActionSetUpdate(v)))
            }
        }
        val characters =
            ChunkedChannel<Pair<PlayerId, String>>(scope, "SnapshotService_characters", chunkSize = 5) { chunk ->
                val byPlayer: Map<PlayerId, List<String>> = chunk.groupBy({ it.first }, { it.second })
                byPlayer.entries.forEach { (playerId, characterNames) ->
                    val vs = Gw2GoonManager.api.client.auth(playerId).characters.getMany(characterNames)
                    val creationDate = Clock.System.now()
                    vs.forEach { character ->
                        store.dispatch(TargetCharacter(playerId, character.name, ActionSetUpdate(character)))
                    }
                }
            }
    }


    private inner class SnapshotUpdateRequestActuator {
        private val cancellations = listOf(
            store.onStale(AccountSnapshot::account) {
                request.account(it)
                store.dispatch(TargetAccount(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::achievements) {
                request.achievements(it)
                store.dispatch(TargetAchievements(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::wallet) {
                request.wallet(it)
                store.dispatch(TargetWallet(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::shared) {
                request.shared(it)
                store.dispatch(TargetShared(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::bank) {
                request.bank(it)
                store.dispatch(TargetBank(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::materials) {
                request.materials(it)
                store.dispatch(TargetMaterials(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::legendaryarmory) {
                request.legendaryarmory(it)
                store.dispatch(TargetLegendary(it, ActionSetRefresh()))
            },
            store.onStale(AccountSnapshot::characterNames) {
                request.characterNames(it)
                store.dispatch(TargetCharacternames(it, ActionSetRefresh()))
            },

            // Sub to character update requests
            store.sub { it.snapshots.snapshots.mapValues { (_, s) -> s.characters.filterValues { c -> c.state == Stale } } }
                .subscribe { updateRequestedOn ->
                    updateRequestedOn.forEach { (playerId, characters) ->
                        characters.forEach { (characterName, character) ->
                            request.characters(playerId to characterName)
                            store.dispatch(TargetCharacter(playerId, characterName, ActionSetRefresh()))
                        }
                    }
                },
//            // Sub to missing characters
//            store.sub { it.currentPlayer to it.snapshot?.missingCharacterNames() }.subscribe { (playerId, names) ->
//                if (playerId != null) names?.forEach { characterName ->
//                    request.characters(playerId to characterName)
//                    store.dispatch(SnapshotRequestedCharacterAction(playerId, characterName))
//                }
//            }
        )

        fun cancel() = cancellations.forEach { it() }


        private fun <T : Any> ObservableState<GoonState>.onStale(
            slice: AccountSnapshot.() -> SnapshotSource<T>,
            actionOnNull: (playerId: PlayerId) -> Unit,
        ): () -> Unit {
            return sub {
                it.snapshots.snapshots.mapValues { (_, snapshot) -> snapshot.slice() }.filterValues { c -> c.state == Stale }
            }.subscribe { updateRequested ->
                updateRequested.forEach { (playerId, updateReq) ->
                    if (updateReq.state == Stale)
                        actionOnNull(playerId)
                }
            }
        }

    }

    private inner class SnapshotStalenessDetector(
        checkInterval: Duration,
        private val staleIn: Duration,
    ) : AutoCloseable {
        private val ticker = scope.myTicker(delayMillis = checkInterval.inWholeMilliseconds, initialDelayMillis = 0L)
        private val job = scope.launch {
            ticker.consumeAsFlow().collect {
                checkStaleness()
            }
        }

        private fun checkStaleness() {
            store.getState().snapshots.snapshots.forEach { (p, snapshot) ->
                val autoupdate = snapshot.autoUpdate
                // Napier.i { "Current snapshot staleness check" }
                onNewlyStale(autoupdate, snapshot.account) { TargetAccount(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.achievements) { TargetAchievements(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.wallet) { TargetWallet(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.shared) { TargetShared(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.bank) { TargetBank(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.materials) { TargetMaterials(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.legendaryarmory) { TargetLegendary(p, ActionSetStale()) }
                onNewlyStale(autoupdate, snapshot.characterNames) { TargetCharacternames(p, ActionSetStale()) }
                // Only check the characters which have a name in the name array
                snapshot.characterNames.value?.forEach { characterName ->
                    val character = snapshot.characters[characterName]
                    onNewlyStale(autoupdate, character) { TargetCharacter(p, characterName, ActionSetStale()) }
                }
            }
        }

        private fun onNewlyStale(autoupdate: Boolean, s: SnapshotSource<*>?, action: () -> GoonAction) {
            if (s.isNewlyStale(autoupdate)) store.dispatch(action())
        }

        private fun SnapshotSource<*>?.isNewlyStale(autoupdate: Boolean): Boolean {
            // Missing? Yeah, update!
            return if (this == null || this.value == null) true
            // Autoupdate off? Don't update
            else if (!autoupdate) false
            // Only deem "fresh" as newly stale
            else state == Fresh && (Clock.System.now() - creationDate) > staleIn
        }

        override fun close() {
            job.cancel()
        }
    }

}

