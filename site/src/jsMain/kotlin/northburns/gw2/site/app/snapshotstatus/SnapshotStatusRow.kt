package northburns.gw2.site.app.snapshotstatus

import com.gw2tb.gw2api.types.GW2ItemId
import northburns.gw2.client.myclient.data.Gw2Ids.`Bank Tab Expansion`
import northburns.gw2.client.myclient.data.Gw2Ids.Eternity
import northburns.gw2.client.myclient.data.Gw2Ids.`Material Storage Expander`
import northburns.gw2.client.myclient.data.Gw2Ids.`Shared Inventory Slot`
import northburns.gw2.client.myclient.data.Gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2ImageBig
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2ImageProfessionBig
import northburns.gw2.client.myclient.data.Gw2Image.Placeholder
import northburns.gw2.client.myclient.data.Gw2WikiProfessionColors.Companion.getColors
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Companion.getProfessionElite
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.resolve
import kotlin.time.Instant

/**
 * For use with SnapshotStatusView
 */
@JsExport
data class SnapshotStatusRow(
    val icon: Gw2Image,
    val text: String,
    val updated: Instant,
    val state: AccountSnapshot.Status,
    val id: String = text,
) {
    companion object {
        fun fromState(state: GoonState): List<SnapshotStatusRow> {
            val players = state.players ?: return emptyList()
            return players.mapNotNull { state.snapshots.snapshots[it] }
                .flatMap { snapshot ->
                    snapshot.run {
                        val constant = listOf(
                            SnapshotStatusRow(
                                icon = state.api.resolve(GW2ItemId(102649)).gw2Image(),
                                text = "Account",
                                updated = account.creationDate,
                                state = account.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(GW2ItemId(102649)).gw2Image(),
                                text = "Achievements",
                                updated = achievements.creationDate,
                                state = achievements.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(GW2ItemId(8939)).gw2Image(),
                                text = "Wallet",
                                updated = wallet.creationDate,
                                state = wallet.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(`Shared Inventory Slot`).gw2Image(),
                                text = "Shared Inventory",
                                updated = shared.creationDate,
                                state = shared.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(`Bank Tab Expansion`).gw2Image(),
                                text = "Bank",
                                updated = bank.creationDate,
                                state = bank.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(`Material Storage Expander`).gw2Image(),
                                text = "Materials",
                                updated = materials.creationDate,
                                state = materials.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(Eternity).gw2Image(),
                                text = "Legendaries",
                                updated = legendaryarmory.creationDate,
                                state = legendaryarmory.state,
                            ),
                            SnapshotStatusRow(
                                icon = state.api.resolve(GW2ItemId(20127)).gw2Image(),
                                text = "Characters",
                                updated = characterNames.creationDate,
                                state = characterNames.state,
                            ),
                        )
                        val characters = characters.entries.map { (characterName, character) ->
                            val image = character.value
                                ?.getProfessionElite()
                                ?.resolveMaybe(state.api::resolve, state.api::resolve)
                                ?.fold(
                                    ifLeft = { profession ->
                                        profession.gw2ImageBig()
                                            .addStyle("--profession-color", profession.getColors().medium)
                                    },
                                    ifRight = { (profession, elite) ->
                                        elite.gw2ImageProfessionBig()
                                            .addStyle("--profession-color", profession.getColors().medium)
                                    })
                                ?.addStyle("--shadow-length", "2px")
                                ?.addClassname("profession-icon-color-shadow")
                                ?: Placeholder
                            SnapshotStatusRow(
                                icon = image,
                                text = characterName,
                                updated = character.creationDate,
                                state = character.state,
                            )
                        } ?: emptyList()

                        constant + characters
                    }
                }
        }
    }
}
