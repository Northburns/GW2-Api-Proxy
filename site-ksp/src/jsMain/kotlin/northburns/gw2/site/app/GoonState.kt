@file:UseSerializers(
    EitherSerializer::class,
    IorSerializer::class,
    OptionSerializer::class,
    NonEmptyListSerializer::class,
    NonEmptySetSerializer::class
)

package northburns.gw2.site.app

import arrow.core.serialization.EitherSerializer
import arrow.core.serialization.IorSerializer
import arrow.core.serialization.NonEmptyListSerializer
import arrow.core.serialization.NonEmptySetSerializer
import arrow.core.serialization.OptionSerializer
import arrow.optics.optics
import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementGroupId
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.GW2MiniId
import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2SkillId
import com.gw2tb.gw2api.types.GW2SkinId
import com.gw2tb.gw2api.types.GW2SpecializationId
import com.gw2tb.gw2api.types.GW2TraitId
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.data.AchievementsCalc
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotId
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.AccountSnapshotMeta
import northburns.gw2.site.app.GoonPath.SPECIAL_HOME

@Serializable
@optics
data class GoonState(
    val account: AccountData? = null,

    val loading: Boolean = false,
    val currentView: GoonRoute = GoonRoute(SPECIAL_HOME),

    /**
     * Global account snapshot, "current snapshot"
     */
    val snapshots: SnapshotsContainer = SnapshotsContainer(),

    /**
     * In-memory data from API
     */
    val api: ApiData = ApiData(),

    /**
     * Known snapshot ids and meta information.
     * This is the GW2Efficiency snapshot "smooching"
     */
    val snapshotIds: Map<AccountSnapshotId, AccountSnapshotMeta> = mapOf(),


    val fatalError: FatalGoonError? = null,
    val errors: List<String> = emptyList(),

    // View states

    val sessiontracker: SessionTrackerState = SessionTrackerState(),
) {
    val loggedIn: Boolean = account != null
    val players: List<PlayerId>? by lazy { account?.party?.map { it.id } }

    @Serializable
    @optics
    data class ApiData(
        val items: Map<GW2ItemId, GW2v2Item> = emptyMap(),
        val minis: Map<GW2MiniId, GW2v2Mini> = emptyMap(),
        val skins: Map<GW2SkinId, GW2v2Skin> = emptyMap(),
        val prices: Map<GW2ItemId, GW2v2CommercePrices> = emptyMap(),
        val professions: Map<GW2ProfessionId, GW2v2Profession> = emptyMap(),
        val specializations: Map<GW2SpecializationId, GW2v2Specialization> = emptyMap(),
        val traits: Map<GW2TraitId, GW2v2Trait> = emptyMap(),
        val skills: Map<GW2SkillId, GW2v2Skill> = emptyMap(),
        val achievements: Map<GW2AchievementId, GW2v2Achievement> = emptyMap(),
        val achievementCategoriesIds: Set<GW2AchievementCategoryId>? = null,
        val achievementCategories: Map<GW2AchievementCategoryId, GW2v2AchievementCategory> = emptyMap(),
        val achievementGroupsIds: Set<GW2AchievementGroupId>? = null,
        val achievementGroups: Map<GW2AchievementGroupId, GW2v2AchievementGroups> = emptyMap(),
    ) {

        // Non-backed fields:

        val achievement by lazy {
            AchievementsCalc(achievements, achievementCategories, achievementGroups)
        }

        companion object
    }

    companion object

}