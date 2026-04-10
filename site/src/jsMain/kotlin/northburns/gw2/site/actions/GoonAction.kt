package northburns.gw2.site.actions

import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementGroupId
import com.gw2tb.gw2api.types.v2.GW2v2Achievement
import com.gw2tb.gw2api.types.v2.GW2v2AchievementCategory
import com.gw2tb.gw2api.types.v2.GW2v2AchievementGroups
import com.gw2tb.gw2api.types.v2.GW2v2CommercePrices
import com.gw2tb.gw2api.types.v2.GW2v2Item
import com.gw2tb.gw2api.types.v2.GW2v2Mini
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Skill
import com.gw2tb.gw2api.types.v2.GW2v2Skin
import com.gw2tb.gw2api.types.v2.GW2v2Specialization
import com.gw2tb.gw2api.types.v2.GW2v2Trait
import io.kvision.redux.RAction
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotMeta
import northburns.gw2.site.app.AccountData
import northburns.gw2.site.app.FatalGoonError
import northburns.gw2.site.app.GoonRoute
import northburns.gw2.site.app.GoonState

sealed interface GoonAction : RAction

sealed interface TlsAction : GoonAction
data class TlsLoggedIn(val account: AccountData) : TlsAction

sealed interface CommonActions : GoonAction

data class FatalError(val error: FatalGoonError): CommonActions

data class ResetState(val state: GoonState) : CommonActions

data class NavigationAction(val route: GoonRoute) : CommonActions

/**
 * Updates the snapshots map!
 */
data class PartyChangedAction(val partyPlayerIds: List<PlayerId>) : CommonActions

sealed interface ApiDataAction : GoonAction

sealed interface FetchApiDataAction : ApiDataAction
data class FetchItemsSuccess(val items: Collection<GW2v2Item>) : FetchApiDataAction
data class FetchMinisSuccess(val minis: Collection<GW2v2Mini>) : FetchApiDataAction
data class FetchSkinsSuccess(val skins: Collection<GW2v2Skin>) : FetchApiDataAction
data class FetchPricesSuccess(val prices: Collection<GW2v2CommercePrices>) : FetchApiDataAction
data class FetchProfessionsSuccess(val professions: Collection<GW2v2Profession>) : FetchApiDataAction
data class FetchSpecializationsSuccess(val specializations: Collection<GW2v2Specialization>) : FetchApiDataAction
data class FetchTraitsSuccess(val traits: Collection<GW2v2Trait>) : FetchApiDataAction
data class FetchSkillsSuccess(val skills: Collection<GW2v2Skill>) : FetchApiDataAction
data class FetchAchievementsSuccess(val achievements: Collection<GW2v2Achievement>) : FetchApiDataAction
data class FetchAchievementCategoriesIdsSuccess(val ids: Collection<GW2AchievementCategoryId>) : FetchApiDataAction
data class FetchAchievementCategoriesSuccess(val categories: Collection<GW2v2AchievementCategory>) : FetchApiDataAction
data class FetchAchievementGroupsIdsSuccess(val ids: Collection<GW2AchievementGroupId>) : FetchApiDataAction
data class FetchAchievementGroupsSuccess(val groups: Collection<GW2v2AchievementGroups>) : FetchApiDataAction


data class FetchSnapshotIdsSuccess(
    val playerId: PlayerId,
    val snapshotIds: Map<AccountSnapshotId, AccountSnapshotMeta>
) : ApiDataAction

data class FetchSnapshotSuccess(val playerId: PlayerId, val snapshot: AccountSnapshot) : ApiDataAction

