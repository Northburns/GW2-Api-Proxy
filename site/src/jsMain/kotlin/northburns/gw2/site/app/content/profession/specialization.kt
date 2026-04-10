package northburns.gw2.site.app.content.profession

import com.gw2tb.gw2api.types.GW2SpecializationId
import io.kvision.core.Container
import io.kvision.html.div
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.CommonComponents.goonH4
import northburns.gw2.site.layout.components.TextWithIcon
import northburns.gw2.site.resolve
import northburns.gw2.site.resolveMany
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1

fun Container.specialization(specializationId: GW2SpecializationId) {
    div().goonBindReselect { it: GoonState -> it.api.resolve(specializationId) }
        .renderNull1()
        .render { (specialization) ->
            goonH4 {
                TextWithIcon(
                    text = specialization.name,
                    image = specialization.gw2Image(),
                ).also { add(it) }
            }

            // We ignore following fields:
            // weaponTrait
            // elite

            div()
                .goonBindReselect(
                    { it.api.resolveMany(specialization.minorTraits) },
                    { it.api.resolveMany(specialization.majorTraits) },
                ).render { (allMinors, allMajors) ->
                    if (allMinors == null || allMajors == null) {
                        +"Minor/Major Traits 🔄️"
                        return@render
                    }
                    val minors = specialization.minorTraits.associateWith(allMinors::getValue)
                    val majors = specialization.majorTraits.associateWith(allMajors::getValue)

                    val traits = (minors.values + majors.values)
                        .sortedWith(
                            compareBy(
                                { it.tier },
                                { it.order },
                            )
                        )

                    traits.forEach { trait ->
                        trait(trait)
                    }

                }


        }

}