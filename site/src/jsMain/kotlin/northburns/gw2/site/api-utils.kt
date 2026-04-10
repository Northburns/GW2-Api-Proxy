package northburns.gw2.site

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
import com.gw2tb.gw2api.types.v2.GW2v2Item
import com.gw2tb.gw2api.types.v2.GW2v2Mini
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Skill
import com.gw2tb.gw2api.types.v2.GW2v2Skin
import com.gw2tb.gw2api.types.v2.GW2v2Specialization
import com.gw2tb.gw2api.types.v2.GW2v2Trait
import northburns.gw2.client.myclient.data.AchievementsCalc.AchievementFull
import northburns.gw2.client.myclient.utils.ChunkedChannel
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.GoonState.ApiData
import kotlin.reflect.typeOf

//
// ApiData.resolve methods either return the requested thing from the `api` state object,
// or return null. If they return null, they also fire a request to the Api service to fill
// in the api object to state. Use in bind extractors.
//


// I think this'd be nicer to use
object StateResolve {
    fun <T> GW2ItemId?.resolver(map: GW2v2Item.() -> T): (GoonState) -> T? {
        return { state -> state.api.resolve(this)?.let(map) }
    }
}

fun ApiData.resolveBitIcon(bit: GW2v2Achievement.Bit?): String? {
    return when (bit) {
        is GW2v2Achievement.Bit.Item -> resolve(bit.id)?.icon
        is GW2v2Achievement.Bit.MiniPet -> resolve(bit.id)?.icon
        is GW2v2Achievement.Bit.Skin -> resolve(bit.id)?.icon
        is GW2v2Achievement.Bit.Text -> null
        null -> null
    }
}

fun ApiData.resolveBitText(bit: GW2v2Achievement.Bit?): String? {
    return when (bit) {
        is GW2v2Achievement.Bit.Item -> bit.text ?: resolve(bit.id)?.name
        is GW2v2Achievement.Bit.MiniPet -> bit.text ?: resolve(bit.id)?.name
        is GW2v2Achievement.Bit.Skin -> bit.text ?: resolve(bit.id)?.name
        is GW2v2Achievement.Bit.Text -> bit.text
        null -> null
    }
}

fun ApiData.resolve(itemId: GW2ItemId?): GW2v2Item? =
    items.resolveInternal(itemId, Gw2GoonManager.r.items)

fun ApiData.resolve(miniId: GW2MiniId?): GW2v2Mini? =
    minis.resolveInternal(miniId, Gw2GoonManager.r.minis)

fun ApiData.resolve(skinId: GW2SkinId?): GW2v2Skin? =
    skins.resolveInternal(skinId, Gw2GoonManager.r.skins)

fun ApiData.resolve(professionId: GW2ProfessionId?): GW2v2Profession? =
    professions.resolveInternal(professionId, Gw2GoonManager.r.professions)

fun ApiData.resolve(specializationId: GW2SpecializationId?): GW2v2Specialization? =
    specializations.resolveInternal(specializationId, Gw2GoonManager.r.specializations)

fun ApiData.resolve(traitId: GW2TraitId?): GW2v2Trait? =
    traits.resolveInternal(traitId, Gw2GoonManager.r.traits)

fun ApiData.resolve(skillId: GW2SkillId?): GW2v2Skill? =
    skills.resolveInternal(skillId, Gw2GoonManager.r.skills)

fun ApiData.resolveMany(traitIds: Collection<GW2TraitId>): Map<GW2TraitId, GW2v2Trait>? =
    traits.resolveManyInternal(traitIds, Gw2GoonManager.r.traits)

fun ApiData.resolve(achievementId: GW2AchievementId?): GW2v2Achievement? =
    achievements.resolveInternal(achievementId, Gw2GoonManager.r.achievements)

fun ApiData.resolveMany(achievementIds: Collection<GW2AchievementId>): Map<GW2AchievementId, GW2v2Achievement>? =
    achievements.resolveManyInternal(achievementIds, Gw2GoonManager.r.achievements)

fun ApiData.resolve(achievementCategoryId: GW2AchievementCategoryId?): GW2v2AchievementCategory? =
    achievementCategories.resolveInternal(achievementCategoryId, Gw2GoonManager.r.achievementCategories)

fun ApiData.resolveMany(achievementCategoryIds: Collection<GW2AchievementCategoryId>): Map<GW2AchievementCategoryId, GW2v2AchievementCategory>? =
    achievementCategories.resolveManyInternal(achievementCategoryIds, Gw2GoonManager.r.achievementCategories)

fun ApiData.resolve(achievementGroupId: GW2AchievementGroupId?): GW2v2AchievementGroups? =
    achievementGroups.resolveInternal(achievementGroupId, Gw2GoonManager.r.achievementGroups)


fun ApiData.resolveAchievement(achievementId: GW2AchievementId?): AchievementFull? {
    if (achievementId == null) return null
    return achievement.full(
        aId = achievementId,
        onNullAch = { Gw2GoonManager.r.achievements(achievementId) },
        onNullCat = { Gw2GoonManager.r.achievementCategoriesAll(Unit) },
        onNullGrp = { Gw2GoonManager.r.achievementGroupsAll(Unit) },
    )
}

inline fun <reified ID : Any> ApiData.resolveIds(): Set<ID>? {
    val set: Set<Any>? = when (typeOf<ID>()) {
        typeOf<GW2AchievementCategoryId>() -> achievementCategoriesIds
            ?: Gw2GoonManager.r.achievementCategoriesIds(Unit).let { null }

        typeOf<GW2AchievementGroupId>() -> achievementGroupsIds
            ?: Gw2GoonManager.r.achievementGroupsIds(Unit).let { null }

        else -> throw IllegalArgumentException("Unexpected type: ${typeOf<ID>()}")
    }
    @Suppress("UNCHECKED_CAST")
    return set as Set<ID>?
}

// ResolveMany
// These don't allocate new memory on success. They return the actual map,
// in its entirety, if all requested ids are present. Otherwise, they request the missing ids.

private fun <K : Any, V : Any> Map<K, V>.resolveManyInternal(
    ids: Collection<K>,
    channel: ChunkedChannel<K>,
): Map<K, V>? {
    return if (keys.containsAll(ids)) this
    else channel(ids - keys).let { null }
}

private fun <K : Any, V : Any> Map<K, V>.resolveInternal(
    id: K?,
    channel: ChunkedChannel<K>,
): V? {
    return if (id == null) null
    else this[id] ?: channel(id).let { null }
}
