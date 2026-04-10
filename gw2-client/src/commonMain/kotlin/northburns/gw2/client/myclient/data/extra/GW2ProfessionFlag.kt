package northburns.gw2.client.myclient.data.extra


enum class GW2ProfessionFlag(
    val flag: String,
) {

    ;

    companion object {

        fun fromString(s: String): GW2ProfessionFlag = entries.firstOrNull() { it.flag == s }
            ?: throw IllegalArgumentException("Unexpected GW2ProfessionFlag: $s")
    }
}
