package northburns.gw2.client2

import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementGroupId
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.GW2BackstoryAnswerId
import com.gw2tb.gw2api.types.GW2BackstoryQuestionId
import com.gw2tb.gw2api.types.GW2ColorId
import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2DungeonId
import com.gw2tb.gw2api.types.GW2DyeId
import com.gw2tb.gw2api.types.GW2EmoteId
import com.gw2tb.gw2api.types.GW2FinisherId
import com.gw2tb.gw2api.types.GW2GliderId
import com.gw2tb.gw2api.types.GW2HomeInstanceCatId
import com.gw2tb.gw2api.types.GW2HomeInstanceNodeId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.GW2JadeBotId
import com.gw2tb.gw2api.types.GW2LegendId
import com.gw2tb.gw2api.types.GW2MailCarrierId
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
import com.gw2tb.gw2api.types.v2.GW2v2Color
import com.gw2tb.gw2api.types.v2.GW2v2CommerceDelivery
import com.gw2tb.gw2api.types.v2.GW2v2CommerceListing
import com.gw2tb.gw2api.types.v2.GW2v2CommercePrices
import com.gw2tb.gw2api.types.v2.GW2v2CommerceTransaction
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
import kotlinx.coroutines.CoroutineScope
import northburns.gw2.client.myclient.PlayerId

interface MyGw2Client2 {
    val general: General
    val tp: TradingPost
    suspend fun auth(playerId: PlayerId): Authenticated
    suspend fun authTp(playerId: PlayerId): TradingPostAuthenticated

    interface General {
        val build: SimpleService<GW2v2Build>
        val achievements: EntityService<GW2AchievementId, GW2v2Achievement>
        val achievementsCategories: EntityService<GW2AchievementCategoryId, GW2v2AchievementCategory>
        val achievementsGroups: EntityService<GW2AchievementGroupId, GW2v2AchievementGroups>
        val mapChests: EntityService<String, GW2v2MapChest>
        val worldBosses: EntityService<GW2WorldBossId, GW2v2WorldBoss>
        val jadeBots: EntityService<GW2JadeBotId, GW2v2JadeBot>
        val legendaryArmory: EntityService<GW2ItemId, GW2v2LegendaryArmorySlot>
        val legends: EntityService<GW2LegendId, GW2v2Legend>
        val masteries: EntityService<GW2MasteryId, GW2v2Mastery>
        val minis: EntityService<GW2MiniId, GW2v2Mini>
        val mountsSkins: EntityService<GW2MountSkinId, GW2v2MountSkin>
        val mountsTypes: EntityService<GW2MountTypeId, GW2v2MountType>
        val outfits: EntityService<GW2OutfitId, GW2v2Outfit>
        val pets: EntityService<GW2PetId, GW2v2Pet>
        val professions: EntityService<GW2ProfessionId, GW2v2Profession>
        val races: EntityService<GW2RaceId, GW2v2Race>
        val skiffs: EntityService<GW2SkiffId, GW2v2Skiff>
        val skills: EntityService<GW2SkillId, GW2v2Skill>
        val specializations: EntityService<GW2SpecializationId, GW2v2Specialization>
        val traits: EntityService<GW2TraitId, GW2v2Trait>
        val homeCats: EntityService<GW2HomeInstanceCatId, GW2v2HomeInstanceCat>
        val homeNodes: EntityService<GW2HomeInstanceNodeId, GW2v2HomeInstanceNode>
        val finishers: EntityService<GW2FinisherId, GW2v2Finisher>
        val items: EntityService<GW2ItemId, GW2v2Item>
        val materials: EntityService<GW2MaterialId, GW2v2MaterialCategory>
        val recipes: EntityService<GW2RecipeId, GW2v2Recipe>
        val skins: EntityService<GW2SkinId, GW2v2Skin>
        val colors: EntityService<GW2ColorId, GW2v2Color>
        val dailyCrafting: EntityService<String, GW2v2DailyCrafting>
        val dungeons: EntityService<GW2DungeonId, GW2v2Dungeon>
        val novelties: EntityService<GW2NoveltyId, GW2v2Novelty>
        val quaggans: EntityService<GW2QuagganId, GW2v2Quaggan>
        val raids: EntityService<GW2RaidId, GW2v2Raid>
        val titles: EntityService<GW2TitleId, GW2v2Title>
        val worlds: EntityService<GW2WorldId, GW2v2World>
        val backstoryAnswers: EntityService<GW2BackstoryAnswerId, GW2v2BackstoryAnswer>
        val backstoryQuestions: EntityService<GW2BackstoryQuestionId, GW2v2BackstoryQuestion>
        val quests: EntityService<Long, GW2v2Quest>
        val stories: EntityService<GW2StoryId, GW2v2Story>
        val storiesSeasons: EntityService<GW2StorySeasonId, GW2v2StorySeason>
        val currencies: EntityService<GW2CurrencyId, GW2v2Currency>
        val wizardsVaultListings: EntityService<GW2WizardsVaultListingId, GW2v2WizardsVaultListing>
        val wizardsVaultObjectives: EntityService<GW2WizardsVaultObjectiveId, GW2v2WizardsVaultObjective>
        val emotes: EntityService<GW2EmoteId, GW2v2Emote>
        val gliders: EntityService<GW2GliderId, GW2v2Glider>
        val mailCarriers: EntityService<GW2MailCarrierId, GW2v2Mailcarrier>

        // TODO val root: AsyncLazy<Gw2ApiRoot>
        // TODO val itemStats: AsyncCache<GW2ItemStatsId, GW2v2ItemStats>
        // TODO val files: AsyncCache<GW2CommonAssetId, GW2v2CommonAsset>
        // TODO ApiMapInformation (continents etc)
    }

    /**
     * Warning: TP endpoints are _not_ cached.
     */
    interface TradingPost {
        val listings: EntityService<GW2ItemId, GW2v2CommerceListing>
        val prices: EntityService<GW2ItemId, GW2v2CommercePrices>
    }

    /**
     * Warning: TP endpoints are _not_ cached.
     */
    interface TradingPostAuthenticated {
        val delivery: SimpleService<GW2v2CommerceDelivery>
        val transactionsCurrentBuys: SimpleService<List<GW2v2CommerceTransaction>>
        val transactionsCurrentSells: SimpleService<List<GW2v2CommerceTransaction>>
        val transactionHistoryBuys: SimpleService<List<GW2v2CommerceTransaction>>
        val transactionHistorySells: SimpleService<List<GW2v2CommerceTransaction>>
    }

    interface Authenticated {
        val characters: EntityService<String, GW2v2Character>

        val account: SimpleService<GW2v2Account>
        val achievements: SimpleService<List<GW2v2AccountAchievement>>
        val bank: SimpleService<List<GW2v2AccountBankSlot?>>
        val dailyCrafting: SimpleService<List<String>>
        val dungeons: SimpleService<List<String>>
        val dyes: SimpleService<List<GW2DyeId>>
        val emotes: SimpleService<List<GW2EmoteId>>
        val finishers: SimpleService<List<GW2v2AccountFinisher>>
        val gliders: SimpleService<List<GW2GliderId>>
        val homeCats: SimpleService<List<GW2HomeInstanceCatId>>
        val homeNodes: SimpleService<List<GW2HomeInstanceNodeId>>
        val inventory: SimpleService<List<GW2v2AccountInventorySlot?>>
        val jadebots: SimpleService<List<GW2JadeBotId>>
        val legendaryArmory: SimpleService<List<GW2v2AccountLegendaryArmoryUnlock>>
        val luck: SimpleService<List<GW2v2Luck>>
        val mailCarriers: SimpleService<List<GW2MailCarrierId>>
        val mapChests: SimpleService<List<String>>
        val masteries: SimpleService<List<GW2v2AccountMastery>>
        val masteryPoints: SimpleService<GW2v2AccountMasteryPoint>
        val materials: SimpleService<List<GW2v2AccountMaterial>>
        val minis: SimpleService<List<GW2MiniId>>
        val mountsSkins: SimpleService<List<GW2MountSkinId>>
        val mountsTypes: SimpleService<List<GW2MountTypeId>>
        val novelties: SimpleService<List<GW2NoveltyId>>
        val outfits: SimpleService<List<GW2OutfitId>>

        // TODO val progression: AsyncLazy<Collection<AccountProgression>>
        val pvpHeroes: SimpleService<List<GW2PvpHeroId>>
        val raids: SimpleService<List<GW2RaidId>>
        val recipes: SimpleService<List<GW2RecipeId>>
        val skiffs: SimpleService<List<GW2SkiffId>>
        val skins: SimpleService<List<GW2SkinId>>
        val titles: SimpleService<List<GW2TitleId>>
        val wallet: SimpleService<List<GW2v2AccountWalletCurrency>>
        val wizardsVaultDaily: SimpleService<GW2v2AccountWizardsVaultDaily>
        val wizardsVaultListings: SimpleService<List<GW2v2AccountWizardsVaultListing>>
        val wizardsVaultSpecial: SimpleService<GW2v2AccountWizardsVaultSpecial>
        val wizardsVaultWeekly: SimpleService<GW2v2AccountWizardsVaultWeekly>
        val worldBosses: SimpleService<List<GW2WorldBossId>>
        val tokenInfo: SimpleService<GW2v2TokenInfo>
    }

    fun interface SimpleService<V> {
        suspend operator fun invoke(): V
        suspend fun get(): V = invoke()
    }

    interface EntityService<K : Any, V : Any> {
        suspend fun getIds(): List<K>
        suspend fun get(k: K): V
        suspend fun getMany(ks: Collection<K>): Collection<V>
        suspend fun getAll(): Map<K, V>

        /**
         * Second is missing keys
         */
        suspend fun getManyOnlyCached(ks: Collection<K>): Pair<Collection<V>, Collection<K>>
    }

    companion object {
        fun create(
            scope: CoroutineScope,
            playerData: PlayerDataProvider,
        ): MyGw2Client2 {
            return MyGw2Client2Impl(scope = scope, playerData = playerData)
        }
    }

}
