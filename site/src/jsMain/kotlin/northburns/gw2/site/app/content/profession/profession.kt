package northburns.gw2.site.app.content.profession

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import northburns.gw2.client.myclient.data.Gw2Ids
import northburns.gw2.client.myclient.data.calc.ProfessionId
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.CommonComponents.goonH2
import northburns.gw2.site.layout.components.CommonComponents.goonUl
import northburns.gw2.site.resolve
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1

fun Container.profession(professionId: ProfessionId, gp: GoonPath, params: Map<String, String>) {
    div()
        .goonBindReselect { it: GoonState -> it.api.resolve(professionId.professionId) }
        .renderNull1()
        .render { (profession) ->
            goonH2(profession.name)

            +"Flags: ${profession.flags}"

            +"Specializations"

            val specializations = Gw2Ids.professionSpecializations.getValue(profession.id)
            val elites = Gw2Ids.professionElites.getValue(profession.id)

            (specializations + elites).forEach { specializationId ->
                specialization(specializationId)
            }

            +"Weapons"

            goonUl {
                profession.weapons.forEach { (name, weapon) ->
                    li("$name $weapon")
                }
            }

            +"Skills"

            goonUl {
                profession.skills.forEach { skill ->
                    li(skill.toString())
                }
            }

            +"Training"

            goonUl {
                profession.training.forEach { training ->
                    li(training.toString())
                }
            }


        }

}