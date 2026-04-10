package northburns.gw2.site.app.content

import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.v2.GW2v2AccountAchievement
import com.gw2tb.gw2api.types.v2.GW2v2Achievement
import io.kvision.core.Container
import io.kvision.html.Align
import io.kvision.html.div
import io.kvision.html.image
import io.kvision.html.p
import io.kvision.html.span
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.data.AchievementsCalc
import northburns.gw2.client.myclient.data.Gw2Ids.VisionIds
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Placeholder
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.Gw2GoonManager.playerDataRead
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.CommonComponents.goonH1
import northburns.gw2.site.layout.components.CommonComponents.goonH2
import northburns.gw2.site.layout.components.CommonComponents.goonH4
import northburns.gw2.site.layout.components.FactSheet
import northburns.gw2.site.layout.components.SimpleTableColumn
import northburns.gw2.site.layout.components.SimpleTableOptions
import northburns.gw2.site.layout.components.TextWithIcon
import northburns.gw2.site.layout.components.simpleTable
import northburns.gw2.site.resolveAchievement
import northburns.gw2.site.resolveBitIcon
import northburns.gw2.site.resolveBitText
import northburns.gw2.site.resolveMany
import northburns.gw2.site.util.applyToImage
import northburns.gw2.site.util.reselect.goonBindReselect

fun Container.guideLegeVision() {
    span("VISIONJ GUDIE HEREEE")

    val playerIds = goonStore.getState().players
    if (playerIds == null) {
        +"Select players!"
        return
    }

    goonH1("Vision")

    FactSheet().also { add(it) }

    goonH2("Vision I: Awakening")

    div()
        .goonBindReselect { it: GoonState -> it.snapshots.achievementsByPlayer }
        .render { (achievementsByPlayer) ->

            val ctx = AchievementContext(playerIds, achievementsByPlayer)

            doAchievement(ctx, VisionIds.`Vision I - Awakening`)

            doAchievement(ctx, VisionIds.`Visions of Kourna`)

            doAchievement(ctx, VisionIds.`Visions of Istan`)
            doAchievement(ctx, VisionIds.Daybreak)
            doAchievement(ctx, VisionIds.`Visions of Sandswept Isles`)
            doAchievement(ctx, VisionIds.`A Bug in the System`)
            doAchievement(ctx, VisionIds.`Visions of Kourna`)
            doAchievement(ctx, VisionIds.`Long Live the Lich`)
            doAchievement(ctx, VisionIds.`Visions of Jahai`)
            doAchievement(ctx, VisionIds.`A Star to Guide Us`)
            doAchievement(ctx, VisionIds.`Visions of Thunderhead Peaks`)
            doAchievement(ctx, VisionIds.`All or Nothing`)
            doAchievement(ctx, VisionIds.`Visions of Dragonfall`)
            doAchievement(ctx, VisionIds.`War Eternal`)

            doMasteryAchievement(ctx, "Daybreak", 30, VisionIds.`Daybreak Achievements`)
            doMasteryAchievement(ctx, "A Bug in the System", 35, VisionIds.`A Bug in the System Achievements`)
            doMasteryAchievement(ctx, "Long Live the Lich", 38, VisionIds.`Long Live the Lich Achievements`)
            doMasteryAchievement(ctx, "A Star to Guide Us", 38, VisionIds.`A Star to Guide Us Achievements`)
            doMasteryAchievement(ctx, "All or Nothing", 30, VisionIds.`All or Nothing Achievements`)
            doMasteryAchievement(ctx, "War Eternal", 18, VisionIds.`War Eternal Achievements`)

        }

    goonH2("Vision II: Farsight")

    p("TODO")
}

private class AchievementContext(
    val playerIds: List<PlayerId>,
    val achievementsByPlayer: Map<PlayerId, Map<GW2AchievementId, GW2v2AccountAchievement>?>,
) {
    fun get(playerId: PlayerId, achievementId: GW2AchievementId) =
        achievementsByPlayer[playerId]?.get(achievementId)

    fun isLoaded(playerId: PlayerId) = achievementsByPlayer[playerId] != null
}

private fun Container.doMasteryAchievement(
    ctx: AchievementContext,
    title: String,
    complete: Int,
    achievementIds: List<GW2AchievementId>,
) {
    goonH4("$title ($complete)")
    div()
        .goonBindReselect(
            { it.api.resolveMany(achievementIds) },
            { "" }
        ).render { (allAchievements, _) ->
            if (allAchievements == null) {
                +"🔃"
                return@render
            }
            val achievements = achievementIds.associateBy(allAchievements::getValue)

            simpleTable(
                data = achievementIds,
                options = SimpleTableOptions(
                    footer = true,
                    columns = listOf(
                        SimpleTableColumn<GW2AchievementId>(
                            header = { "Achievement" },
                            cell = { _, achievementId -> allAchievements.getValue(achievementId).name },
                        ),
                    ) + ctx.playerIds.map { playerId ->
                        SimpleTableColumn(
                            header = { playerId.id },
                            cell = { _, achievementId ->
                                if (ctx.get(playerId, achievementId)?.done == true) "✅" else ""
                            },
                            footer = {
                                val done = achievementIds.filter { ctx.get(playerId, it)?.done == true }.size
                                done.toString()
                            }
                        )
                    },
                ),
            )

        }
}

private fun Container.doAchievement(
    ctx: AchievementContext,
    achievementId: GW2AchievementId,
) {
    div()
        .goonBindReselect(
            { it.api.resolveAchievement(achievementId) },
            { it.snapshots.achievementsByPlayer },
        ).render { (achievement, accountAchievementsByPlayers) ->
            if (achievement == null) {
                +"🔄️"
                return@render
            }

            val a = achievement.a
            val bits = a.bits

            val playerAchievements = accountAchievementsByPlayers.mapValues { (_, v) -> v?.get(achievementId) }
            val calculator = AchievementsCalc.AchievementCalc(a, playerAchievements)

            TextWithIcon(a.name, achievement.gw2Image())

            if (bits == null)
                +"TODO a.bits == null"
            else
                simpleTable(
                    data = bits,
                    options = SimpleTableOptions(
                        footer = true,
                        columns = listOf(
                            SimpleTableColumn<GW2v2Achievement.Bit>(
                                align = Align.CENTER,
                                cellInit = { _, bit ->
                                    if (bit !is GW2v2Achievement.Bit.Text)
                                        image(Placeholder.src(32))
                                            .goonBindReselect { it: GoonState ->
                                                it.api.resolveBitIcon(
                                                    bit
                                                )
                                            }
                                            .render { (it) -> it.gw2Image().imageAttributes(32).applyToImage(this) }
                                },
                            ),

                            SimpleTableColumn(
                                align = Align.LEFT,
                                header = { "Bit" },
                                cellInit = { _, bit ->
                                    span("Loading...")
                                        .goonBindReselect { it: GoonState -> it.api.resolveBitText(bit) }
                                        .render { content = it.value }
                                },
                            ),
                        ) + ctx.playerIds.map { playerId ->
                            SimpleTableColumn(
                                align = Align.CENTER,
                                header = { playerDataRead.playerData(playerId).name },
                                cell = { bitIndex, _ ->
                                    if (calculator.get(playerId).isBitDone(bitIndex)) "✅" else ""
                                },
                                footer = {
                                    val accountAchievement = playerAchievements[playerId]
                                    if (!ctx.isLoaded(playerId)) {
                                        ("🔄️")
                                    } else if (accountAchievement?.done == true) {
                                        ("✅")
                                    } else {
                                        val bitsDone = accountAchievement?.bits?.size ?: 0
                                        val bitsTotal = bits.size
                                        ("$bitsDone/$bitsTotal")
                                    }
                                },
                            )
                        },
                    )
                )

        }
}