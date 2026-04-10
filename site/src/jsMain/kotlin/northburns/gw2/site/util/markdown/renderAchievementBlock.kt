package northburns.gw2.site.util.markdown

import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.v2.GW2v2Achievement
import com.gw2tb.gw2api.types.v2.GW2v2Achievement.Reward
import io.kvision.core.Component
import io.kvision.core.JustifyItems
import io.kvision.html.Align
import io.kvision.html.Div
import io.kvision.html.Em
import io.kvision.html.H5
import io.kvision.html.Span
import io.kvision.html.TextNode
import io.kvision.html.div
import io.kvision.html.h6
import io.kvision.html.image
import io.kvision.html.span
import io.kvision.panel.gridPanel
import northburns.gw2.client.myclient.data.AchievementsCalc
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Placeholder
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Companion.get
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.Gw2GoonManager.playerDataRead
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.SimpleTableColumn
import northburns.gw2.site.layout.components.SimpleTableOptions
import northburns.gw2.site.layout.components.TextWithIconFactory
import northburns.gw2.site.layout.components.bootstrap.card
import northburns.gw2.site.layout.components.simpleTable
import northburns.gw2.site.resolve
import northburns.gw2.site.resolveAchievement
import northburns.gw2.site.resolveBitIcon
import northburns.gw2.site.resolveBitText
import northburns.gw2.site.util.applyToImage
import northburns.gw2.site.util.createImage
import northburns.gw2.site.util.markdown.GoonMarkdownMacros.MacroArgs
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1

internal fun renderAchievementBlock(args: MacroArgs): Component {
    val achievementId = args["id"]?.toLong()?.let(::GW2AchievementId)
        ?: throw IllegalArgumentException("Achievement ID is invalid: ${args["id"]}")
    val mode = args["mode"]
    return Div()
        .goonBindReselect(
            { it.api.resolveAchievement(achievementId) },
            { it.snapshots.snapshots.mapValues { (_, v) -> v[achievementId] } }
        )
        .renderNull1()
        .render { (achievement, accounts) ->
            val playerIds = goonStore.getState().players
            if (playerIds == null) {
                +"Pls select players..."
                return@render
            }
            val (a, c, g) = achievement
            // prepare for the "party view"
            val playerAchievements = playerIds.associateWith { playerId -> accounts[playerId] }
            val calculator = AchievementsCalc.AchievementCalc(a, playerAchievements)


            // TODO any `a.flags` we want to show?
            // a.type = default is any achievement, ItemSet is linked to Collections. Not important.
            // a.tiers, how to show?..


            card {
                addBody {
                    // Top grid
                    gridPanel(
                        templateAreas = listOf(
                            "icon name         ap",
                            "icon requirement  rewards",
                            "icon description  rewards",
                        ),
                        templateColumns = "64px 1fr auto",
                        templateRows = "auto auto 1fr",
                    ) {
                        add(
                            area = "icon",
                            child = achievement.gw2Image().createImage(64),
                        )
                        add(
                            area = "name",
                            child = H5(a.name),
                        )
                        add(
                            area = "requirement",
                            child = Span(a.requirement),
                        )
                        add(
                            area = "description",
                            child = Em(a.description),
                        )
                        add(
                            area = "ap",
                            justifySelf = JustifyItems.END,
                            child = Div {
                                div("${calculator.totalAp()} AP")
                                a.pointCap?.let { pointCap ->
                                    div("max $pointCap AP")
                                }
                            },
                        )
                        a.rewards?.let { rewards ->
                            add(
                                area = "rewards",
                                child = Div {
                                    h6("Rewards:")
                                    rewards.forEach { reward ->
                                        when (reward) {
                                            is Reward.Coins -> +"Coins: ${reward.count}" // TODO show coins
                                            is Reward.Item -> TextWithIconFactory.itemText(
                                                id = reward.id
                                            ).also {
                                                it.addCssClass("block")
                                                add(it)
                                                add(TextNode("(${reward.count})"))
                                            }

                                            is Reward.Mastery -> +"Mastery: ${reward.id}" // TODO show mastery
                                            is Reward.Title -> +"Title: ${reward.id}" // TODO show title
                                        }
                                    }
                                },
                            )
                        }
                    }

                    a.prerequisites?.let { prerequisites ->
                        div {
                            h6("Prerequisites:")
                            prerequisites.map(::GW2AchievementId).forEach { prerequisiteId ->
                                span("").goonBindReselect { it: GoonState -> it.api.resolve(prerequisiteId) }
                                    .render { (prerequisite) ->
                                        content = prerequisite?.name ?: "Loading... " // TODO more details? And layout!
                                    }
                            }
                        }
                    }

                    a.bits?.let { bits ->
                        when (a.type) {
                            "ItemSet" -> {
                                h6("Collection:")
                            }

                            "Default" -> {
                                h6("Objectives:")
                            }

                            else -> span("Unexpected achievement type: '${a.type}'")
                        }

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
                                                    .render {
                                                        it.value.gw2Image().imageAttributes(32).applyToImage(this)
                                                    }
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
                                ) + playerIds.map { playerId ->
                                    SimpleTableColumn(
                                        align = Align.CENTER,
                                        header = { playerDataRead.playerData(playerId).name },
                                        cell = { bitIndex, _ ->
                                            if (calculator.get(playerId).isBitDone(bitIndex)) "✅" else ""
                                        },
                                        footer = {
                                            // TODO This implementation doesn't know if
                                            //  a) player doesn't have achievement, or
                                            //  b) it's just not loaded yet
                                            val accountAchievement = playerAchievements[playerId]
                                            if (accountAchievement?.done == true) {
                                                ("✅")
                                            } else {
                                                val bitsDone = accountAchievement?.bits?.size ?: 0
                                                val bitsTotal = bits.size
                                                ("$bitsDone/$bitsTotal")
                                            }
                                        },
                                    )
                                },
                            ),
                        )
                    }
                    // Bottom grid
                    gridPanel(
                        templateAreas = listOf(
                            "img text player spacer",
                        ),
                        templateColumns = "32px 1fr auto",
                        templateRows = "auto auto 1fr",
                    ) {

                    }
                }
            }


        }
}
