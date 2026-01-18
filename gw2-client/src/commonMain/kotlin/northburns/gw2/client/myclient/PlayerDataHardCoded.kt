package northburns.gw2.client.myclient

object PlayerDataHardCoded {

    val playerData = mapOf(
        PlayerId("aki") to PlayerData(
            PlayerId("aki"),
            "Aki",
            PlayerApiKey("5CA7C4ED-5981-8247-A3ED-6FE2C9680F79426E1575-53CE-4179-BEB5-F9698734B362")
        ),
        PlayerId("maria") to PlayerData(
            PlayerId("maria"),
            "Maria",
            PlayerApiKey("C9DBE2D5-0CEC-C64D-A41A-434FFF63F59F6C0389B4-5B4D-4ADF-A873-17D9F37D39E8")
        ),
    )

    val playerIds = listOf(
        PlayerId("aki"),
        PlayerId("maria"),
    )

    fun playerData(id: PlayerId): PlayerData {
        return playerData.getValue(id)
    }

}