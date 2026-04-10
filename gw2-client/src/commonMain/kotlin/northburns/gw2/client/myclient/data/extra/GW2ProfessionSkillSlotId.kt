package northburns.gw2.client.myclient.data.extra

enum class GW2ProfessionSkillSlotId(
    val slot: String,
) {
    PROFESSION_1("Profession_1"),
    PROFESSION_2("Profession_2"),
    PROFESSION_3("Profession_3"),
    PROFESSION_4("Profession_4"),
    PROFESSION_5("Profession_5"),

    ;

    companion object {
        fun fromString(s: String): GW2ProfessionSkillSlotId = entries.firstOrNull() { it.slot == s }
            ?: throw IllegalArgumentException("Unexpected GW2ProfessionSkillSlotId: $s")
    }
}
