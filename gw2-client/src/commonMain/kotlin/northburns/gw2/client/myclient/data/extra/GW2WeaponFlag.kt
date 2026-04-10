package northburns.gw2.client.myclient.data.extra


enum class GW2WeaponFlag(
    val flag: String,
) {
    MAINHAND("Mainhand"),
    OFFHAND("Offhand"),
    TWOHAND("TwoHand"),
    AQUATIC("Aquatic"),
    ;

    companion object {

        fun fromString(s: String): GW2WeaponFlag = entries.firstOrNull() { it.flag == s }
            ?: throw IllegalArgumentException("Unexpected GW2WeaponFlag: $s")
    }
}
