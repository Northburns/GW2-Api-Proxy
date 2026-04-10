package northburns.gw2.client2

import com.gw2tb.gw2api.client.AuthenticationStrategy
import com.gw2tb.gw2api.client.RequestConfigurer
import com.gw2tb.gw2api.client.RequestTemplate
import com.gw2tb.gw2api.client.UnsafeQueryAuthentication
import com.gw2tb.gw2api.client.getOrThrow
import com.gw2tb.gw2api.client.ktor.buildGw2ApiClient
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
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsCategoriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsCategoriesIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsGroupsByIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsGroupsIds
import com.gw2tb.gw2api.client.v2.gw2v2AchievementsIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryAnswersByIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryAnswersIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryQuestionsByIds
import com.gw2tb.gw2api.client.v2.gw2v2BackstoryQuestionsIds
import com.gw2tb.gw2api.client.v2.gw2v2Build
import com.gw2tb.gw2api.client.v2.gw2v2CharactersByIds
import com.gw2tb.gw2api.client.v2.gw2v2CharactersIds
import com.gw2tb.gw2api.client.v2.gw2v2ColorsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ColorsIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceDelivery
import com.gw2tb.gw2api.client.v2.gw2v2CommerceListingsByIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceListingsIds
import com.gw2tb.gw2api.client.v2.gw2v2CommercePricesByIds
import com.gw2tb.gw2api.client.v2.gw2v2CommercePricesIds
import com.gw2tb.gw2api.client.v2.gw2v2CommerceTransactionsByPage
import com.gw2tb.gw2api.client.v2.gw2v2CurrenciesByIds
import com.gw2tb.gw2api.client.v2.gw2v2CurrenciesIds
import com.gw2tb.gw2api.client.v2.gw2v2DailyCraftingByIds
import com.gw2tb.gw2api.client.v2.gw2v2DailyCraftingIds
import com.gw2tb.gw2api.client.v2.gw2v2DungeonsByIds
import com.gw2tb.gw2api.client.v2.gw2v2DungeonsIds
import com.gw2tb.gw2api.client.v2.gw2v2EmotesByIds
import com.gw2tb.gw2api.client.v2.gw2v2EmotesIds
import com.gw2tb.gw2api.client.v2.gw2v2FinishersByIds
import com.gw2tb.gw2api.client.v2.gw2v2FinishersIds
import com.gw2tb.gw2api.client.v2.gw2v2GlidersByIds
import com.gw2tb.gw2api.client.v2.gw2v2GlidersIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeCatsByIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeCatsIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeNodesByIds
import com.gw2tb.gw2api.client.v2.gw2v2HomeNodesIds
import com.gw2tb.gw2api.client.v2.gw2v2ItemsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ItemsIds
import com.gw2tb.gw2api.client.v2.gw2v2JadeBotsByIds
import com.gw2tb.gw2api.client.v2.gw2v2JadeBotsIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendaryArmoryByIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendaryArmoryIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendsByIds
import com.gw2tb.gw2api.client.v2.gw2v2LegendsIds
import com.gw2tb.gw2api.client.v2.gw2v2MailcarriersByIds
import com.gw2tb.gw2api.client.v2.gw2v2MailcarriersIds
import com.gw2tb.gw2api.client.v2.gw2v2MapChestsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MapChestsIds
import com.gw2tb.gw2api.client.v2.gw2v2MasteriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2MasteriesIds
import com.gw2tb.gw2api.client.v2.gw2v2MaterialsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MaterialsIds
import com.gw2tb.gw2api.client.v2.gw2v2MinisByIds
import com.gw2tb.gw2api.client.v2.gw2v2MinisIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsSkinsByIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsSkinsIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsTypesByIds
import com.gw2tb.gw2api.client.v2.gw2v2MountsTypesIds
import com.gw2tb.gw2api.client.v2.gw2v2NoveltiesByIds
import com.gw2tb.gw2api.client.v2.gw2v2NoveltiesIds
import com.gw2tb.gw2api.client.v2.gw2v2OutfitsByIds
import com.gw2tb.gw2api.client.v2.gw2v2OutfitsIds
import com.gw2tb.gw2api.client.v2.gw2v2PetsByIds
import com.gw2tb.gw2api.client.v2.gw2v2PetsIds
import com.gw2tb.gw2api.client.v2.gw2v2ProfessionsByIds
import com.gw2tb.gw2api.client.v2.gw2v2ProfessionsIds
import com.gw2tb.gw2api.client.v2.gw2v2QuaggansByIds
import com.gw2tb.gw2api.client.v2.gw2v2QuaggansIds
import com.gw2tb.gw2api.client.v2.gw2v2QuestsByIds
import com.gw2tb.gw2api.client.v2.gw2v2QuestsIds
import com.gw2tb.gw2api.client.v2.gw2v2RacesByIds
import com.gw2tb.gw2api.client.v2.gw2v2RacesIds
import com.gw2tb.gw2api.client.v2.gw2v2RaidsByIds
import com.gw2tb.gw2api.client.v2.gw2v2RaidsIds
import com.gw2tb.gw2api.client.v2.gw2v2RecipesByIds
import com.gw2tb.gw2api.client.v2.gw2v2RecipesIds
import com.gw2tb.gw2api.client.v2.gw2v2SkiffsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkiffsIds
import com.gw2tb.gw2api.client.v2.gw2v2SkillsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkillsIds
import com.gw2tb.gw2api.client.v2.gw2v2SkinsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SkinsIds
import com.gw2tb.gw2api.client.v2.gw2v2SpecializationsByIds
import com.gw2tb.gw2api.client.v2.gw2v2SpecializationsIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesByIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesSeasonsByIds
import com.gw2tb.gw2api.client.v2.gw2v2StoriesSeasonsIds
import com.gw2tb.gw2api.client.v2.gw2v2TitlesByIds
import com.gw2tb.gw2api.client.v2.gw2v2TitlesIds
import com.gw2tb.gw2api.client.v2.gw2v2TokenInfo
import com.gw2tb.gw2api.client.v2.gw2v2TraitsByIds
import com.gw2tb.gw2api.client.v2.gw2v2TraitsIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultListingsByIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultListingsIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultObjectivesByIds
import com.gw2tb.gw2api.client.v2.gw2v2WizardsVaultObjectivesIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldBossesByIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldBossesIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldsByIds
import com.gw2tb.gw2api.client.v2.gw2v2WorldsIds
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2ApiClientWrapper
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.cache.KottageStorage
import northburns.gw2.client2.EntityServiceImpl.Companion.entityService
import northburns.gw2.client2.MyGw2Client2.SimpleService
import northburns.gw2.client2.MyGw2Client2Impl.KottageInstanceName.AUTH
import northburns.gw2.client2.MyGw2Client2Impl.KottageInstanceName.GENERAL
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

/**
 * This version of the client uses ???
 */
class MyGw2Client2Impl(
    val scope: CoroutineScope,
    val playerData: PlayerDataProvider,
) : MyGw2Client2 {

    // ========================================================================
    //    Initialize private properties at the start of the class
    // ========================================================================

    private val kottages: MutableMap<PlayerId?, MutableMap<KottageInstanceName, MutableMap<String, KottageStorage>>> =
        mutableMapOf()
    private val auths = mutableMapOf<PlayerId, AuthenticatedImpl>()
    private val authTps = mutableMapOf<PlayerId, TradingPostAuthenticatedImpl>()

    /**
     * Use a _single_ client to ensure the ratelimiting works as intended.
     */
    private val client = Gw2ApiClientWrapper(buildGw2ApiClient {
        json = Gw2Json.json

        // To make browser not send preflight requests - which are explicitly not supported by the API
        // TODO do this only if targeting browserJS
        @OptIn(UnsafeQueryAuthentication::class)
        authenticationStrategy = AuthenticationStrategy.QUERY
    })

    // ========================================================================
    //    Public overrides
    // ========================================================================

    override val general = GeneralImpl()
    override val tp = TradingPostImpl()

    override suspend fun auth(playerId: PlayerId) =
        auths.getOrPut(playerId) { AuthenticatedImpl(playerId) }

    override suspend fun authTp(playerId: PlayerId) =
        authTps.getOrPut(playerId) { TradingPostAuthenticatedImpl(playerId) }

    // ========================================================================
    //     API Implementations
    // ========================================================================

    internal interface ApiImpl {
        //val player: PlayerData?
        val playerId: PlayerId?
        val requestConfigurer: RequestConfigurer?
        val kottageForSimple: KottageStorage
    }

    inner class TradingPostAuthenticatedImpl(
        override val playerId: PlayerId,
    ) : MyGw2Client2.TradingPostAuthenticated, ApiImpl {
        override val requestConfigurer = RequestConfigurer { withAPIKey(playerData.getPlayerKey(playerId).key) }
        override val kottageForSimple: KottageStorage = getKottageStorage("_simple-tp-auth")

        override val delivery by simple(::gw2v2CommerceDelivery)
        override val transactionsCurrentBuys by simplePaged { page: Long, pageSize: Long?, configure: RequestConfigurer? ->
            gw2v2CommerceTransactionsByPage(
                "current",
                "buys",
                page,
                pageSize,
                configure
            )
        }
        override val transactionsCurrentSells by simplePaged { page: Long, pageSize: Long?, configure: RequestConfigurer? ->
            gw2v2CommerceTransactionsByPage(
                "current",
                "sells",
                page,
                pageSize,
                configure
            )
        }
        override val transactionHistoryBuys by simplePaged { page: Long, pageSize: Long?, configure: RequestConfigurer? ->
            gw2v2CommerceTransactionsByPage(
                "history",
                "buys",
                page,
                pageSize,
                configure
            )
        }
        override val transactionHistorySells by simplePaged { page: Long, pageSize: Long?, configure: RequestConfigurer? ->
            gw2v2CommerceTransactionsByPage(
                "history",
                "sells",
                page,
                pageSize,
                configure
            )
        }

    }

    inner class AuthenticatedImpl(
        override val playerId: PlayerId,
    ) : MyGw2Client2.Authenticated, ApiImpl {
        override val requestConfigurer = RequestConfigurer { withAPIKey(playerData.getPlayerKey(playerId).key) }
        override val kottageForSimple: KottageStorage = getKottageStorage("_simple-auth")

        override val characters by es(::gw2v2CharactersIds, ::gw2v2CharactersByIds) { it.name }
        override val account by simple(::gw2v2Account)
        override val achievements by simple(::gw2v2AccountAchievements)
        override val bank by simple(::gw2v2AccountBank)
        override val dailyCrafting by simple(::gw2v2AccountDailyCrafting)
        override val dungeons by simple(::gw2v2AccountDungeons)
        override val dyes by simple(::gw2v2AccountDyes)
        override val emotes by simple(::gw2v2AccountEmotes)
        override val finishers by simple(::gw2v2AccountFinishers)
        override val gliders by simple(::gw2v2AccountGliders)
        override val homeCats by simple(::gw2v2AccountHomeCats)
        override val homeNodes by simple(::gw2v2AccountHomeNodes)
        override val inventory by simple(::gw2v2AccountInventory)
        override val jadebots by simple(::gw2v2AccountJadeBots)
        override val legendaryArmory by simple(::gw2v2AccountLegendaryArmory)
        override val luck by simple(::gw2v2AccountLuck)
        override val mailCarriers by simple(::gw2v2AccountMailcarriers)
        override val mapChests by simple(::gw2v2AccountMapChests)
        override val masteries by simple(::gw2v2AccountMasteries)
        override val masteryPoints by simple(::gw2v2AccountMasteryPoints)
        override val materials by simple(::gw2v2AccountMaterials)
        override val minis by simple(::gw2v2AccountMinis)
        override val mountsSkins by simple(::gw2v2AccountMountsSkins)
        override val mountsTypes by simple(::gw2v2AccountMountsTypes)
        override val novelties by simple(::gw2v2AccountNovelties)
        override val outfits by simple(::gw2v2AccountOutfits)
        override val pvpHeroes by simple(::gw2v2AccountPvPHeroes)
        override val raids by simple(::gw2v2AccountRaids)
        override val recipes by simple(::gw2v2AccountRecipes)
        override val skiffs by simple(::gw2v2AccountSkiffs)
        override val skins by simple(::gw2v2AccountSkins)
        override val titles by simple(::gw2v2AccountTitles)
        override val wallet by simple(::gw2v2AccountWallet)
        override val wizardsVaultDaily by simple(::gw2v2AccountWizardsVaultDaily)
        override val wizardsVaultListings by simple(::gw2v2AccountWizardsVaultListings)
        override val wizardsVaultSpecial by simple(::gw2v2AccountWizardsVaultSpecial)
        override val wizardsVaultWeekly by simple(::gw2v2AccountWizardsVaultWeekly)
        override val worldBosses by simple(::gw2v2AccountWorldBosses)
        override val tokenInfo by simple(::gw2v2TokenInfo)


    }

    inner class GeneralImpl : MyGw2Client2.General, ApiImpl {
        override val playerId: PlayerId? = null
        override val requestConfigurer: RequestConfigurer? = null
        override val kottageForSimple: KottageStorage = getKottageStorage("_simple")

        override val build by simple(::gw2v2Build)
        override val items by es(::gw2v2ItemsIds, ::gw2v2ItemsByIds) { it.id }
        override val achievements by es(::gw2v2AchievementsIds, ::gw2v2AchievementsByIds) { it.id }
        override val achievementsCategories by es(
            ::gw2v2AchievementsCategoriesIds,
            ::gw2v2AchievementsCategoriesByIds
        ) { it.id }
        override val achievementsGroups by es(::gw2v2AchievementsGroupsIds, ::gw2v2AchievementsGroupsByIds) { it.id }
        override val mapChests by es(::gw2v2MapChestsIds, ::gw2v2MapChestsByIds) { it.id }
        override val worldBosses by es(::gw2v2WorldBossesIds, ::gw2v2WorldBossesByIds) { it.id }
        override val jadeBots by es(::gw2v2JadeBotsIds, ::gw2v2JadeBotsByIds) { it.id }
        override val legendaryArmory by es(::gw2v2LegendaryArmoryIds, ::gw2v2LegendaryArmoryByIds) { it.id }
        override val legends by es(::gw2v2LegendsIds, ::gw2v2LegendsByIds) { it.id }
        override val masteries by es(::gw2v2MasteriesIds, ::gw2v2MasteriesByIds) { it.id }
        override val minis by es(::gw2v2MinisIds, ::gw2v2MinisByIds) { it.id }
        override val mountsSkins by es(::gw2v2MountsSkinsIds, ::gw2v2MountsSkinsByIds) { it.id }
        override val mountsTypes by es(::gw2v2MountsTypesIds, ::gw2v2MountsTypesByIds) { it.id }
        override val outfits by es(::gw2v2OutfitsIds, ::gw2v2OutfitsByIds) { it.id }
        override val pets by es(::gw2v2PetsIds, ::gw2v2PetsByIds) { it.id }
        override val professions by es(::gw2v2ProfessionsIds, ::gw2v2ProfessionsByIds) { it.id }
        override val races by es(::gw2v2RacesIds, ::gw2v2RacesByIds) { it.id }
        override val skiffs by es(::gw2v2SkiffsIds, ::gw2v2SkiffsByIds) { it.id }
        override val skills by es(::gw2v2SkillsIds, ::gw2v2SkillsByIds) { it.id }
        override val specializations by es(::gw2v2SpecializationsIds, ::gw2v2SpecializationsByIds) { it.id }
        override val traits by es(::gw2v2TraitsIds, ::gw2v2TraitsByIds) { it.id }
        override val homeCats by es(::gw2v2HomeCatsIds, ::gw2v2HomeCatsByIds) { it.id }
        override val homeNodes by es(::gw2v2HomeNodesIds, ::gw2v2HomeNodesByIds) { it.id }
        override val finishers by es(::gw2v2FinishersIds, ::gw2v2FinishersByIds) { it.id }
        override val materials by es(::gw2v2MaterialsIds, ::gw2v2MaterialsByIds) { it.id }
        override val recipes by es(::gw2v2RecipesIds, ::gw2v2RecipesByIds) { it.id }
        override val skins by es(::gw2v2SkinsIds, ::gw2v2SkinsByIds) { it.id }
        override val colors by es(::gw2v2ColorsIds, ::gw2v2ColorsByIds) { it.id }
        override val dailyCrafting by es(::gw2v2DailyCraftingIds, ::gw2v2DailyCraftingByIds) { it.id }
        override val dungeons by es(::gw2v2DungeonsIds, ::gw2v2DungeonsByIds) { it.id }
        override val novelties by es(::gw2v2NoveltiesIds, ::gw2v2NoveltiesByIds) { it.id }
        override val quaggans by es(::gw2v2QuaggansIds, ::gw2v2QuaggansByIds) { it.id }
        override val raids by es(::gw2v2RaidsIds, ::gw2v2RaidsByIds) { it.id }
        override val titles by es(::gw2v2TitlesIds, ::gw2v2TitlesByIds) { it.id }
        override val worlds by es(::gw2v2WorldsIds, ::gw2v2WorldsByIds) { it.id }
        override val backstoryAnswers by es(::gw2v2BackstoryAnswersIds, ::gw2v2BackstoryAnswersByIds) { it.id }
        override val backstoryQuestions by es(::gw2v2BackstoryQuestionsIds, ::gw2v2BackstoryQuestionsByIds) { it.id }
        override val quests by es(::gw2v2QuestsIds, ::gw2v2QuestsByIds) { it.id }
        override val stories by es(::gw2v2StoriesIds, ::gw2v2StoriesByIds) { it.id }
        override val storiesSeasons by es(::gw2v2StoriesSeasonsIds, ::gw2v2StoriesSeasonsByIds) { it.id }
        override val currencies by es(::gw2v2CurrenciesIds, ::gw2v2CurrenciesByIds) { it.id }
        override val wizardsVaultListings by es(
            ::gw2v2WizardsVaultListingsIds,
            ::gw2v2WizardsVaultListingsByIds
        ) { it.id }
        override val wizardsVaultObjectives by es(
            ::gw2v2WizardsVaultObjectivesIds,
            ::gw2v2WizardsVaultObjectivesByIds
        ) { it.id }
        override val emotes by es(::gw2v2EmotesIds, ::gw2v2EmotesByIds) { it.id }
        override val gliders by es(::gw2v2GlidersIds, ::gw2v2GlidersByIds) { it.id }
        override val mailCarriers by es(::gw2v2MailcarriersIds, ::gw2v2MailcarriersByIds) { it.id }
    }

    inner class TradingPostImpl : MyGw2Client2.TradingPost, ApiImpl {
        override val playerId: PlayerId? = null
        override val requestConfigurer: RequestConfigurer? = null
        override val kottageForSimple: KottageStorage = getKottageStorage("_simple-tp")
        override val listings by es(::gw2v2CommerceListingsIds, ::gw2v2CommerceListingsByIds) { it.id }
        override val prices by es(::gw2v2CommercePricesIds, ::gw2v2CommercePricesByIds) { it.id }

    }

// ========================================================================
//     Private helpers/utilities
// ========================================================================

    private inline fun <reified V : Any> simple(
        noinline get: (configure: RequestConfigurer?) -> RequestTemplate<V>,
    ): ReadOnlyProperty<ApiImpl, SimpleService<V>> {
        return ReadOnlyProperty { thisRef: ApiImpl, property: KProperty<*> ->
            SimpleServiceImpl(
                kottage = thisRef.kottageForSimple,
                key = property.name,
                serializer = Gw2Json.json.serializersModule.serializer<V>(),
                execute = thisRef.exe(get),
            )
        }
    }

    private inline fun <reified V : Any> simplePaged(
        noinline byPage: (page: Long, pageSize: Long?, configure: RequestConfigurer?) -> RequestTemplate<List<V>>,
    ): ReadOnlyProperty<ApiImpl, SimpleService<List<V>>> {
        return ReadOnlyProperty { thisRef: ApiImpl, _: KProperty<*> ->
            SimpleService<List<V>> {
                val values = mutableListOf<V>()

                var page = 0L
                do {
                    val request = byPage(page, PAGE_SIZE, thisRef.requestConfigurer)
                    val response = client.executeAsync(request)
                    values.addAll(response.decodingResult.getOrThrow())
                    val pageTotal = response.headers["X-Page-Total"]?.single()?.toInt() ?: 1
                    page++
                } while (page < pageTotal)

                values
            }
        }
    }

    private class SimpleServiceImpl<V : Any>(
        private val kottage: KottageStorage,
        private val key: String,
        private val serializer: KSerializer<V>,
        private val execute: suspend () -> V,
    ) : SimpleService<V> {
        override suspend fun invoke(): V {
            return kottage // Get cached value
                .get(key)?.json?.let {
                    Gw2Json.json.decodeFromString(serializer, it)
                }  // Or else calculate & store
                ?: execute().also {
                    kottage.set(key, Gw2Json.json.encodeToString(serializer, it))
                }
        }
    }

    private inline fun <reified K : Any, reified V : Any> es(
        noinline ids: (configure: RequestConfigurer?) -> RequestTemplate<List<K>>,
        noinline items: (keys: List<K>, configure: RequestConfigurer?) -> RequestTemplate<List<V>>,
        noinline key: (V) -> K,
    ): ReadOnlyProperty<ApiImpl, EntityServiceImpl<K, V>> {
        return ReadOnlyProperty { thisRef: ApiImpl, property: KProperty<*> ->
            scope.entityService<K, V>(
                fetchIds = thisRef.exe(ids),
                fetch = thisRef.exe(items),
                kottage = thisRef.getKottageStorage(property.name),
                keyOf = key,
            )
        }
    }


    private fun <V> ApiImpl.exe(
        tpl: (configure: RequestConfigurer?) -> RequestTemplate<V>,
    ): suspend () -> V = {
        client.executeAsync(tpl(requestConfigurer)).decodingResult.getOrThrow()
    }


    private fun <K : Any, V : Any> ApiImpl.exe(
        tpl: (keys: List<K>, configure: RequestConfigurer?) -> RequestTemplate<List<V>>,
    ): suspend (keys: List<K>) -> Collection<V> = { keys: List<K> ->
        client.executeAsync(tpl(keys, requestConfigurer)).decodingResult.getOrThrow()
    }


    private enum class KottageInstanceName(val v: String, val ttl: Duration) {
        GENERAL("general", 30.days),
        AUTH("auth", 4.minutes),
    }

    private fun ApiImpl.getKottageStorage(name: String): KottageStorage {
        return getKottageStorage(this.playerId, if (playerId != null) AUTH else GENERAL, name)
    }

    private fun getKottageStorage(
        playerId: PlayerId?,
        kottageInstanceName: KottageInstanceName,
        cacheName: String
    ): KottageStorage {
        if (kottageInstanceName == AUTH) requireNotNull(playerId) { "AUTH storage: playerId required" }
        else require(playerId == null) { "Not AUTH storage: playerId prohibited" }

        /*
          Kottage(
                    name = "cache_${kottageInstanceName.v}" + if (player == null) "" else "_${player.apiKey.key}",
                    directoryPath = MyCacheSettings.cacheDir(),
                    environment = kottageEnvironment,
                    scope = scope,
                    json = Gw2Json.json,
                )
         */
        return kottages
            .getOrPut(playerId) { mutableMapOf() }
            .getOrPut(kottageInstanceName) { mutableMapOf() }
            .let { storages ->
                storages.getOrPut(cacheName) {
                    KottageStorage(
                        filename = "cache_${kottageInstanceName.v}" + if (playerId == null) "" else "_${playerId.id}",
                        cacheName = cacheName,
                        defaultTtl = kottageInstanceName.ttl,
                    )
                }
            }
    }


    companion object {
        private const val PAGE_SIZE = 200L
    }
}
