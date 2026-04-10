package northburns.gw2.client.myclient.data.calc

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2SpecializationId
import com.gw2tb.gw2api.types.v2.GW2v2Character
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Specialization

enum class ProfessionEliteId(
    val professionId: GW2ProfessionId,
    val eliteId: GW2SpecializationId? = null,
) {
    Guardian(GW2ProfessionId("Guardian"), null),
    Warrior(GW2ProfessionId("Warrior"), null),
    Engineer(GW2ProfessionId("Engineer"), null),
    Ranger(GW2ProfessionId("Ranger"), null),
    Thief(GW2ProfessionId("Thief"), null),
    Elementalist(GW2ProfessionId("Elementalist"), null),
    Mesmer(GW2ProfessionId("Mesmer"), null),
    Necromancer(GW2ProfessionId("Necromancer"), null),
    Revenant(GW2ProfessionId("Revenant"), null),

    Luminary(GW2ProfessionId("Guardian"), GW2SpecializationId(81)),
    Dragonhunter(GW2ProfessionId("Guardian"), GW2SpecializationId(27)),
    Firebrand(GW2ProfessionId("Guardian"), GW2SpecializationId(62)),
    Willbender(GW2ProfessionId("Guardian"), GW2SpecializationId(65)),

    Berserker(GW2ProfessionId("Warrior"), GW2SpecializationId(18)),
    Bladesworn(GW2ProfessionId("Warrior"), GW2SpecializationId(68)),
    Spellbreaker(GW2ProfessionId("Warrior"), GW2SpecializationId(61)),
    Paragon(GW2ProfessionId("Warrior"), GW2SpecializationId(74)),

    Amalgam(GW2ProfessionId("Engineer"), GW2SpecializationId(75)),
    Mechanist(GW2ProfessionId("Engineer"), GW2SpecializationId(70)),
    Holosmith(GW2ProfessionId("Engineer"), GW2SpecializationId(57)),
    Scrapper(GW2ProfessionId("Engineer"), GW2SpecializationId(43)),

    Galeshot(GW2ProfessionId("Ranger"), GW2SpecializationId(78)),
    Untamed(GW2ProfessionId("Ranger"), GW2SpecializationId(72)),
    Soulbeast(GW2ProfessionId("Ranger"), GW2SpecializationId(55)),
    Druid(GW2ProfessionId("Ranger"), GW2SpecializationId(5)),

    Antiquary(GW2ProfessionId("Thief"), GW2SpecializationId(77)),
    Specter(GW2ProfessionId("Thief"), GW2SpecializationId(71)),
    Deadeye(GW2ProfessionId("Thief"), GW2SpecializationId(58)),
    Daredevil(GW2ProfessionId("Thief"), GW2SpecializationId(7)),

    Evoker(GW2ProfessionId("Elementalist"), GW2SpecializationId(80)),
    Catalyst(GW2ProfessionId("Elementalist"), GW2SpecializationId(67)),
    Tempest(GW2ProfessionId("Elementalist"), GW2SpecializationId(48)),
    Weaver(GW2ProfessionId("Elementalist"), GW2SpecializationId(56)),

    Troubadour(GW2ProfessionId("Mesmer"), GW2SpecializationId(73)),
    Mirage(GW2ProfessionId("Mesmer"), GW2SpecializationId(59)),
    Chronomancer(GW2ProfessionId("Mesmer"), GW2SpecializationId(40)),
    Virtuoso(GW2ProfessionId("Mesmer"), GW2SpecializationId(66)),

    Ritualist(GW2ProfessionId("Necromancer"), GW2SpecializationId(76)),
    Harbinger(GW2ProfessionId("Necromancer"), GW2SpecializationId(64)),
    Scourge(GW2ProfessionId("Necromancer"), GW2SpecializationId(60)),
    Reaper(GW2ProfessionId("Necromancer"), GW2SpecializationId(34)),

    Conduit(GW2ProfessionId("Revenant"), GW2SpecializationId(79)),
    Vindicator(GW2ProfessionId("Revenant"), GW2SpecializationId(69)),
    Herald(GW2ProfessionId("Revenant"), GW2SpecializationId(52)),
    Renegade(GW2ProfessionId("Revenant"), GW2SpecializationId(63)),

    ;

    /**
     * Returns null if either resolve function returns null.
     */
    fun resolveMaybe(
        p: (GW2ProfessionId) -> GW2v2Profession?,
        e: (GW2SpecializationId) -> GW2v2Specialization?
    ): Either<GW2v2Profession, Pair<GW2v2Profession, GW2v2Specialization>>? {
        val profession = p(professionId) ?: return null
        if (eliteId == null) return profession.left()
        val elite = e(eliteId) ?: return null
        return (profession to elite).right()
    }

    companion object {
        private val idx: Map<GW2ProfessionId, Map<GW2SpecializationId?, ProfessionEliteId>> =
            entries.groupBy { it.professionId }.mapValues { (_, v) -> v.associateBy({ it.eliteId }, { it }) }

        private val eliteSpecializationIds = entries.mapNotNull { it.eliteId }.toSet()

        fun GW2v2Character.eliteSpecialization(): GW2SpecializationId? {
            val chosenEliteSpecs = getActiveBuildTab()?.build
                ?.specializations?.mapNotNull { it.id }?.intersect(eliteSpecializationIds)
            if (chosenEliteSpecs.isNullOrEmpty()) return null
            require(chosenEliteSpecs.size == 1) { "Character shouldn't be able to have 2+ elite specs!" }
            return chosenEliteSpecs.single()
        }

        fun GW2v2Character.getProfessionElite(): ProfessionEliteId = forCharacter(this)

        fun forCharacter(character: GW2v2Character): ProfessionEliteId = idx
            .getValue(character.profession)
            .getValue(character.eliteSpecialization())


    }
}
