package northburns.gw2.client.myclient

object PlayerDataHardCoded {

    val AKI = PlayerId("aki")
    val MARIA = PlayerId("maria")
    val playerData = mapOf(
        AKI to PlayerData(
            AKI,
            "Aki",
            PlayerApiKey(" ")
        ),
        MARIA to PlayerData(
            MARIA,
            "Maria",
            PlayerApiKey("")
        ),
    )

    val playerIds = listOf(
        AKI,
        MARIA,
    )

    fun playerData(id: PlayerId): PlayerData {
        return playerData.getValue(id)
    }

}