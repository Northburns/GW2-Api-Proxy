package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClient
import com.gw2tb.gw2api.client.RequestConfigurer
import com.gw2tb.gw2api.client.v2.gw2v2Account
import com.gw2tb.gw2api.client.v2.gw2v2AccountAchievements
import com.gw2tb.gw2api.client.v2.gw2v2AccountBank
import com.gw2tb.gw2api.client.v2.gw2v2AccountDailyCrafting
import com.gw2tb.gw2api.client.v2.gw2v2AccountDungeons
import com.gw2tb.gw2api.client.v2.gw2v2AccountDyes
import com.gw2tb.gw2api.client.v2.gw2v2AccountEmotes
import com.gw2tb.gw2api.client.v2.gw2v2AccountFinishers
import com.gw2tb.gw2api.client.v2.gw2v2AccountGliders
import com.gw2tb.gw2api.client.v2.gw2v2AccountHomeCats
import com.gw2tb.gw2api.client.v2.gw2v2AccountHomeNodes
import com.gw2tb.gw2api.client.v2.gw2v2AccountInventory
import com.gw2tb.gw2api.client.v2.gw2v2AccountJadeBots
import com.gw2tb.gw2api.client.v2.gw2v2AccountLegendaryArmory
import com.gw2tb.gw2api.client.v2.gw2v2AccountLuck
import com.gw2tb.gw2api.client.v2.gw2v2AccountMailcarriers
import com.gw2tb.gw2api.client.v2.gw2v2AccountMapChests
import com.gw2tb.gw2api.client.v2.gw2v2AccountMasteries
import com.gw2tb.gw2api.client.v2.gw2v2AccountMasteryPoints
import com.gw2tb.gw2api.client.v2.gw2v2AccountMaterials
import com.gw2tb.gw2api.client.v2.gw2v2AccountMinis
import com.gw2tb.gw2api.client.v2.gw2v2AccountMountsSkins
import com.gw2tb.gw2api.client.v2.gw2v2AccountMountsTypes
import com.gw2tb.gw2api.client.v2.gw2v2AccountNovelties
import com.gw2tb.gw2api.client.v2.gw2v2AccountOutfits
import com.gw2tb.gw2api.client.v2.gw2v2AccountPvPHeroes
import com.gw2tb.gw2api.client.v2.gw2v2AccountRaids
import com.gw2tb.gw2api.client.v2.gw2v2AccountRecipes
import com.gw2tb.gw2api.client.v2.gw2v2AccountSkiffs
import com.gw2tb.gw2api.client.v2.gw2v2AccountSkins
import com.gw2tb.gw2api.client.v2.gw2v2AccountTitles
import com.gw2tb.gw2api.client.v2.gw2v2AccountWallet
import com.gw2tb.gw2api.client.v2.gw2v2AccountWizardsVaultDaily
import com.gw2tb.gw2api.client.v2.gw2v2AccountWizardsVaultListings
import com.gw2tb.gw2api.client.v2.gw2v2AccountWizardsVaultSpecial
import com.gw2tb.gw2api.client.v2.gw2v2AccountWizardsVaultWeekly
import com.gw2tb.gw2api.client.v2.gw2v2AccountWorldBosses
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsByIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsByPage
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsCategoriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsCategoriesByPage
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsCategoriesIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsGroupsByIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsGroupsByPage
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsGroupsIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryAnswersByIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryAnswersByPage
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryAnswersIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryQuestionsByIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryQuestionsByPage
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryQuestionsIds
import com.gw2tb.gw2api.client.v2.gw2v2Build
import com.gw2tb.gw2api.client.v2.gw2v2CharactersByIds
import com.gw2tb.gw2api.client.v2.gw2v2CharactersByPage
import com.gw2tb.gw2api.client.v2.gw2v2CharactersIds
import com.gw2tb.gw2api.client.v2.gw2v2ColorsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ColorsByPage
import com.gw2tb.gw2api.client.v2.gw2v2ColorsIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceDelivery
import com.gw2tb.gw2api.client.v2.gw2v2CommerceListingsByIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceListingsByPage
import com.gw2tb.gw2api.client.v2.gw2v2CommerceListingsIds
import com.gw2tb.gw2api.client.v2.gw2v2CommercePricesByIds
import com.gw2tb.gw2api.client.v2.gw2v2CommercePricesByPage
import com.gw2tb.gw2api.client.v2.gw2v2CommercePricesIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceTransactionsByPage
import com.gw2tb.gw2api.client.v2.gw2v2CurrenciesByIds
import com.gw2tb.gw2api.client.v2.gw2v2CurrenciesByPage
import com.gw2tb.gw2api.client.v2.gw2v2CurrenciesIds
import com.gw2tb.gw2api.client.v2.gw2v2DailyCraftingByIds
import com.gw2tb.gw2api.client.v2.gw2v2DailyCraftingByPage
import com.gw2tb.gw2api.client.v2.gw2v2DailyCraftingIds
import com.gw2tb.gw2api.client.v2.gw2v2DungeonsByIds
import com.gw2tb.gw2api.client.v2.gw2v2DungeonsByPage
import com.gw2tb.gw2api.client.v2.gw2v2DungeonsIds
import com.gw2tb.gw2api.client.v2.gw2v2EmotesByIds
import com.gw2tb.gw2api.client.v2.gw2v2EmotesByPage
import com.gw2tb.gw2api.client.v2.gw2v2EmotesIds
import com.gw2tb.gw2api.client.v2.gw2v2FinishersByIds
import com.gw2tb.gw2api.client.v2.gw2v2FinishersByPage
import com.gw2tb.gw2api.client.v2.gw2v2FinishersIds
import com.gw2tb.gw2api.client.v2.gw2v2GlidersByIds
import com.gw2tb.gw2api.client.v2.gw2v2GlidersByPage
import com.gw2tb.gw2api.client.v2.gw2v2GlidersIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeCatsByIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeCatsByPage
import com.gw2tb.gw2api.client.v2.gw2v2HomeCatsIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeNodesByIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeNodesByPage
import com.gw2tb.gw2api.client.v2.gw2v2HomeNodesIds
import com.gw2tb.gw2api.client.v2.gw2v2ItemsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ItemsByPage
import com.gw2tb.gw2api.client.v2.gw2v2ItemsIds
import com.gw2tb.gw2api.client.v2.gw2v2JadeBotsByIds
import com.gw2tb.gw2api.client.v2.gw2v2JadeBotsByPage
import com.gw2tb.gw2api.client.v2.gw2v2JadeBotsIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendaryArmoryByIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendaryArmoryByPage
import com.gw2tb.gw2api.client.v2.gw2v2LegendaryArmoryIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendsByIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendsByPage
import com.gw2tb.gw2api.client.v2.gw2v2LegendsIds
import com.gw2tb.gw2api.client.v2.gw2v2MailcarriersByIds
import com.gw2tb.gw2api.client.v2.gw2v2MailcarriersByPage
import com.gw2tb.gw2api.client.v2.gw2v2MailcarriersIds
import com.gw2tb.gw2api.client.v2.gw2v2MapChestsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MapChestsByPage
import com.gw2tb.gw2api.client.v2.gw2v2MapChestsIds
import com.gw2tb.gw2api.client.v2.gw2v2MasteriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2MasteriesByPage
import com.gw2tb.gw2api.client.v2.gw2v2MasteriesIds
import com.gw2tb.gw2api.client.v2.gw2v2MaterialsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MaterialsByPage
import com.gw2tb.gw2api.client.v2.gw2v2MaterialsIds
import com.gw2tb.gw2api.client.v2.gw2v2MinisByIds
import com.gw2tb.gw2api.client.v2.gw2v2MinisByPage
import com.gw2tb.gw2api.client.v2.gw2v2MinisIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsSkinsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsSkinsByPage
import com.gw2tb.gw2api.client.v2.gw2v2MountsSkinsIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsTypesByIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsTypesByPage
import com.gw2tb.gw2api.client.v2.gw2v2MountsTypesIds
import com.gw2tb.gw2api.client.v2.gw2v2NoveltiesByIds
import com.gw2tb.gw2api.client.v2.gw2v2NoveltiesByPage
import com.gw2tb.gw2api.client.v2.gw2v2NoveltiesIds
import com.gw2tb.gw2api.client.v2.gw2v2OutfitsByIds
import com.gw2tb.gw2api.client.v2.gw2v2OutfitsByPage
import com.gw2tb.gw2api.client.v2.gw2v2OutfitsIds
import com.gw2tb.gw2api.client.v2.gw2v2PetsByIds
import com.gw2tb.gw2api.client.v2.gw2v2PetsByPage
import com.gw2tb.gw2api.client.v2.gw2v2PetsIds
import com.gw2tb.gw2api.client.v2.gw2v2ProfessionsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ProfessionsByPage
import com.gw2tb.gw2api.client.v2.gw2v2ProfessionsIds
import com.gw2tb.gw2api.client.v2.gw2v2QuaggansByIds
import com.gw2tb.gw2api.client.v2.gw2v2QuaggansByPage
import com.gw2tb.gw2api.client.v2.gw2v2QuaggansIds
import com.gw2tb.gw2api.client.v2.gw2v2QuestsByIds
import com.gw2tb.gw2api.client.v2.gw2v2QuestsByPage
import com.gw2tb.gw2api.client.v2.gw2v2QuestsIds
import com.gw2tb.gw2api.client.v2.gw2v2RacesByIds
import com.gw2tb.gw2api.client.v2.gw2v2RacesByPage
import com.gw2tb.gw2api.client.v2.gw2v2RacesIds
import com.gw2tb.gw2api.client.v2.gw2v2RaidsByIds
import com.gw2tb.gw2api.client.v2.gw2v2RaidsByPage
import com.gw2tb.gw2api.client.v2.gw2v2RaidsIds
import com.gw2tb.gw2api.client.v2.gw2v2RecipesByIds
import com.gw2tb.gw2api.client.v2.gw2v2RecipesByPage
import com.gw2tb.gw2api.client.v2.gw2v2RecipesIds
import com.gw2tb.gw2api.client.v2.gw2v2SkiffsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkiffsByPage
import com.gw2tb.gw2api.client.v2.gw2v2SkiffsIds
import com.gw2tb.gw2api.client.v2.gw2v2SkillsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkillsByPage
import com.gw2tb.gw2api.client.v2.gw2v2SkillsIds
import com.gw2tb.gw2api.client.v2.gw2v2SkinsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkinsByPage
import com.gw2tb.gw2api.client.v2.gw2v2SkinsIds
import com.gw2tb.gw2api.client.v2.gw2v2SpecializationsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SpecializationsByPage
import com.gw2tb.gw2api.client.v2.gw2v2SpecializationsIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesByPage
import com.gw2tb.gw2api.client.v2.gw2v2StoriesIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesSeasonsByIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesSeasonsByPage
import com.gw2tb.gw2api.client.v2.gw2v2StoriesSeasonsIds
import com.gw2tb.gw2api.client.v2.gw2v2TitlesByIds
import com.gw2tb.gw2api.client.v2.gw2v2TitlesByPage
import com.gw2tb.gw2api.client.v2.gw2v2TitlesIds
import com.gw2tb.gw2api.client.v2.gw2v2TokenInfo
import com.gw2tb.gw2api.client.v2.gw2v2TraitsByIds
import com.gw2tb.gw2api.client.v2.gw2v2TraitsByPage
import com.gw2tb.gw2api.client.v2.gw2v2TraitsIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultListingsByIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultListingsByPage
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultListingsIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultObjectivesByIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultObjectivesByPage
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultObjectivesIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldBossesByIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldBossesByPage
import com.gw2tb.gw2api.client.v2.gw2v2WorldBossesIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldsByIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldsByPage
import com.gw2tb.gw2api.client.v2.gw2v2WorldsIds
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
import northburns.gw2.client.myclient.MyGw2Client.ApiAuthenticated
import northburns.gw2.client.myclient.MyGw2Client.ApiGeneral
import northburns.gw2.client.myclient.MyGw2Client.ApiMapInformation
import northburns.gw2.client.myclient.MyGw2Client.ApiTradingPost
import northburns.gw2.client.myclient.MyGw2Client.ApiTradingPostAuthenticated

class ApiImplementation(
    client: Gw2ApiClient,
    player: PlayerData?,
) {

    private val ef = EndpointFactory(
        client,
        RequestConfigurer {
            player?.key?.key?.also { key -> withAPIKey(key) }
        },
    )

    val apiGeneral: ApiGeneral = ApiGeneralImpl()
    val apiMapInformation: ApiMapInformation = ApiMapInformationImpl()
    val apiTradingPost: ApiTradingPost = ApiTradingPostImpl()
    val apiTradingPostAuthenticated: ApiTradingPostAuthenticated = ApiTradingPostAuthenticatedImpl()
    val apiAuthenticated: ApiAuthenticated = ApiAuthenticatedImpl()

    private inner class ApiGeneralImpl : ApiGeneral {
        override val build = ef.simple(::gw2v2Build)
        override val achievements: EndpointEntity<GW2AchievementId, GW2v2Achievement> = ef.entity(
            allIds = ::gw2v2AchievementsIds,
            byIds = ::gw2v2AchievementsByIds,
            byPage = ::gw2v2AchievementsByPage,
            getId = GW2v2Achievement::id,
        )
        override val achievementsCategories: EndpointEntity<GW2AchievementCategoryId, GW2v2AchievementCategory> =
            ef.entity(
                allIds = ::gw2v2AchievementsCategoriesIds,
                byIds = ::gw2v2AchievementsCategoriesByIds,
                byPage = ::gw2v2AchievementsCategoriesByPage,
                getId = GW2v2AchievementCategory::id,
            )
        override val achievementsGroups: EndpointEntity<GW2AchievementGroupId, GW2v2AchievementGroups> = ef.entity(
            allIds = ::gw2v2AchievementsGroupsIds,
            byIds = ::gw2v2AchievementsGroupsByIds,
            byPage = ::gw2v2AchievementsGroupsByPage,
            getId = GW2v2AchievementGroups::id,
        )
        override val mapChests: EndpointEntity<String, GW2v2MapChest> = ef.entity(
            allIds = ::gw2v2MapChestsIds,
            byIds = ::gw2v2MapChestsByIds,
            byPage = ::gw2v2MapChestsByPage,
            getId = GW2v2MapChest::id,
        )
        override val worldBosses: EndpointEntity<GW2WorldBossId, GW2v2WorldBoss> = ef.entity(
            allIds = ::gw2v2WorldBossesIds,
            byIds = ::gw2v2WorldBossesByIds,
            byPage = ::gw2v2WorldBossesByPage,
            getId = GW2v2WorldBoss::id,
        )
        override val jadeBots: EndpointEntity<GW2JadeBotId, GW2v2JadeBot> = ef.entity(
            allIds = ::gw2v2JadeBotsIds,
            byIds = ::gw2v2JadeBotsByIds,
            byPage = ::gw2v2JadeBotsByPage,
            getId = GW2v2JadeBot::id,
        )
        override val legendaryArmory: EndpointEntity<GW2ItemId, GW2v2LegendaryArmorySlot> = ef.entity(
            allIds = ::gw2v2LegendaryArmoryIds,
            byIds = ::gw2v2LegendaryArmoryByIds,
            byPage = ::gw2v2LegendaryArmoryByPage,
            getId = GW2v2LegendaryArmorySlot::id,
        )
        override val legends: EndpointEntity<GW2LegendId, GW2v2Legend> = ef.entity(
            allIds = ::gw2v2LegendsIds,
            byIds = ::gw2v2LegendsByIds,
            byPage = ::gw2v2LegendsByPage,
            getId = GW2v2Legend::id,
        )
        override val masteries: EndpointEntity<GW2MasteryId, GW2v2Mastery> = ef.entity(
            allIds = ::gw2v2MasteriesIds,
            byIds = ::gw2v2MasteriesByIds,
            byPage = ::gw2v2MasteriesByPage,
            getId = GW2v2Mastery::id,
        )
        override val minis: EndpointEntity<GW2MiniId, GW2v2Mini> = ef.entity(
            allIds = ::gw2v2MinisIds,
            byIds = ::gw2v2MinisByIds,
            byPage = ::gw2v2MinisByPage,
            getId = GW2v2Mini::id,
        )
        override val mountsSkins: EndpointEntity<GW2MountSkinId, GW2v2MountSkin> = ef.entity(
            allIds = ::gw2v2MountsSkinsIds,
            byIds = ::gw2v2MountsSkinsByIds,
            byPage = ::gw2v2MountsSkinsByPage,
            getId = GW2v2MountSkin::id,
        )
        override val mountsTypes: EndpointEntity<GW2MountTypeId, GW2v2MountType> = ef.entity(
            allIds = ::gw2v2MountsTypesIds,
            byIds = ::gw2v2MountsTypesByIds,
            byPage = ::gw2v2MountsTypesByPage,
            getId = GW2v2MountType::id,
        )
        override val outfits: EndpointEntity<GW2OutfitId, GW2v2Outfit> = ef.entity(
            allIds = ::gw2v2OutfitsIds,
            byIds = ::gw2v2OutfitsByIds,
            byPage = ::gw2v2OutfitsByPage,
            getId = GW2v2Outfit::id,
        )
        override val pets: EndpointEntity<GW2PetId, GW2v2Pet> = ef.entity(
            allIds = ::gw2v2PetsIds,
            byIds = ::gw2v2PetsByIds,
            byPage = ::gw2v2PetsByPage,
            getId = GW2v2Pet::id,
        )
        override val professions: EndpointEntity<GW2ProfessionId, GW2v2Profession> = ef.entity(
            allIds = ::gw2v2ProfessionsIds,
            byIds = ::gw2v2ProfessionsByIds,
            byPage = ::gw2v2ProfessionsByPage,
            getId = GW2v2Profession::id,
        )
        override val races: EndpointEntity<GW2RaceId, GW2v2Race> = ef.entity(
            allIds = ::gw2v2RacesIds,
            byIds = ::gw2v2RacesByIds,
            byPage = ::gw2v2RacesByPage,
            getId = GW2v2Race::id,
        )
        override val skiffs: EndpointEntity<GW2SkiffId, GW2v2Skiff> = ef.entity(
            allIds = ::gw2v2SkiffsIds,
            byIds = ::gw2v2SkiffsByIds,
            byPage = ::gw2v2SkiffsByPage,
            getId = GW2v2Skiff::id,
        )
        override val skills: EndpointEntity<GW2SkillId, GW2v2Skill> = ef.entity(
            allIds = ::gw2v2SkillsIds,
            byIds = ::gw2v2SkillsByIds,
            byPage = ::gw2v2SkillsByPage,
            getId = GW2v2Skill::id,
        )
        override val specializations: EndpointEntity<GW2SpecializationId, GW2v2Specialization> = ef.entity(
            allIds = ::gw2v2SpecializationsIds,
            byIds = ::gw2v2SpecializationsByIds,
            byPage = ::gw2v2SpecializationsByPage,
            getId = GW2v2Specialization::id,
        )
        override val traits: EndpointEntity<GW2TraitId, GW2v2Trait> = ef.entity(
            allIds = ::gw2v2TraitsIds,
            byIds = ::gw2v2TraitsByIds,
            byPage = ::gw2v2TraitsByPage,
            getId = GW2v2Trait::id,
        )
        override val homeCats: EndpointEntity<GW2HomeInstanceCatId, GW2v2HomeInstanceCat> = ef.entity(
            allIds = ::gw2v2HomeCatsIds,
            byIds = ::gw2v2HomeCatsByIds,
            byPage = ::gw2v2HomeCatsByPage,
            getId = GW2v2HomeInstanceCat::id,
        )
        override val homeNodes: EndpointEntity<GW2HomeInstanceNodeId, GW2v2HomeInstanceNode> = ef.entity(
            allIds = ::gw2v2HomeNodesIds,
            byIds = ::gw2v2HomeNodesByIds,
            byPage = ::gw2v2HomeNodesByPage,
            getId = GW2v2HomeInstanceNode::id,
        )
        override val finishers: EndpointEntity<GW2FinisherId, GW2v2Finisher> = ef.entity(
            allIds = ::gw2v2FinishersIds,
            byIds = ::gw2v2FinishersByIds,
            byPage = ::gw2v2FinishersByPage,
            getId = GW2v2Finisher::id,
        )
        override val items: EndpointEntity<GW2ItemId, GW2v2Item> = ef.entity(
            allIds = ::gw2v2ItemsIds,
            byIds = ::gw2v2ItemsByIds,
            byPage = ::gw2v2ItemsByPage,
            getId = GW2v2Item::id,
        )
        override val materials: EndpointEntity<GW2MaterialId, GW2v2MaterialCategory> = ef.entity(
            allIds = ::gw2v2MaterialsIds,
            byIds = ::gw2v2MaterialsByIds,
            byPage = ::gw2v2MaterialsByPage,
            getId = GW2v2MaterialCategory::id,
        )
        override val recipes: EndpointEntity<GW2RecipeId, GW2v2Recipe> = ef.entity(
            allIds = ::gw2v2RecipesIds,
            byIds = ::gw2v2RecipesByIds,
            byPage = ::gw2v2RecipesByPage,
            getId = GW2v2Recipe::id,
        )
        override val skins: EndpointEntity<GW2SkinId, GW2v2Skin> = ef.entity(
            allIds = ::gw2v2SkinsIds,
            byIds = ::gw2v2SkinsByIds,
            byPage = ::gw2v2SkinsByPage,
            getId = GW2v2Skin::id,
        )
        override val colors: EndpointEntity<GW2ColorId, GW2v2Color> = ef.entity(
            allIds = ::gw2v2ColorsIds,
            byIds = ::gw2v2ColorsByIds,
            byPage = ::gw2v2ColorsByPage,
            getId = GW2v2Color::id,
        )
        override val dailyCrafting: EndpointEntity<String, GW2v2DailyCrafting> = ef.entity(
            allIds = ::gw2v2DailyCraftingIds,
            byIds = ::gw2v2DailyCraftingByIds,
            byPage = ::gw2v2DailyCraftingByPage,
            getId = GW2v2DailyCrafting::id,
        )
        override val dungeons: EndpointEntity<GW2DungeonId, GW2v2Dungeon> = ef.entity(
            allIds = ::gw2v2DungeonsIds,
            byIds = ::gw2v2DungeonsByIds,
            byPage = ::gw2v2DungeonsByPage,
            getId = GW2v2Dungeon::id,
        )
        override val novelties: EndpointEntity<GW2NoveltyId, GW2v2Novelty> = ef.entity(
            allIds = ::gw2v2NoveltiesIds,
            byIds = ::gw2v2NoveltiesByIds,
            byPage = ::gw2v2NoveltiesByPage,
            getId = GW2v2Novelty::id,
        )
        override val quaggans: EndpointEntity<GW2QuagganId, GW2v2Quaggan> = ef.entity(
            allIds = ::gw2v2QuaggansIds,
            byIds = ::gw2v2QuaggansByIds,
            byPage = ::gw2v2QuaggansByPage,
            getId = GW2v2Quaggan::id,
        )
        override val raids: EndpointEntity<GW2RaidId, GW2v2Raid> = ef.entity(
            allIds = ::gw2v2RaidsIds,
            byIds = ::gw2v2RaidsByIds,
            byPage = ::gw2v2RaidsByPage,
            getId = GW2v2Raid::id,
        )
        override val titles: EndpointEntity<GW2TitleId, GW2v2Title> = ef.entity(
            allIds = ::gw2v2TitlesIds,
            byIds = ::gw2v2TitlesByIds,
            byPage = ::gw2v2TitlesByPage,
            getId = GW2v2Title::id,
        )
        override val worlds: EndpointEntity<GW2WorldId, GW2v2World> = ef.entity(
            allIds = ::gw2v2WorldsIds,
            byIds = ::gw2v2WorldsByIds,
            byPage = ::gw2v2WorldsByPage,
            getId = GW2v2World::id,
        )
        override val backstoryAnswers: EndpointEntity<GW2BackstoryAnswerId, GW2v2BackstoryAnswer> = ef.entity(
            allIds = ::gw2v2BackstoryAnswersIds,
            byIds = ::gw2v2BackstoryAnswersByIds,
            byPage = ::gw2v2BackstoryAnswersByPage,
            getId = GW2v2BackstoryAnswer::id,
        )
        override val backstoryQuestions: EndpointEntity<GW2BackstoryQuestionId, GW2v2BackstoryQuestion> = ef.entity(
            allIds = ::gw2v2BackstoryQuestionsIds,
            byIds = ::gw2v2BackstoryQuestionsByIds,
            byPage = ::gw2v2BackstoryQuestionsByPage,
            getId = GW2v2BackstoryQuestion::id,
        )
        override val quests: EndpointEntity<Int, GW2v2Quest> = ef.entity(
            allIds = ::gw2v2QuestsIds,
            byIds = ::gw2v2QuestsByIds,
            byPage = ::gw2v2QuestsByPage,
            getId = GW2v2Quest::id,
        )
        override val stories: EndpointEntity<GW2StoryId, GW2v2Story> = ef.entity(
            allIds = ::gw2v2StoriesIds,
            byIds = ::gw2v2StoriesByIds,
            byPage = ::gw2v2StoriesByPage,
            getId = GW2v2Story::id,
        )
        override val storiesSeasons: EndpointEntity<GW2StorySeasonId, GW2v2StorySeason> = ef.entity(
            allIds = ::gw2v2StoriesSeasonsIds,
            byIds = ::gw2v2StoriesSeasonsByIds,
            byPage = ::gw2v2StoriesSeasonsByPage,
            getId = GW2v2StorySeason::id,
        )
        override val currencies: EndpointEntity<GW2CurrencyId, GW2v2Currency> = ef.entity(
            allIds = ::gw2v2CurrenciesIds,
            byIds = ::gw2v2CurrenciesByIds,
            byPage = ::gw2v2CurrenciesByPage,
            getId = GW2v2Currency::id,
        )
        override val wizardsVaultListings: EndpointEntity<GW2WizardsVaultListingId, GW2v2WizardsVaultListing> =
            ef.entity(
                allIds = ::gw2v2WizardsVaultListingsIds,
                byIds = ::gw2v2WizardsVaultListingsByIds,
                byPage = ::gw2v2WizardsVaultListingsByPage,
                getId = GW2v2WizardsVaultListing::id,
            )
        override val wizardsVaultObjectives: EndpointEntity<GW2WizardsVaultObjectiveId, GW2v2WizardsVaultObjective> =
            ef.entity(
                allIds = ::gw2v2WizardsVaultObjectivesIds,
                byIds = ::gw2v2WizardsVaultObjectivesByIds,
                byPage = ::gw2v2WizardsVaultObjectivesByPage,
                getId = GW2v2WizardsVaultObjective::id,
            )
        override val emotes: EndpointEntity<GW2EmoteId, GW2v2Emote> = ef.entity(
            allIds = ::gw2v2EmotesIds,
            byIds = ::gw2v2EmotesByIds,
            byPage = ::gw2v2EmotesByPage,
            getId = GW2v2Emote::id,
        )
        override val gliders: EndpointEntity<GW2GliderId, GW2v2Glider> = ef.entity(
            allIds = ::gw2v2GlidersIds,
            byIds = ::gw2v2GlidersByIds,
            byPage = ::gw2v2GlidersByPage,
            getId = GW2v2Glider::id,
        )
        override val mailCarriers: EndpointEntity<GW2MailCarrierId, GW2v2Mailcarrier> = ef.entity(
            allIds = ::gw2v2MailcarriersIds,
            byIds = ::gw2v2MailcarriersByIds,
            byPage = ::gw2v2MailcarriersByPage,
            getId = GW2v2Mailcarrier::id,
        )
    }

    private inner class ApiMapInformationImpl : ApiMapInformation {
        override val continents: EndpointEntity<GW2ContinentId, ApiMapInformation.ApiContinent>
            get() = TODO()
        override val maps2: EndpointEntity<GW2MapId, GW2v2Map>
            get() = TODO()
    }

    private inner class ApiTradingPostImpl : ApiTradingPost {
        override val listings: EndpointEntity<GW2ItemId, GW2v2CommerceListing> = ef.entity(
            allIds = ::gw2v2CommerceListingsIds,
            byIds = ::gw2v2CommerceListingsByIds,
            byPage = ::gw2v2CommerceListingsByPage,
            getId = GW2v2CommerceListing::id,
        )
        override val prices: EndpointEntity<GW2ItemId, GW2v2CommercePrices> = ef.entity(
            allIds = ::gw2v2CommercePricesIds,
            byIds = ::gw2v2CommercePricesByIds,
            byPage = ::gw2v2CommercePricesByPage,
            getId = GW2v2CommercePrices::id,
        )
    }

    private inner class ApiTradingPostAuthenticatedImpl : ApiTradingPostAuthenticated {
        override val delivery: EndpointSimple<GW2v2CommerceDelivery> = ef.simple(::gw2v2CommerceDelivery)
        override val transactionsCurrentBuys: EndpointSimplePaged<GW2v2CommerceTransaction> = ef.simplePaged(
            byPage = { page, pageSize, configure ->
                gw2v2CommerceTransactionsByPage(
                    "current",
                    "buys",
                    page,
                    pageSize,
                    configure
                )
            },
        )
        override val transactionsCurrentSells: EndpointSimplePaged<GW2v2CommerceTransaction> = ef.simplePaged(
            byPage = { page, pageSize, configure ->
                gw2v2CommerceTransactionsByPage(
                    "current",
                    "sells",
                    page,
                    pageSize,
                    configure
                )
            },
        )
        override val transactionHistoryBuys: EndpointSimplePaged<GW2v2CommerceTransaction> = ef.simplePaged(
            byPage = { page, pageSize, configure ->
                gw2v2CommerceTransactionsByPage(
                    "history",
                    "buys",
                    page,
                    pageSize,
                    configure
                )
            },
        )
        override val transactionHistorySells: EndpointSimplePaged<GW2v2CommerceTransaction> = ef.simplePaged(
            byPage = { page, pageSize, configure ->
                gw2v2CommerceTransactionsByPage(
                    "history",
                    "sells",
                    page,
                    pageSize,
                    configure
                )
            },
        )
    }

    private inner class ApiAuthenticatedImpl : ApiAuthenticated {
        override val account: EndpointSimple<GW2v2Account> = ef.simple(::gw2v2Account)
        override val achievements: EndpointSimple<List<GW2v2AccountAchievement>> = ef.simple(::gw2v2AccountAchievements)
        override val bank: EndpointSimple<List<GW2v2AccountBankSlot?>> = ef.simple(::gw2v2AccountBank)
        override val dailyCrafting: EndpointSimple<List<String>> = ef.simple(::gw2v2AccountDailyCrafting)
        override val dungeons: EndpointSimple<List<String>> = ef.simple(::gw2v2AccountDungeons)
        override val dyes: EndpointSimple<List<GW2DyeId>> = ef.simple(::gw2v2AccountDyes)
        override val emotes: EndpointSimple<List<GW2EmoteId>> = ef.simple(::gw2v2AccountEmotes)
        override val finishers: EndpointSimple<List<GW2v2AccountFinisher>> = ef.simple(::gw2v2AccountFinishers)
        override val gliders: EndpointSimple<List<GW2GliderId>> = ef.simple(::gw2v2AccountGliders)
        override val homeCats: EndpointSimple<List<GW2HomeInstanceCatId>> = ef.simple(::gw2v2AccountHomeCats)
        override val homeNodes: EndpointSimple<List<GW2HomeInstanceNodeId>> = ef.simple(::gw2v2AccountHomeNodes)
        override val inventory: EndpointSimple<List<GW2v2AccountInventorySlot>> = ef.simple(::gw2v2AccountInventory)
        override val jadebots: EndpointSimple<List<GW2JadeBotId>> = ef.simple(::gw2v2AccountJadeBots)
        override val legendaryArmory: EndpointSimple<List<GW2v2AccountLegendaryArmoryUnlock>> =
            ef.simple(::gw2v2AccountLegendaryArmory)
        override val luck: EndpointSimple<List<GW2v2Luck>> = ef.simple(::gw2v2AccountLuck)
        override val mailCarriers: EndpointSimple<List<GW2MailCarrierId>> = ef.simple(::gw2v2AccountMailcarriers)
        override val mapChests: EndpointSimple<List<String>> = ef.simple(::gw2v2AccountMapChests)
        override val masteries: EndpointSimple<List<GW2v2AccountMastery>> = ef.simple(::gw2v2AccountMasteries)
        override val masteryPoints: EndpointSimple<GW2v2AccountMasteryPoint> = ef.simple(::gw2v2AccountMasteryPoints)
        override val materials: EndpointSimple<List<GW2v2AccountMaterial>> = ef.simple(::gw2v2AccountMaterials)
        override val minis: EndpointSimple<List<GW2MiniId>> = ef.simple(::gw2v2AccountMinis)
        override val mountsSkins: EndpointSimple<List<GW2MountSkinId>> = ef.simple(::gw2v2AccountMountsSkins)
        override val mountsTypes: EndpointSimple<List<GW2MountTypeId>> = ef.simple(::gw2v2AccountMountsTypes)
        override val novelties: EndpointSimple<List<GW2NoveltyId>> = ef.simple(::gw2v2AccountNovelties)
        override val outfits: EndpointSimple<List<GW2OutfitId>> = ef.simple(::gw2v2AccountOutfits)
        override val pvpHeroes: EndpointSimple<List<GW2PvpHeroId>> = ef.simple(::gw2v2AccountPvPHeroes)
        override val raids: EndpointSimple<List<GW2RaidId>> = ef.simple(::gw2v2AccountRaids)
        override val recipes: EndpointSimple<List<GW2RecipeId>> = ef.simple(::gw2v2AccountRecipes)
        override val skiffs: EndpointSimple<List<GW2SkiffId>> = ef.simple(::gw2v2AccountSkiffs)
        override val skins: EndpointSimple<List<GW2SkinId>> = ef.simple(::gw2v2AccountSkins)
        override val titles: EndpointSimple<List<GW2TitleId>> = ef.simple(::gw2v2AccountTitles)
        override val wallet: EndpointSimple<List<GW2v2AccountWalletCurrency>> = ef.simple(::gw2v2AccountWallet)
        override val wizardsVaultDaily: EndpointSimple<GW2v2AccountWizardsVaultDaily> =
            ef.simple(::gw2v2AccountWizardsVaultDaily)
        override val wizardsVaultListings: EndpointSimple<List<GW2v2AccountWizardsVaultListing>> =
            ef.simple(::gw2v2AccountWizardsVaultListings)
        override val wizardsVaultSpecial: EndpointSimple<GW2v2AccountWizardsVaultSpecial> =
            ef.simple(::gw2v2AccountWizardsVaultSpecial)
        override val wizardsVaultWeekly: EndpointSimple<GW2v2AccountWizardsVaultWeekly> =
            ef.simple(::gw2v2AccountWizardsVaultWeekly)
        override val worldBosses: EndpointSimple<List<GW2WorldBossId>> = ef.simple(::gw2v2AccountWorldBosses)
        override val tokenInfo: EndpointSimple<GW2v2TokenInfo> = ef.simple(::gw2v2TokenInfo)
        override val characters: EndpointEntity<String, GW2v2Character> = ef.entity(
            allIds = ::gw2v2CharactersIds,
            byIds = ::gw2v2CharactersByIds,
            byPage = ::gw2v2CharactersByPage,
            getId = GW2v2Character::name,
        )
    }

}