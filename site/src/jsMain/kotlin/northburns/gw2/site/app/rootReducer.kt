package northburns.gw2.site.app

import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.utils.replaceKeys
import northburns.gw2.site.actions.ApiDataAction
import northburns.gw2.site.actions.CommonActions
import northburns.gw2.site.actions.FatalError
import northburns.gw2.site.actions.FetchAchievementCategoriesIdsSuccess
import northburns.gw2.site.actions.FetchAchievementCategoriesSuccess
import northburns.gw2.site.actions.FetchAchievementGroupsIdsSuccess
import northburns.gw2.site.actions.FetchAchievementGroupsSuccess
import northburns.gw2.site.actions.FetchAchievementsSuccess
import northburns.gw2.site.actions.FetchApiDataAction
import northburns.gw2.site.actions.FetchItemsSuccess
import northburns.gw2.site.actions.FetchMinisSuccess
import northburns.gw2.site.actions.FetchPricesSuccess
import northburns.gw2.site.actions.FetchProfessionsSuccess
import northburns.gw2.site.actions.FetchSkillsSuccess
import northburns.gw2.site.actions.FetchSkinsSuccess
import northburns.gw2.site.actions.FetchSnapshotIdsSuccess
import northburns.gw2.site.actions.FetchSnapshotSuccess
import northburns.gw2.site.actions.FetchSpecializationsSuccess
import northburns.gw2.site.actions.FetchTraitsSuccess
import northburns.gw2.site.actions.GoonAction
import northburns.gw2.site.actions.NavigationAction
import northburns.gw2.site.actions.PartyChangedAction
import northburns.gw2.site.actions.ResetState
import northburns.gw2.site.actions.SessionTrackerViewAction
import northburns.gw2.site.actions.SnapshotAction
import northburns.gw2.site.actions.TlsAction
import northburns.gw2.site.actions.TlsLoggedIn
import northburns.gw2.site.app.sessiontracker.sessiontrackerReducer
import northburns.gw2.site.app.snapshotstatus.snapshotReducer

fun goonReducer(state: GoonState, action: GoonAction): GoonState = when (action) {
    is TlsAction -> when (action) {

        is TlsLoggedIn -> state.copy(
            account = action.account,
        )
    }

    is CommonActions -> when (action) {
        is ResetState -> action.state
        is NavigationAction -> state.copy(
            currentView = action.route,
        )
        is PartyChangedAction -> state.copy(
            snapshots = state.snapshots.copy(
                snapshots = state.snapshots.snapshots.replaceKeys(action.partyPlayerIds) { AccountSnapshot.new() }
            ),
        )

        is FatalError -> state.copy(
            fatalError = action.error,
        )
    }

    is ApiDataAction -> goonReducerApiAction(state, action)
    is SnapshotAction<*> -> snapshotReducer(state, action)

    // Views
    is SessionTrackerViewAction -> state.copy(
        sessiontracker = sessiontrackerReducer(state.sessiontracker, action)
    )

}

// TODO Refactor this method. The operations are so simple, but it takes so many lines!
fun goonReducerApiAction(state: GoonState, action: ApiDataAction): GoonState = when (action) {
    is FetchApiDataAction -> state.copy(api = goonReducerFetchApiDataAction(state.api, action))

    is FetchSnapshotIdsSuccess -> state.copy(
        snapshotIds = action.snapshotIds // .mapKeys { (k, _) -> Gw2Json.json.encodeToString(k) },
    )

    is FetchSnapshotSuccess -> state.copy(
        snapshots = state.snapshots.copy(
            snapshots = state.snapshots.snapshots + (action.playerId to action.snapshot)
        ),
    )

}

fun goonReducerFetchApiDataAction(d: GoonState.ApiData, action: FetchApiDataAction): GoonState.ApiData {
    return when (action) {
        is FetchAchievementCategoriesSuccess -> d.copy(
            achievementCategories = d.achievementCategories + action.categories.associateBy { it.id },
        )

        is FetchAchievementGroupsSuccess -> d.copy(
            achievementGroups = d.achievementGroups + action.groups.associateBy { it.id },
        )

        is FetchAchievementsSuccess -> d.copy(
            achievements = d.achievements + action.achievements.associateBy { it.id },
        )

        is FetchItemsSuccess -> d.copy(
            items = d.items + action.items.associateBy { it.id },
        )

        is FetchMinisSuccess -> d.copy(
            minis = d.minis + action.minis.associateBy { it.id },
        )

        is FetchSkinsSuccess -> d.copy(
            skins = d.skins + action.skins.associateBy { it.id },
        )

        is FetchProfessionsSuccess -> d.copy(
            professions = d.professions + action.professions.associateBy { it.id },
        )

        is FetchSpecializationsSuccess -> d.copy(
            specializations = d.specializations + action.specializations.associateBy { it.id },
        )

        is FetchTraitsSuccess -> d.copy(
            traits = d.traits + action.traits.associateBy { it.id },
        )

        is FetchSkillsSuccess -> d.copy(
            skills = d.skills + action.skills.associateBy { it.id },
        )

        is FetchPricesSuccess -> d.copy(
            prices = d.prices.plus(action.prices.associateBy { it.id }),
        )

        is FetchAchievementCategoriesIdsSuccess -> d.copy(
            achievementCategoriesIds = action.ids.toSet(),
        )

        is FetchAchievementGroupsIdsSuccess -> d.copy(
            achievementGroupsIds = action.ids.toSet(),
        )
    }
}
