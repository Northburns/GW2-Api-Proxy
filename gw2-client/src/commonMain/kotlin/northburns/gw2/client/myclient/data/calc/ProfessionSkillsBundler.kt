package northburns.gw2.client.myclient.data.calc

import com.gw2tb.gw2api.types.v2.GW2v2Profession
import northburns.gw2.client.myclient.data.extra.GW2ProfessionFlag
import northburns.gw2.client.myclient.data.extra.GW2ProfessionSkillSlotId
import northburns.gw2.client.myclient.data.extra.GW2WeaponFlag
import northburns.gw2.client.myclient.data.extra.GW2WeaponId

class ProfessionSkillsBundler(
    val profession: GW2v2Profession,
) {
    val flags = profession.flags.map(GW2ProfessionFlag::fromString).toSet()

    val healSkills: List<GW2v2Profession.Skill>
    val utiltySkills: List<GW2v2Profession.Skill>
    val eliteSkills: List<GW2v2Profession.Skill>
    val professionSkills: Map<GW2ProfessionSkillSlotId, GW2v2Profession.Skill>
    val weaponSkills: Map<GW2WeaponId, WeaponSkillHolder>

    data class WeaponSkillHolder(
        val flags: Set<GW2WeaponFlag>,
        val skills: List<GW2v2Profession.Weapon.Skill>,
    )

    init {
        @Suppress("LocalVariableName") val _heal = mutableListOf<GW2v2Profession.Skill>()
        @Suppress("LocalVariableName") val _utility = mutableListOf<GW2v2Profession.Skill>()
        @Suppress("LocalVariableName") val _elite = mutableListOf<GW2v2Profession.Skill>()
        @Suppress("LocalVariableName") val _profession = mutableMapOf<GW2ProfessionSkillSlotId, GW2v2Profession.Skill>()
        @Suppress("LocalVariableName") val _weaponSkills = mutableMapOf<GW2WeaponId, WeaponSkillHolder>()

        profession.skills.forEach { skill ->
            when (skill.type) {
                "Heal" -> _heal.add(skill)
                "Utility" -> _utility.add(skill)
                "Elite" -> _elite.add(skill)
                "Profession" -> _profession[GW2ProfessionSkillSlotId.fromString(skill.slot)] = skill
                else -> throw IllegalStateException("Unexpected skill type ${skill.type}")
            }
            require(skill.attunement == null) { "Unexpected to be null: skill.attunement" }
            require(skill.source == null) { "Unexpected to be null: skill.source" }
        }

        profession.weapons.forEach { (weaponName, weapon) ->
            val weaponId = GW2WeaponId.fromString(weaponName)
            val flags = weapon.flags.map(GW2WeaponFlag::fromString).toSet()
            val skills = weapon.skills.sortedBy { it.slot }

            _weaponSkills[weaponId] = WeaponSkillHolder(flags, skills)
        }

        healSkills = _heal
        utiltySkills = _utility
        eliteSkills = _elite
        professionSkills = _profession
        weaponSkills = _weaponSkills
    }


}