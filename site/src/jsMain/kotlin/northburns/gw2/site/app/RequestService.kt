package northburns.gw2.site.app

import kotlinx.coroutines.CoroutineScope
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.client.myclient.utils.ChunkedChannel
import northburns.gw2.client2.MyGw2Client2
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.actions.FetchAchievementCategoriesIdsSuccess
import northburns.gw2.site.actions.FetchAchievementCategoriesSuccess
import northburns.gw2.site.actions.FetchAchievementGroupsIdsSuccess
import northburns.gw2.site.actions.FetchAchievementGroupsSuccess
import northburns.gw2.site.actions.FetchAchievementsSuccess
import northburns.gw2.site.actions.FetchItemsSuccess
import northburns.gw2.site.actions.FetchMinisSuccess
import northburns.gw2.site.actions.FetchPricesSuccess
import northburns.gw2.site.actions.FetchProfessionsSuccess
import northburns.gw2.site.actions.FetchSkillsSuccess
import northburns.gw2.site.actions.FetchSkinsSuccess
import northburns.gw2.site.actions.FetchSpecializationsSuccess
import northburns.gw2.site.actions.FetchTraitsSuccess

/**
 * You can do non-suspending requests for data with this. These requests are collected every second to batch operations.
 * The implementations do the work and fire corresponding actions.
 */
class RequestService(
    scope: CoroutineScope,
    private val client: MyGw2Client2,
) {
    private val log = GoonLog["RequestService"]

    val items = ChunkedChannel(scope, "RequestService_items") { ids ->
        val items = client.general.items.getMany(ids)
        goonStore.dispatch(FetchItemsSuccess(items))
    }
    val minis = ChunkedChannel(scope, "RequestService_minis") { ids ->
        val items = client.general.minis.getMany(ids)
        goonStore.dispatch(FetchMinisSuccess(items))
    }
    val skins = ChunkedChannel(scope, "RequestService_skins") { ids ->
        val items = client.general.skins.getMany(ids)
        goonStore.dispatch(FetchSkinsSuccess(items))
    }
    val tradingpostPrices = ChunkedChannel(scope, "RequestService_tradingpostPrices") { ids ->
        val items = client.tp.prices.getMany(ids)
        goonStore.dispatch(FetchPricesSuccess(items))
    }
    val professions = ChunkedChannel(scope, "RequestService_professions") { ids ->
        val items = client.general.professions.getMany(ids)
        goonStore.dispatch(FetchProfessionsSuccess(items))
    }
    val specializations = ChunkedChannel(scope, "RequestService_specializations") { ids ->
        val items = client.general.specializations.getMany(ids)
        goonStore.dispatch(FetchSpecializationsSuccess(items))
    }
    val traits = ChunkedChannel(scope, "RequestService_traits") { ids ->
        val items = client.general.traits.getMany(ids)
        goonStore.dispatch(FetchTraitsSuccess(items))
    }
    val skills = ChunkedChannel(scope, "RequestService_skills") { ids ->
        val items = client.general.skills.getMany(ids)
        goonStore.dispatch(FetchSkillsSuccess(items))
    }
    val achievements = ChunkedChannel(scope, "RequestService_achievements") { ids ->
        val items = client.general.achievements.getMany(ids)
        goonStore.dispatch(FetchAchievementsSuccess(items))
    }
    val achievementCategoriesIds = ChunkedChannel<Unit>(scope, "RequestService_achievementCategoriesIds") { _ ->
        val ids = client.general.achievementsCategories.getIds()
        goonStore.dispatch(FetchAchievementCategoriesIdsSuccess(ids))
    }
    val achievementCategories = ChunkedChannel(scope, "RequestService_achievementCategories") { ids ->
        val items = client.general.achievementsCategories.getMany(ids)
        goonStore.dispatch(FetchAchievementCategoriesSuccess(items))
    }
    val achievementGroupsIds = ChunkedChannel<Unit>(scope, "RequestService_achievementGroupsIds") { _ ->
        val ids = client.general.achievementsGroups.getIds()
        goonStore.dispatch(FetchAchievementGroupsIdsSuccess(ids))
    }
    val achievementGroups = ChunkedChannel(scope, "RequestService_achievementGroups") { ids ->
        val items = client.general.achievementsGroups.getMany(ids)
        goonStore.dispatch(FetchAchievementGroupsSuccess(items))
    }

    val achievementCategoriesAll = ChunkedChannel<Unit>(scope, "RequestService_achievementCategoriesAll") { _ ->
        val items = client.general.achievementsCategories.getAll().values
        goonStore.dispatch(FetchAchievementCategoriesSuccess(items))
    }
    val achievementGroupsAll = ChunkedChannel<Unit>(scope, "RequestService_achievementGroupsAll") { _ ->
        val items = client.general.achievementsGroups.getAll().values
        goonStore.dispatch(FetchAchievementGroupsSuccess(items))
    }

}