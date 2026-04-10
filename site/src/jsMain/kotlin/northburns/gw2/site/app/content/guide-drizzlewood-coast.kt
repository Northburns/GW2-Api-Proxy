package northburns.gw2.site.app.content

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import northburns.gw2.client.myclient.data.Gw2Ids.AchievementCategories.`No Quarter`
import northburns.gw2.site.layout.components.CommonComponents.goonUl
import northburns.gw2.site.resolve
import northburns.gw2.site.resolveMany
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1

fun Container.`guide-drizzlewood-coast`() {
    div()
        .goonBindReselect({ it.api.resolve(`No Quarter`) })
        .renderNull1 { +"Loading achievement category 🔄️" }
        .render { (cat) ->

            val achievementIds = cat.achievements.map { it.id }
                .filterNot { it.raw == 5284L } // https://github.com/GW2ToolBelt/api-generator/issues/558

            +achievementIds.joinToString(",") { it.raw.toString() }


            goonBindReselect({ it.api.resolveMany(achievementIds) })
                .renderNull1 { +"Loading achievements 🔄️" }
                .render { (allAchievements) ->
                    val achievements = achievementIds.associateWith { allAchievements.getValue(it) }

                    //val specialMissions = achievements.values.filter { it.name }


                    goonUl {
                        achievements.forEach { (k, v) ->
                            li("${k.raw} = ${v.name}")
                        }
                    }

                }

        }


}