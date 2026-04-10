package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementGroupId
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.v2.GW2v2AccountAchievement
import com.gw2tb.gw2api.types.v2.GW2v2Achievement
import com.gw2tb.gw2api.types.v2.GW2v2AchievementCategory
import com.gw2tb.gw2api.types.v2.GW2v2AchievementGroups
import northburns.gw2.client.myclient.PlayerId

class AchievementsCalc(
    val achs: Map<GW2AchievementId, GW2v2Achievement>,
    val cats: Map<GW2AchievementCategoryId, GW2v2AchievementCategory>,
    val groups: Map<GW2AchievementGroupId, GW2v2AchievementGroups>,
) {

    private val fullAchievements: MutableMap<GW2AchievementId, AchievementFull> = mutableMapOf()

    fun full(
        aId: GW2AchievementId,
        cat: (aId: GW2AchievementId) -> GW2v2AchievementCategory? = catsByAch::get,
        grp: (aId: GW2AchievementId) -> GW2v2AchievementGroups? = ::groupsByAch,
        onNullAch: ()-> Unit={},
        onNullCat: () -> Unit = {},
        onNullGrp: () -> Unit = {},
    ): AchievementFull? {
        val a = achs[aId] ?: return onNullAch().let { null }
        return fullAchievements.getOrPut(aId) {
            AchievementFull(
                a = a,
                c = cat(aId) ?: onNullCat().let { null },
                g = grp(aId) ?: onNullGrp().let { null },
            )
        }
    }

    /**
     * [c] and [g] are nullable just in case there is insufficient data provided
     * to [northburns.gw2.client.myclient.data.AchievementsCalc]
     */
    data class AchievementFull(
        val a: GW2v2Achievement,
        val c: GW2v2AchievementCategory?,
        val g: GW2v2AchievementGroups?,
    ) {
        // TODO calculations on a single achivement here (bits etc)? (nah, extension functions is better)
    }

    class AchievementCalc(
        private val a: GW2v2Achievement,
        private val players: Map<PlayerId, GW2v2AccountAchievement?>,
    ) {
        private val calcs = mutableMapOf<PlayerId, AchievementCalcPlayer>()
        fun get(playerId: PlayerId) = calcs.getOrPut(playerId) { AchievementCalcPlayer(players[playerId]) }

        fun totalAp(): Long = a.tiers.sumOf { it.points }

        inner class AchievementCalcPlayer(
            private val account: GW2v2AccountAchievement?,
        ) {
            val bitsDoneByIndex = a.bits?.let { bits ->
                (0 until bits.size).map { i ->
                    if (isDone()) true else account?.bits?.contains(i.toLong()) ?: false
                }
            }

            fun isBitDone(bitIndex: Int): Boolean = bitsDoneByIndex?.getOrNull(bitIndex) == true
            fun isDone() = account?.done == true
        }
    }


    val catsByAch: Map<GW2AchievementId, GW2v2AchievementCategory> by lazy {
        cats.values.flatMap { c -> c.achievements.map { a -> a.id to c } }.toMap()
    }
    val groupsByCat: Map<GW2AchievementCategoryId, GW2v2AchievementGroups> by lazy {
        groups.values.flatMap { g -> g.categories.map { c -> c to g } }.toMap()
    }

    fun groupsByAch(aId: GW2AchievementId): GW2v2AchievementGroups? =
        catsByAch[aId]?.let { c -> groupsByCat[c.id] }

}