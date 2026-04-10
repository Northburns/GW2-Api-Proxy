package northburns.gw2.client.myclient.data.calc

import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2SpecializationId
import com.gw2tb.gw2api.types.v2.GW2v2Character

enum class ProfessionId(
    val professionId: GW2ProfessionId,
) {
    Guardian(GW2ProfessionId("Guardian")),
    Warrior(GW2ProfessionId("Warrior")),
    Engineer(GW2ProfessionId("Engineer")),
    Ranger(GW2ProfessionId("Ranger")),
    Thief(GW2ProfessionId("Thief")),
    Elementalist(GW2ProfessionId("Elementalist")),
    Mesmer(GW2ProfessionId("Mesmer")),
    Necromancer(GW2ProfessionId("Necromancer")),
    Revenant(GW2ProfessionId("Revenant")),

    ;


    companion object {
        private val idx: Map<GW2ProfessionId, ProfessionId> =
            entries.associateBy { it.professionId }

    }
}
