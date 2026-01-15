package northburns.gw2.client.myclient

import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementGroupId
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.GW2BackstoryAnswerId
import com.gw2tb.gw2api.types.GW2BackstoryQuestionId
import com.gw2tb.gw2api.types.GW2ColorId
import com.gw2tb.gw2api.types.GW2ContinentId
import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2DungeonId
import com.gw2tb.gw2api.types.GW2DyeId
import com.gw2tb.gw2api.types.GW2EmoteId
import com.gw2tb.gw2api.types.GW2FinisherId
import com.gw2tb.gw2api.types.GW2FloorId
import com.gw2tb.gw2api.types.GW2GliderId
import com.gw2tb.gw2api.types.GW2HomeInstanceCatId
import com.gw2tb.gw2api.types.GW2HomeInstanceNodeId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.GW2JadeBotId
import com.gw2tb.gw2api.types.GW2LegendId
import com.gw2tb.gw2api.types.GW2MailCarrierId
import com.gw2tb.gw2api.types.GW2MapId
import com.gw2tb.gw2api.types.GW2MasteryId
import com.gw2tb.gw2api.types.GW2MaterialId
import com.gw2tb.gw2api.types.GW2MiniId
import com.gw2tb.gw2api.types.GW2MountSkinId
import com.gw2tb.gw2api.types.GW2MountTypeId
import com.gw2tb.gw2api.types.GW2NoveltyId
import com.gw2tb.gw2api.types.GW2OutfitId
import com.gw2tb.gw2api.types.GW2PetId
import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2PvpHeroId
import com.gw2tb.gw2api.types.GW2QuagganId
import com.gw2tb.gw2api.types.GW2RaceId
import com.gw2tb.gw2api.types.GW2RaidId
import com.gw2tb.gw2api.types.GW2RecipeId
import com.gw2tb.gw2api.types.GW2SkiffId
import com.gw2tb.gw2api.types.GW2SkillId
import com.gw2tb.gw2api.types.GW2SkinId
import com.gw2tb.gw2api.types.GW2SpecializationId
import com.gw2tb.gw2api.types.GW2StoryId
import com.gw2tb.gw2api.types.GW2StorySeasonId
import com.gw2tb.gw2api.types.GW2TitleId
import com.gw2tb.gw2api.types.GW2TraitId
import com.gw2tb.gw2api.types.GW2WizardsVaultListingId
import com.gw2tb.gw2api.types.GW2WizardsVaultObjectiveId
import com.gw2tb.gw2api.types.GW2WorldBossId
import com.gw2tb.gw2api.types.GW2WorldId
import com.gw2tb.gw2api.types.v2.GW2v2Account
import com.gw2tb.gw2api.types.v2.GW2v2AccountAchievement
import com.gw2tb.gw2api.types.v2.GW2v2AccountBankSlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountFinisher
import com.gw2tb.gw2api.types.v2.GW2v2AccountInventorySlot
import com.gw2tb.gw2api.types.v2.GW2v2AccountLegendaryArmoryUnlock
import com.gw2tb.gw2api.types.v2.GW2v2AccountMastery
import com.gw2tb.gw2api.types.v2.GW2v2AccountMasteryPoint
import com.gw2tb.gw2api.types.v2.GW2v2AccountMaterial
import com.gw2tb.gw2api.types.v2.GW2v2AccountWalletCurrency
import com.gw2tb.gw2api.types.v2.GW2v2AccountWizardsVaultDaily
import com.gw2tb.gw2api.types.v2.GW2v2AccountWizardsVaultListing
import com.gw2tb.gw2api.types.v2.GW2v2AccountWizardsVaultSpecial
import com.gw2tb.gw2api.types.v2.GW2v2AccountWizardsVaultWeekly
import com.gw2tb.gw2api.types.v2.GW2v2Achievement
import com.gw2tb.gw2api.types.v2.GW2v2AchievementCategory
import com.gw2tb.gw2api.types.v2.GW2v2AchievementGroups
import com.gw2tb.gw2api.types.v2.GW2v2BackstoryAnswer
import com.gw2tb.gw2api.types.v2.GW2v2BackstoryQuestion
import com.gw2tb.gw2api.types.v2.GW2v2Build
import com.gw2tb.gw2api.types.v2.GW2v2Character
import com.gw2tb.gw2api.types.v2.GW2v2CharactersBackstory
import com.gw2tb.gw2api.types.v2.GW2v2CharactersBuildTab
import com.gw2tb.gw2api.types.v2.GW2v2CharactersCore
import com.gw2tb.gw2api.types.v2.GW2v2CharactersCrafting
import com.gw2tb.gw2api.types.v2.GW2v2CharactersEquipment
import com.gw2tb.gw2api.types.v2.GW2v2CharactersEquipmentTab
import com.gw2tb.gw2api.types.v2.GW2v2CharactersRecipes
import com.gw2tb.gw2api.types.v2.GW2v2CharactersSAB
import com.gw2tb.gw2api.types.v2.GW2v2CharactersTraining
import com.gw2tb.gw2api.types.v2.GW2v2Color
import com.gw2tb.gw2api.types.v2.GW2v2CommerceDelivery
import com.gw2tb.gw2api.types.v2.GW2v2CommerceListing
import com.gw2tb.gw2api.types.v2.GW2v2CommercePrices
import com.gw2tb.gw2api.types.v2.GW2v2CommerceTransaction
import com.gw2tb.gw2api.types.v2.GW2v2Continent
import com.gw2tb.gw2api.types.v2.GW2v2ContinentFloor
import com.gw2tb.gw2api.types.v2.GW2v2ContinentFloor.Region
import com.gw2tb.gw2api.types.v2.GW2v2Currency
import com.gw2tb.gw2api.types.v2.GW2v2DailyCrafting
import com.gw2tb.gw2api.types.v2.GW2v2Dungeon
import com.gw2tb.gw2api.types.v2.GW2v2Emote
import com.gw2tb.gw2api.types.v2.GW2v2Finisher
import com.gw2tb.gw2api.types.v2.GW2v2Glider
import com.gw2tb.gw2api.types.v2.GW2v2HomeInstanceCat
import com.gw2tb.gw2api.types.v2.GW2v2HomeInstanceNode
import com.gw2tb.gw2api.types.v2.GW2v2Item
import com.gw2tb.gw2api.types.v2.GW2v2JadeBot
import com.gw2tb.gw2api.types.v2.GW2v2Legend
import com.gw2tb.gw2api.types.v2.GW2v2LegendaryArmorySlot
import com.gw2tb.gw2api.types.v2.GW2v2Luck
import com.gw2tb.gw2api.types.v2.GW2v2Mailcarrier
import com.gw2tb.gw2api.types.v2.GW2v2Map
import com.gw2tb.gw2api.types.v2.GW2v2MapChest
import com.gw2tb.gw2api.types.v2.GW2v2Mastery
import com.gw2tb.gw2api.types.v2.GW2v2MaterialCategory
import com.gw2tb.gw2api.types.v2.GW2v2Mini
import com.gw2tb.gw2api.types.v2.GW2v2MountSkin
import com.gw2tb.gw2api.types.v2.GW2v2MountType
import com.gw2tb.gw2api.types.v2.GW2v2Novelty
import com.gw2tb.gw2api.types.v2.GW2v2Outfit
import com.gw2tb.gw2api.types.v2.GW2v2Pet
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Quaggan
import com.gw2tb.gw2api.types.v2.GW2v2Quest
import com.gw2tb.gw2api.types.v2.GW2v2Race
import com.gw2tb.gw2api.types.v2.GW2v2Raid
import com.gw2tb.gw2api.types.v2.GW2v2Recipe
import com.gw2tb.gw2api.types.v2.GW2v2Skiff
import com.gw2tb.gw2api.types.v2.GW2v2Skill
import com.gw2tb.gw2api.types.v2.GW2v2Skin
import com.gw2tb.gw2api.types.v2.GW2v2Specialization
import com.gw2tb.gw2api.types.v2.GW2v2Story
import com.gw2tb.gw2api.types.v2.GW2v2StorySeason
import com.gw2tb.gw2api.types.v2.GW2v2Title
import com.gw2tb.gw2api.types.v2.GW2v2TokenInfo
import com.gw2tb.gw2api.types.v2.GW2v2Trait
import com.gw2tb.gw2api.types.v2.GW2v2WizardsVaultListing
import com.gw2tb.gw2api.types.v2.GW2v2WizardsVaultObjective
import com.gw2tb.gw2api.types.v2.GW2v2World
import com.gw2tb.gw2api.types.v2.GW2v2WorldBoss
import fi.northburns.northburns.gw2.client.myclient.ApiImplementation
import fi.northburns.northburns.gw2.client.myclient.EndpointSimplePaged
import fi.northburns.northburns.gw2.client.myclient.MyGw2ClientImpl
import fi.northburns.northburns.gw2.client.myclient.PlayerDataHardCoded

/**
 *
 * Won't implement the following:
 *
 * * createsubtoken, not really cacheable
 * * tokeninfo, while could be cacheable, but I don't think it's worth it. Maybe it is, time will tell.
 * * Guild endpoints (general + authenticated). Don't have the time at the moment for these..
 * * recipes/search, not cacheable so... But this could be interesting.
 * * PVP, don't have time for this at the moment...
 * * WVW, don't have time for this at the moment...
 * * "/v2/commerce/exchange/coins", "/v2/commerce/exchange/gems": require quantity param
 */
interface MyGw2Client {

    val general: ApiGeneral
    val mapInformation: ApiMapInformation
    val authenticated: CMap<PlayerId, ApiAuthenticated>
    val tradingPost: ApiTradingPost
    val tradingPostAuthenticated: CMap<PlayerId, ApiTradingPostAuthenticated>

    // TODO val convenience: Convenience
    // TODO val convenienceAuth: CMap<PlayerId, ConvenienceAuth>
    // TODO val convenienceAuthMulti: ConvenienceAuthMulti

    // TODO val hardstuck: ApiHardstuck
    // TODO val gw2Efficiency: ApiGw2Efficiency

    interface ApiGeneral {
        // TODO val root: AsyncLazy<Gw2ApiRoot>
        val build: EndpointSimple<GW2v2Build>
        val achievements: EndpointEntity<GW2AchievementId, GW2v2Achievement>
        val achievementsCategories: EndpointEntity<GW2AchievementCategoryId, GW2v2AchievementCategory>
        val achievementsGroups: EndpointEntity<GW2AchievementGroupId, GW2v2AchievementGroups>
        val mapChests: EndpointEntity<String, GW2v2MapChest>
        val worldBosses: EndpointEntity<GW2WorldBossId, GW2v2WorldBoss>
        val jadeBots: EndpointEntity<GW2JadeBotId, GW2v2JadeBot>
        val legendaryArmory: EndpointEntity<GW2ItemId, GW2v2LegendaryArmorySlot>
        val legends: EndpointEntity<GW2LegendId, GW2v2Legend>
        val masteries: EndpointEntity<GW2MasteryId, GW2v2Mastery>
        val minis: EndpointEntity<GW2MiniId, GW2v2Mini>
        val mountsSkins: EndpointEntity<GW2MountSkinId, GW2v2MountSkin>
        val mountsTypes: EndpointEntity<GW2MountTypeId, GW2v2MountType>
        val outfits: EndpointEntity<GW2OutfitId, GW2v2Outfit>
        val pets: EndpointEntity<GW2PetId, GW2v2Pet>
        val professions: EndpointEntity<GW2ProfessionId, GW2v2Profession>
        val races: EndpointEntity<GW2RaceId, GW2v2Race>
        val skiffs: EndpointEntity<GW2SkiffId, GW2v2Skiff>
        val skills: EndpointEntity<GW2SkillId, GW2v2Skill>
        val specializations: EndpointEntity<GW2SpecializationId, GW2v2Specialization>
        val traits: EndpointEntity<GW2TraitId, GW2v2Trait>
        val homeCats: EndpointEntity<GW2HomeInstanceCatId, GW2v2HomeInstanceCat>
        val homeNodes: EndpointEntity<GW2HomeInstanceNodeId, GW2v2HomeInstanceNode>
        val finishers: EndpointEntity<GW2FinisherId, GW2v2Finisher>
        val items: EndpointEntity<GW2ItemId, GW2v2Item>
        // TODO val itemStats: AsyncCache<GW2ItemStatsId, GW2v2ItemStats>
        val materials: EndpointEntity<GW2MaterialId, GW2v2MaterialCategory>
        val recipes: EndpointEntity<GW2RecipeId, GW2v2Recipe>
        val skins: EndpointEntity<GW2SkinId, GW2v2Skin>
        val colors: EndpointEntity<GW2ColorId, GW2v2Color>
        val dailyCrafting: EndpointEntity<String, GW2v2DailyCrafting>
        val dungeons: EndpointEntity<GW2DungeonId, GW2v2Dungeon>
        // TODO val files: AsyncCache<GW2CommonAssetId, GW2v2CommonAsset>
        val novelties: EndpointEntity<GW2NoveltyId, GW2v2Novelty>
        val quaggans: EndpointEntity<GW2QuagganId, GW2v2Quaggan>
        val raids: EndpointEntity<GW2RaidId, GW2v2Raid>
        val titles: EndpointEntity<GW2TitleId, GW2v2Title>
        val worlds: EndpointEntity<GW2WorldId, GW2v2World>
        val backstoryAnswers: EndpointEntity<GW2BackstoryAnswerId, GW2v2BackstoryAnswer>
        val backstoryQuestions: EndpointEntity<GW2BackstoryQuestionId, GW2v2BackstoryQuestion>
        val quests: EndpointEntity<Int, GW2v2Quest>
        val stories: EndpointEntity<GW2StoryId, GW2v2Story>
        val storiesSeasons: EndpointEntity<GW2StorySeasonId, GW2v2StorySeason>
        val currencies: EndpointEntity<GW2CurrencyId, GW2v2Currency>
        val wizardsVaultListings: EndpointEntity<GW2WizardsVaultListingId, GW2v2WizardsVaultListing>
        val wizardsVaultObjectives: EndpointEntity<GW2WizardsVaultObjectiveId, GW2v2WizardsVaultObjective>
        val emotes: EndpointEntity<GW2EmoteId, GW2v2Emote>
        val gliders: EndpointEntity<GW2GliderId, GW2v2Glider>
        val mailCarriers: EndpointEntity<GW2MailCarrierId, GW2v2Mailcarrier>
    }

    interface ApiMapInformation {
        val continents: EndpointEntity<GW2ContinentId, ApiContinent>
        val maps2: EndpointEntity<GW2MapId, GW2v2Map>

        interface ApiContinent {
            val id: GW2ContinentId
            val continent: GW2v2Continent
            val floors: EndpointEntity<GW2FloorId, ApiContinentFloor>
        }

        interface ApiContinentFloor {
            val id: GW2FloorId
            val floor: GW2v2ContinentFloor
            val regions: EndpointEntity<Int, Region>
        }
    }

    interface ApiTradingPost {
        val listings: EndpointEntity<GW2ItemId, GW2v2CommerceListing>
        val prices: EndpointEntity<GW2ItemId, GW2v2CommercePrices>
    }

    interface ApiTradingPostAuthenticated {
        val delivery: EndpointSimple<GW2v2CommerceDelivery>
        val transactionsCurrentBuys: EndpointSimplePaged<GW2v2CommerceTransaction>
        val transactionsCurrentSells: EndpointSimplePaged<GW2v2CommerceTransaction>
        val transactionHistoryBuys: EndpointSimplePaged<GW2v2CommerceTransaction>
        val transactionHistorySells: EndpointSimplePaged<GW2v2CommerceTransaction>
    }

    interface ApiAuthenticated {
        val account: EndpointSimple<GW2v2Account>
        val achievements: EndpointSimple<List<GW2v2AccountAchievement>>
        val bank: EndpointSimple<List<GW2v2AccountBankSlot?>>
        val dailyCrafting: EndpointSimple<List<String>>
        val dungeons: EndpointSimple<List<String>>
        val dyes: EndpointSimple<List<GW2DyeId>>
        val emotes: EndpointSimple<List<GW2EmoteId>>
        val finishers: EndpointSimple<List<GW2v2AccountFinisher>>
        val gliders: EndpointSimple<List<GW2GliderId>>
        val homeCats: EndpointSimple<List<GW2HomeInstanceCatId>>
        val homeNodes: EndpointSimple<List<GW2HomeInstanceNodeId>>
        val inventory: EndpointSimple<List<GW2v2AccountInventorySlot>>
        val jadebots: EndpointSimple<List<GW2JadeBotId>>
        val legendaryArmory: EndpointSimple<List<GW2v2AccountLegendaryArmoryUnlock>>
        val luck: EndpointSimple<List<GW2v2Luck>>
        val mailCarriers: EndpointSimple<List<GW2MailCarrierId>>
        val mapChests: EndpointSimple<List<String>>
        val masteries: EndpointSimple<List<GW2v2AccountMastery>>
        val masteryPoints: EndpointSimple<GW2v2AccountMasteryPoint>
        val materials: EndpointSimple<List<GW2v2AccountMaterial>>
        val minis: EndpointSimple<List<GW2MiniId>>
        val mountsSkins: EndpointSimple<List<GW2MountSkinId>>
        val mountsTypes: EndpointSimple<List<GW2MountTypeId>>
        val novelties: EndpointSimple<List<GW2NoveltyId>>
        val outfits: EndpointSimple<List<GW2OutfitId>>
        // TODO val progression: AsyncLazy<Collection<AccountProgression>>
        val pvpHeroes: EndpointSimple<List<GW2PvpHeroId>>
        val raids: EndpointSimple<List<GW2RaidId>>
        val recipes: EndpointSimple<List<GW2RecipeId>>
        val skiffs: EndpointSimple<List<GW2SkiffId>>
        val skins: EndpointSimple<List<GW2SkinId>>
        val titles: EndpointSimple<List<GW2TitleId>>
        val wallet: EndpointSimple<List<GW2v2AccountWalletCurrency>>
        val wizardsVaultDaily: EndpointSimple<GW2v2AccountWizardsVaultDaily>
        val wizardsVaultListings: EndpointSimple<List<GW2v2AccountWizardsVaultListing>>
        val wizardsVaultSpecial: EndpointSimple<GW2v2AccountWizardsVaultSpecial>
        val wizardsVaultWeekly: EndpointSimple<GW2v2AccountWizardsVaultWeekly>
        val worldBosses: EndpointSimple<List<GW2WorldBossId>>
        val tokenInfo: EndpointSimple<GW2v2TokenInfo>

        val characters: EndpointEntity<String, GW2v2Character>

    }

    // TODO
//    interface ApiGw2Efficiency {
//        val recipes: AsyncLazy<Set<Recipe.RecipeGw2E>>
//    }

    /**
     * TODO
     * https://gw2-api.hardstuck.gg/ui
     */
//    interface ApiHardstuck {
//        val items: AsyncCache<HsItemId, HsItem>
//        val itemStats: AsyncCache<HsAttributeSetId, HsAttributeSet>
//        val palettes: AsyncCache<HsPaletteId, HsPalette>
//        val pets: AsyncCache<HsPetId, HsPet>
//        val professions: AsyncCache<HsProfessionId, HsProfession>
//        val pvpAmulets: AsyncCache<HsItemId, HsItem>
//        val skills: AsyncCache<HsSkillId, HsSkill>
//        val skins: AsyncCache<HsSkinId, HsSkin>
//        val specializations: AsyncCache<HsSpecializationId, HsSpecialization>
//        val texts: AsyncCache<HsTextId, HsText>
//        val titles: AsyncCache<HsTitleId, HsTitle>
//        val traits: AsyncCache<HsTraitId, HsTrait>
//    }

    companion object {
        fun create() : MyGw2Client {
            return MyGw2ClientImpl({PlayerDataHardCoded.playerData(it)})
        }
    }

    suspend fun invalidatePlayerCaches()
    suspend fun invalidateTradingPostData()
}

