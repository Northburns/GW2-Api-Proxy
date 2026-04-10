package northburns.gw2.client.myclient.gw2e

import com.gw2tb.gw2api.types.GW2BackstoryAnswerId
import com.gw2tb.gw2api.types.GW2DyeId
import com.gw2tb.gw2api.types.GW2EmoteId
import com.gw2tb.gw2api.types.GW2FinisherId
import com.gw2tb.gw2api.types.GW2GliderId
import com.gw2tb.gw2api.types.GW2GuildId
import com.gw2tb.gw2api.types.GW2GuildUpgradeId
import com.gw2tb.gw2api.types.GW2HomeInstanceCatId
import com.gw2tb.gw2api.types.GW2HomeInstanceNodeId
import com.gw2tb.gw2api.types.GW2HomesteadGlyphId
import com.gw2tb.gw2api.types.GW2JadeBotId
import com.gw2tb.gw2api.types.GW2MailCarrierId
import com.gw2tb.gw2api.types.GW2MiniId
import com.gw2tb.gw2api.types.GW2MountSkinId
import com.gw2tb.gw2api.types.GW2MountTypeId
import com.gw2tb.gw2api.types.GW2NoveltyId
import com.gw2tb.gw2api.types.GW2OutfitId
import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2PvpHeroId
import com.gw2tb.gw2api.types.GW2RaceId
import com.gw2tb.gw2api.types.GW2RecipeId
import com.gw2tb.gw2api.types.GW2SkiffId
import com.gw2tb.gw2api.types.GW2SkinId
import com.gw2tb.gw2api.types.GW2TitleId
import com.gw2tb.gw2api.types.v2.GW2v2Account
import com.gw2tb.gw2api.types.v2.GW2v2AccountAchievement
import com.gw2tb.gw2api.types.v2.GW2v2AccountBankSlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountFinisher
import com.gw2tb.gw2api.types.v2.GW2v2AccountHomesteadDecoration
import com.gw2tb.gw2api.types.v2.GW2v2AccountInventorySlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountLegendaryArmoryUnlock
import com.gw2tb.gw2api.types.v2.GW2v2AccountMastery
import com.gw2tb.gw2api.types.v2.GW2v2AccountMasteryPoint
import com.gw2tb.gw2api.types.v2.GW2v2AccountMaterial
import com.gw2tb.gw2api.types.v2.GW2v2AccountWalletCurrency
import com.gw2tb.gw2api.types.v2.GW2v2Character
import com.gw2tb.gw2api.types.v2.GW2v2CharactersBuildTab
import com.gw2tb.gw2api.types.v2.GW2v2CommerceDelivery
import com.gw2tb.gw2api.types.v2.GW2v2CommerceTransaction
import com.gw2tb.gw2api.types.v2.GW2v2Guild
import com.gw2tb.gw2api.types.v2.GW2v2GuildMember
import com.gw2tb.gw2api.types.v2.GW2v2GuildRank
import com.gw2tb.gw2api.types.v2.GW2v2GuildStashSection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.time.Instant

@Serializable
data class Gw2eAccountSnapshot(
    val account: Account,
    val economy: Economy,
    val creationDate: Instant,
) {

    @Serializable
    data class Account(
        val account: GW2v2Account,
        val achievements: List<GW2v2AccountAchievement>,
        val bank: List<GW2v2AccountBankSlot?>,
        val characters: List<GW2v2Character>,
        val commerce: Commerce,
        /**
         * TODO type
         */
        val dungeons: List<JsonElement>,
        val dyes: List<GW2DyeId>,
        val emotes: List<GW2EmoteId>,
        val finishers: List<GW2v2AccountFinisher>,
        val gliders: List<GW2GliderId>,
        val guilds: List<Guild>,
        val home: Home,
        val homestead: Homestead,
        val jadebots: List<GW2JadeBotId>,
        val legendaryarmory: List<GW2v2AccountLegendaryArmoryUnlock>,
        val luck: Int,
        val mailcarriers: List<GW2MailCarrierId>,
        val masteries: List<GW2v2AccountMastery>,
        val mastery: Mastery,
        val materials: List<GW2v2AccountMaterial>,
        val minis: List<GW2MiniId>,
        val mounts: Mounts,
        val novelties: List<GW2NoveltyId>,
        val outfits: List<GW2OutfitId>,
        val pvp: JsonElement,
        val raids: List<JsonElement>,
        val recipes: List<GW2RecipeId>,
        val shared: List<GW2v2AccountInventorySlot?>,
        val skiffs: List<GW2SkiffId>,
        val skins: List<GW2SkinId>,
        val titles: List<GW2TitleId>,
        val wallet: List<GW2v2AccountWalletCurrency>,
    ) {

    }

    @Serializable
    data class Mounts(
        val skins: List<GW2MountSkinId>,
        val types: List<GW2MountTypeId>,
    )

    @Serializable
    data class Mastery(
        val points: GW2v2AccountMasteryPoint,
    )

    @Serializable
    data class Homestead(
        val decorations: List<GW2v2AccountHomesteadDecoration>,
        val glyphs: List<GW2HomesteadGlyphId>,
    )

    @Serializable
    data class Home(
        val cats: List<GW2HomeInstanceCatId>,
        val nodes: List<GW2HomeInstanceNodeId>,
    )

    @Serializable
    data class Guild(
        val data: GW2v2Guild,
        val members: List<GW2v2GuildMember>,
        val ranks: List<GW2v2GuildRank>,
        val stash: List<GW2v2GuildStashSection>,
        /**
         * TODO type
         */
        val teams: JsonElement?,
        /**
         * TODO type
         */
        val treasury: List<JsonElement>,
        val upgrades: List<GW2GuildUpgradeId>,
    )

    @Serializable
    data class Commerce(
        val buys: List<GW2v2CommerceTransaction>,
        val sells: List<GW2v2CommerceTransaction>,
        val delivery: GW2v2CommerceDelivery,
    )


    @Serializable
    data class Economy(
        /**
         * Has Gw2Efficiency item ids in there as well, which are Long
         */
        val items: Map<Long, HasValue>,
        val skins: Map<GW2SkinId, SkinUnlock>,
        val outfits: Map<GW2OutfitId, HasValue>,
        val dyes: Map<GW2DyeId, HasValue>,
        val minis: Map<GW2MiniId, HasValue>,
        val mounts: Map<GW2MountSkinId, HasValue>,
        val finishers: Map<GW2FinisherId, HasValue>,
        val emotes: Map<GW2EmoteId, HasValue>,
        val jadebots: Map<GW2JadeBotId, HasValue>,
        val heroes: Map<GW2PvpHeroId, HasValue>,
        val mailcarriers: Map<GW2MailCarrierId, HasValue>,
        val nodes: Map<GW2HomeInstanceNodeId, HasValue>,
        val gliders: Map<GW2GliderId, HasValue>,
        val recipes: Map<GW2RecipeId, HasValue>,
        val novelties: Map<GW2NoveltyId, HasValue>,
        val skiffs: Map<GW2SkiffId, HasValue>,
        val craftingProfessions: Map<String, Map<Int, Int>>,

        )

    @Serializable
    data class SkinUnlock(
        val unlocks: List<Long>,
        val value: Double,
    )

    @Serializable
    data class HasValue(
        val value: Double,
    )
}
