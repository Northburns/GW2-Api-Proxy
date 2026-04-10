package northburns.gw2.client.myclient.data.extra

enum class GW2WeaponId(
    val weaponName: String,
) {
    AXE("Axe"),
    DAGGER("Dagger"),
    FOCUS("Focus"),
    GREATSWORD("Greatsword"),
    SPEAR("Spear"),
    PISTOL("Pistol"),
    RIFLE("Rifle"),
    SCEPTER("Scepter"),
    SHIELD("Shield"),
    STAFF("Staff"),
    SWORD("Sword"),
    TORCH("Torch"),
    TRIDENT("Trident"),
    HAMMER("Hammer"),
    ;

    companion object {
        fun fromString(s: String): GW2WeaponId = entries.firstOrNull() { it.weaponName == s }
            ?: throw IllegalArgumentException("Unexpected GW2WeaponId: $s")
    }
}
