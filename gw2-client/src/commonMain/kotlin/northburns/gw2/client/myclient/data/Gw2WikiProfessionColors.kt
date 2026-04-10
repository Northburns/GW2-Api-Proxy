package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Specialization
import northburns.gw2.client.myclient.data.Gw2Ids.ElementalistIds
import northburns.gw2.client.myclient.data.Gw2Ids.EngineerIds
import northburns.gw2.client.myclient.data.Gw2Ids.GuardianIds
import northburns.gw2.client.myclient.data.Gw2Ids.MesmerIds
import northburns.gw2.client.myclient.data.Gw2Ids.NecromancerIds
import northburns.gw2.client.myclient.data.Gw2Ids.RangerIds
import northburns.gw2.client.myclient.data.Gw2Ids.RevenantIds
import northburns.gw2.client.myclient.data.Gw2Ids.ThiefIds
import northburns.gw2.client.myclient.data.Gw2Ids.WarriorIds

/**
 * https://wiki.guildwars2.com/wiki/Guild_Wars_2_Wiki:Color_schemes
 */
enum class Gw2WikiProfessionColors(
    val professionId: GW2ProfessionId,
    val lighter: String,
    val light: String,
    val medium: String,
    val dark: String,
) {
    Guardian(
        GuardianIds.Guardian,
        "#CFEEFD", "#BCE8FD", "#72C1D9", "#186885"
    ),
    Revenant(
        RevenantIds.Revenant,
        "#EBC9C2", "#E4AEA3", "#D16E5A", "#A66356"
    ),
    Warrior(
        WarriorIds.Warrior,
        "#FFF5BB", "#FFF2A4", "#FFD166", "#CAAA2A"
    ),
    Engineer(
        EngineerIds.Engineer,
        "#E8C89F", "#E8BC84", "#D09C59", "#87581D"
    ),
    Ranger(
        RangerIds.Ranger,
        "#E2F6D1", "#D2F6BC", "#8CDC82", "#67A833"
    ),
    Thief(
        ThiefIds.Thief,
        "#E6D5D7", "#DEC6C9", "#C08F95", "#974550"
    ),
    Elementalist(
        ElementalistIds.Elementalist,
        "#F6D2D1,", "#F6BEBC", "#F68A87", "#DC423E"
    ),
    Mesmer(
        MesmerIds.Mesmer,
        "#D7B2EA,", "#D09EEA", "#B679D5", "#69278A"
    ),
    Necromancer(
        NecromancerIds.Necromancer,
        "#D5EDE1,", "#BFE6D0", "#52A76F", "#2C9D5D"
    ),

    ;

    companion object {

        private val byProfessionId = entries.associateBy { it.professionId }

        fun GW2ProfessionId.getColors() = byProfessionId.getValue(this)
        fun GW2v2Profession.getColors() = byProfessionId.getValue(this.id)

    }
}