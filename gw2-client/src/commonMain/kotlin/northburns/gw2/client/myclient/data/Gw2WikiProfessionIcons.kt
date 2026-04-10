package northburns.gw2.client.myclient.data

import northburns.gw2.client.myclient.data.calc.ProfessionEliteId
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Amalgam
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Antiquary
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Berserker
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Bladesworn
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Catalyst
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Chronomancer
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Conduit
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Daredevil
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Deadeye
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Dragonhunter
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Druid
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Elementalist
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Engineer
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Evoker
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Firebrand
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Galeshot
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Guardian
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Harbinger
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Herald
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Holosmith
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Luminary
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Mechanist
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Mesmer
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Mirage
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Necromancer
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Paragon
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Ranger
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Reaper
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Renegade
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Revenant
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Ritualist
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Scourge
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Scrapper
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Soulbeast
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Specter
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Spellbreaker
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Tempest
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Thief
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Troubadour
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Untamed
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Vindicator
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Virtuoso
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Warrior
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Weaver
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId.Willbender

/**
 * https://wiki.guildwars2.com/wiki/Guild_Wars_2_Wiki:Profession_icons
 *
 * TODO DON'T hotlink wiki images. Not cool. And the CORS settings trigger :)
 */
class Gw2WikiProfessionIcons private constructor(
    val smallColorized: String,
    val official: String,
    val large: String,
    val deco: String,
) {

    companion object {
        val professionIcons: Map<ProfessionEliteId, Gw2WikiProfessionIcons> = mapOf(
            Elementalist to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/4/4e/Elementalist_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/b4/Elementalist_icon_white.png",
                large = "https://wiki.guildwars2.com/images/2/2f/Elementalist_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/c/c0/Elementalist_icon_%28highres%29.png",
            ),
            Mesmer to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/7/79/Mesmer_icon_small.png",
                official = "https://wiki.guildwars2.com/images/7/74/Mesmer_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6c/Mesmer_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/0/0a/Mesmer_icon_%28highres%29.png",
            ),
            Necromancer to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/1/10/Necromancer_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/ab/Necromancer_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/a9/Necromancer_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/2/27/Necromancer_icon_%28highres%29.png",
            ),
            Engineer to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/0/07/Engineer_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/bd/Engineer_icon_white.png",
                large = "https://wiki.guildwars2.com/images/e/e3/Engineer_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/5/53/Engineer_icon_%28highres%29.png",
            ),
            Ranger to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/1/1e/Ranger_icon_small.png",
                official = "https://wiki.guildwars2.com/images/0/06/Ranger_icon_white.png",
                large = "https://wiki.guildwars2.com/images/4/47/Ranger_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/c/c7/Ranger_icon_%28highres%29.png",
            ),
            Thief to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/a/a0/Thief_icon_small.png",
                official = "https://wiki.guildwars2.com/images/6/62/Thief_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6d/Thief_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/3/31/Thief_icon_%28highres%29.png",
            ),
            Guardian to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/c/c7/Guardian_icon_small.png",
                official = "https://wiki.guildwars2.com/images/1/14/Guardian_icon_white.png",
                large = "https://wiki.guildwars2.com/images/d/de/Guardian_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/3/30/Guardian_icon_%28highres%29.png",
            ),
            Revenant to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/4/4c/Revenant_icon_small.png",
                official = "https://wiki.guildwars2.com/images/0/08/Revenant_icon_white.png",
                large = "https://wiki.guildwars2.com/images/f/f0/Revenant_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/2/2d/Revenant_icon_%28highres%29.png",
            ),
            Warrior to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/4/45/Warrior_icon_small.png",
                official = "https://wiki.guildwars2.com/images/7/7c/Warrior_icon_white.png",
                large = "https://wiki.guildwars2.com/images/0/02/Warrior_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/7/76/Warrior_icon_%28highres%29.png",
            ),
            Tempest to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/5/58/Tempest_icon_small.png",
                official = "https://wiki.guildwars2.com/images/7/70/Tempest_icon_white.png",
                large = "https://wiki.guildwars2.com/images/0/0b/Tempest_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/4/4d/Tempest_icon_%28highres%29.png",
            ),
            Weaver to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/c/c3/Weaver_icon_small.png",
                official = "https://wiki.guildwars2.com/images/c/cb/Weaver_icon_white.png",
                large = "https://wiki.guildwars2.com/images/7/7f/Weaver_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/6/66/Weaver_icon_%28extract%29.png",
            ),
            Catalyst to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/c/c5/Catalyst_icon_small.png",
                official = "https://wiki.guildwars2.com/images/9/93/Catalyst_icon_white.png",
                large = "https://wiki.guildwars2.com/images/3/36/Catalyst_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/b/ba/Catalyst_icon_%28highres%29.png",
            ),
            Evoker to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/e/e3/Evoker_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/a4/Evoker_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/a1/Evoker_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/1/10/Evoker_icon_%28highres%29.png",
            ),
            Chronomancer to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/e/e0/Chronomancer_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/a9/Chronomancer_icon_white.png",
                large = "https://wiki.guildwars2.com/images/2/2d/Chronomancer_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/7/79/Chronomancer_icon_%28highres%29.png",
            ),
            Mirage to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/c/c8/Mirage_icon_small.png",
                official = "https://wiki.guildwars2.com/images/8/80/Mirage_icon_white.png",
                large = "https://wiki.guildwars2.com/images/9/98/Mirage_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/9/90/Mirage_icon_%28extract%29.png",
            ),
            Virtuoso to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/7/77/Virtuoso_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/bc/Virtuoso_icon_white.png",
                large = "https://wiki.guildwars2.com/images/0/04/Virtuoso_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/f/ff/Virtuoso_icon_%28highres%29.png",
            ),
            Troubadour to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/f/f4/Troubadour_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/b2/Troubadour_icon_white.png",
                large = "https://wiki.guildwars2.com/images/5/5e/Troubadour_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/9/99/Troubadour_icon_%28highres%29.png",
            ),
            Reaper to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/9/93/Reaper_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/be/Reaper_icon_white.png",
                large = "https://wiki.guildwars2.com/images/3/39/Reaper_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/0/0c/Reaper_icon_%28highres%29.png",
            ),
            Scourge to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/e/e8/Scourge_icon_small.png",
                official = "https://wiki.guildwars2.com/images/8/80/Scourge_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/aa/Scourge_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/f/f4/Scourge_icon_%28extract%29.png",
            ),
            Harbinger to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/1/1d/Harbinger_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/a7/Harbinger_icon_white.png",
                large = "https://wiki.guildwars2.com/images/9/9e/Harbinger_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/c/c4/Harbinger_icon_%28highres%29.png",
            ),
            Ritualist to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/f/f9/Ritualist_icon_small.png",
                official = "https://wiki.guildwars2.com/images/0/03/Ritualist_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/a1/Ritualist_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/0/07/Ritualist_icon_%28highres%29.png",
            ),
            Scrapper to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/7/7d/Scrapper_icon_small.png",
                official = "https://wiki.guildwars2.com/images/6/68/Scrapper_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/a1/Scrapper_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/f/fd/Scrapper_icon_%28highres%29.png",
            ),
            Holosmith to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/a/aa/Holosmith_icon_small.png",
                official = "https://wiki.guildwars2.com/images/3/3a/Holosmith_icon_white.png",
                large = "https://wiki.guildwars2.com/images/f/f3/Holosmith_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/a/a8/Holosmith_icon_%28extract%29.png",
            ),
            Mechanist to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/6/6d/Mechanist_icon_small.png",
                official = "https://wiki.guildwars2.com/images/6/6c/Mechanist_icon_white.png",
                large = "https://wiki.guildwars2.com/images/c/cb/Mechanist_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/7/70/Mechanist_icon_%28highres%29.png",
            ),
            Amalgam to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/d/d0/Amalgam_icon_small.png",
                official = "https://wiki.guildwars2.com/images/9/9e/Amalgam_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/a3/Amalgam_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/a/a1/Amalgam_icon_%28highres%29.png",
            ),
            Druid to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/9/9b/Druid_icon_small.png",
                official = "https://wiki.guildwars2.com/images/0/01/Druid_icon_white.png",
                large = "https://wiki.guildwars2.com/images/2/2e/Druid_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/9/96/Druid_icon_%28highres%29.png",
            ),
            Soulbeast to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/6/6a/Soulbeast_icon_small.png",
                official = "https://wiki.guildwars2.com/images/1/14/Soulbeast_icon_white.png",
                large = "https://wiki.guildwars2.com/images/3/3a/Soulbeast_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/7/71/Soulbeast_icon_%28extract%29.png",
            ),
            Untamed to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/2/2d/Untamed_icon_small.png",
                official = "https://wiki.guildwars2.com/images/1/13/Untamed_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6d/Untamed_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/8/8e/Untamed_icon_%28highres%29.png",
            ),
            Galeshot to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/a/a7/Galeshot_icon_small.png",
                official = "https://wiki.guildwars2.com/images/b/be/Galeshot_icon_white.png",
                large = "https://wiki.guildwars2.com/images/e/e9/Galeshot_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/e/ef/Galeshot_icon_%28highres%29.png",
            ),
            Daredevil to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/f/f3/Daredevil_icon_small.png",
                official = "https://wiki.guildwars2.com/images/6/64/Daredevil_icon_white.png",
                large = "https://wiki.guildwars2.com/images/d/df/Daredevil_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/1/13/Daredevil_icon_%28highres%29.png",
            ),
            Deadeye to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/7/70/Deadeye_icon_small.png",
                official = "https://wiki.guildwars2.com/images/3/3d/Deadeye_icon_white.png",
                large = "https://wiki.guildwars2.com/images/d/d6/Deadeye_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/d/d1/Deadeye_icon_%28extract%29.png",
            ),
            Specter to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/6/61/Specter_icon_small.png",
                official = "https://wiki.guildwars2.com/images/3/38/Specter_icon_white.png",
                large = "https://wiki.guildwars2.com/images/9/9c/Specter_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/0/03/Specter_icon_%28highres%29.png",
            ),
            Antiquary to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/d/d1/Antiquary_icon_small.png",
                official = "https://wiki.guildwars2.com/images/7/7d/Antiquary_icon_white.png",
                large = "https://wiki.guildwars2.com/images/d/db/Antiquary_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/7/7f/Antiquary_icon_%28unofficial_color%29.png",
            ),
            Dragonhunter to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/5/5d/Dragonhunter_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/a4/Dragonhunter_icon_white.png",
                large = "https://wiki.guildwars2.com/images/9/91/Dragonhunter_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/3/33/Dragonhunter_icon_%28highres%29.png",
            ),
            Firebrand to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/0/0e/Firebrand_icon_small.png",
                official = "https://wiki.guildwars2.com/images/d/d5/Firebrand_icon_white.png",
                large = "https://wiki.guildwars2.com/images/9/94/Firebrand_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/f/f1/Firebrand_icon_%28extract%29.png",
            ),
            Willbender to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/6/64/Willbender_icon_small.png",
                official = "https://wiki.guildwars2.com/images/d/d8/Willbender_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6e/Luminary_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/5/5f/Willbender_icon_%28highres%29.png",
            ),
            Luminary to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/7/7b/Luminary_icon_small.png",
                official = "https://wiki.guildwars2.com/images/0/02/Luminary_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6e/Luminary_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/2/27/Luminary_icon_%28unofficial_color%29.png",
            ),
            Herald to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/3/39/Herald_icon_small.png",
                official = "https://wiki.guildwars2.com/images/9/9f/Herald_icon_white.png",
                large = "https://wiki.guildwars2.com/images/8/89/Herald_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/6/63/Herald_icon_%28highres%29.png",
            ),
            Renegade to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/b/be/Renegade_icon_small.png",
                official = "https://wiki.guildwars2.com/images/d/da/Renegade_icon_white.png",
                large = "https://wiki.guildwars2.com/images/a/ad/Renegade_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/6/61/Renegade_icon_%28extract%29.png",
            ),
            Vindicator to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/6/6d/Vindicator_icon_small.png",
                official = "https://wiki.guildwars2.com/images/2/20/Vindicator_icon_white.png",
                large = "https://wiki.guildwars2.com/images/d/d6/Vindicator_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/b/b7/Vindicator_icon_%28highres%29.png",
            ),
            Conduit to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/a/a1/Conduit_icon_small.png",
                official = "https://wiki.guildwars2.com/images/5/56/Conduit_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/60/Conduit_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/3/38/Conduit_icon_%28highres%29.png",
            ),
            Berserker to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/a/a8/Berserker_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/a3/Berserker_icon_white.png",
                large = "https://wiki.guildwars2.com/images/5/5a/Berserker_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/6/60/Berserker_icon_%28highres%29.png",
            ),
            Spellbreaker to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/0/08/Spellbreaker_icon_small.png",
                official = "https://wiki.guildwars2.com/images/1/17/Spellbreaker_icon_white.png",
                large = "https://wiki.guildwars2.com/images/f/fc/Spellbreaker_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/1/14/Spellbreaker_icon_%28extract%29.png",
            ),
            Bladesworn to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/c/cf/Bladesworn_icon_small.png",
                official = "https://wiki.guildwars2.com/images/a/af/Bladesworn_icon_white.png",
                large = "https://wiki.guildwars2.com/images/6/6e/Bladesworn_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/a/ab/Bladesworn_icon_%28highres%29.png",
            ),
            Paragon to Gw2WikiProfessionIcons(
                smallColorized = "https://wiki.guildwars2.com/images/1/13/Paragon_icon_small.png",
                official = "https://wiki.guildwars2.com/images/1/17/Paragon_icon_white.png",
                large = "https://wiki.guildwars2.com/images/3/3c/Paragon_%28overhead_icon%29.png",
                deco = "https://wiki.guildwars2.com/images/f/f1/Paragon_icon_%28highres%29.png",
            ),
        )
    }

}
