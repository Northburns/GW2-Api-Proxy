package northburns.gw2.site.app.account

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import io.kvision.html.p
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Companion.getProfessionElite
import northburns.gw2.client.myclient.data.calc.ProfessionSkillsBundler
import northburns.gw2.client.myclient.data.calc.getActiveBuildTab
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.CommonComponents.goonH3
import northburns.gw2.site.layout.components.CommonComponents.goonUl
import northburns.gw2.site.resolve
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1
import northburns.gw2.site.util.reselect.renderNull2
import northburns.gw2.site.util.reselect.renderNull3

fun Container.character(params: Map<String, String>) {
    val playerId = params["account"]?.let(::PlayerId)
    if (playerId == null) {
        p("PlayerId is null?..")
        return
    }
    val characterName = params["character"]
    if (characterName.isNullOrEmpty()) {
        p("CharacterName is null?..")
        return
    }

    fun GoonState.getCharacter() = snapshots.snapshots[playerId]?.characters?.get(characterName)?.value

    div()
        .goonBindReselect(
            { it.account?.accountsById?.get(playerId) },
            { it.getCharacter() },
            { s -> s.getCharacter()?.let { s.api.resolve(it.profession) } },
            { s -> s.getCharacter()?.let { s.api.resolve(it.getProfessionElite().eliteId) } }
        )
        .renderNull1()
        .renderNull2()
        .renderNull3()
        .render { (account, character, profession, eliteSpecialization) ->

            goonH3("${account.name}: ${character.name}")

            val professionEliteId = character.getProfessionElite()
            val b = ProfessionSkillsBundler(profession)

            // Missäs järjestyksessä asiat esittäis?
            //
            // Gear + stats :thinking:
            // Skills (weapons, utility, jne)
            //
            // Hmm, miten olis sama järkkä ku täällä: https://en.gw2skills.net/editor/
            // Ensin skills (F1-F5, weapon skills, heal, utility, ult)
            // Sitten specializations
            // sitten GEAR
            // Mä olisn kyll aika velho jos tekisin tosta mobiiliversion :D
            // NYT VAA READ-ONLY

            val build = character.getActiveBuildTab()?.build
            if (build == null) {
                +"no active buildtab?!"
                return@render
            }

            // Heal div
            div()
                .goonBindReselect { it.api.resolve(build.skills.heal) }
                .renderNull1()
                .render { (skill) ->
                    goonUl {
                        li("name: " + skill.name)
                        li("description: " + skill.description, rich = true)
                        li("icon: " + skill.icon)
                        li("flags: " + skill.flags)
                        li("facts: " + skill.facts)
                        li("traitedFacts: " + skill.traitedFacts)
                        li("categories: " + skill.categories)
                        li("attunement: " + skill.attunement)
                        li("cost: " + skill.cost)
                        li("dualWield: " + skill.dualWield)
                        li("flipSkill: " + skill.flipSkill)
                        li("initiative: " + skill.initiative)
                        li("nextChain: " + skill.nextChain)
                        li("prevChain: " + skill.prevChain)
                        li("transformSkills: " + skill.transformSkills)
                        li("bundleSkills: " + skill.bundleSkills)
                        li("toolbeltSkill: " + skill.toolbeltSkill)
                    }
                }


        }

}