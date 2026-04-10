package northburns.gw2.site.app.content

import io.kvision.core.Container
import io.kvision.html.div
import northburns.gw2.client.myclient.data.calc.ProfessionId
import northburns.gw2.client.myclient.data.calc.ProfessionId.Elementalist
import northburns.gw2.client.myclient.data.calc.ProfessionId.Engineer
import northburns.gw2.client.myclient.data.calc.ProfessionId.Guardian
import northburns.gw2.client.myclient.data.calc.ProfessionId.Mesmer
import northburns.gw2.client.myclient.data.calc.ProfessionId.Necromancer
import northburns.gw2.client.myclient.data.calc.ProfessionId.Ranger
import northburns.gw2.client.myclient.data.calc.ProfessionId.Revenant
import northburns.gw2.client.myclient.data.calc.ProfessionId.Thief
import northburns.gw2.client.myclient.data.calc.ProfessionId.Warrior
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.Gw2GoonManager.goonNav
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.app.GoonPath.REF_PROFESSION
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_ELEMENTALIST
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_ENGINEEER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_GUARDIAN
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_MESMER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_NECROMANCER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_RANGER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_REVENANT
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_THIEF
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_WARRIOR
import northburns.gw2.site.app.content.profession.profession
import northburns.gw2.site.layout.components.CommonComponents.goonH2
import northburns.gw2.site.layout.components.GoonButton
import northburns.gw2.site.layout.components.goonButton
import northburns.gw2.site.util.tailwind.tw

fun Container.professionReference(path: GoonPath, params: Map<String, String>) {
    GoonLog["professionRef"].info(path.toString())
    val selectedProfession = path.toProfessionId()

    if (selectedProfession == null) {
        goonH2("Select Profession")

        div(className = tw {
            +"flex flex-wrap mx-4"
        }) {
            ProfessionId.entries.forEach { profession ->
                goonButton(
                    style = GoonButton.GoonButtonStyle.ACTION,
                    text = profession.name,
                ) { mouseEvent ->
                    goonNav.navigateTo(profession.toGoonPath())
                }
            }
        }

    } else {
        profession(selectedProfession,path, params)
    }
}

private fun GoonPath.toProfessionId() = when (this) {
    REF_PROFESSION_GUARDIAN -> Guardian
    REF_PROFESSION_REVENANT -> Revenant
    REF_PROFESSION_WARRIOR -> Warrior
    REF_PROFESSION_ENGINEEER -> Engineer
    REF_PROFESSION_RANGER -> Ranger
    REF_PROFESSION_THIEF -> Thief
    REF_PROFESSION_ELEMENTALIST -> Elementalist
    REF_PROFESSION_MESMER -> Mesmer
    REF_PROFESSION_NECROMANCER -> Necromancer
    REF_PROFESSION -> null
    else -> throw IllegalArgumentException("Unexpected GoonPath: $this")
}

private fun ProfessionId.toGoonPath() = when (this) {
    Guardian -> GoonPath.REF_PROFESSION_GUARDIAN
    Warrior -> GoonPath.REF_PROFESSION_WARRIOR
    Engineer -> GoonPath.REF_PROFESSION_ENGINEEER
    Ranger -> GoonPath.REF_PROFESSION_RANGER
    Thief -> GoonPath.REF_PROFESSION_THIEF
    Elementalist -> GoonPath.REF_PROFESSION_ELEMENTALIST
    Mesmer -> GoonPath.REF_PROFESSION_MESMER
    Necromancer -> GoonPath.REF_PROFESSION_NECROMANCER
    Revenant -> GoonPath.REF_PROFESSION_REVENANT
}