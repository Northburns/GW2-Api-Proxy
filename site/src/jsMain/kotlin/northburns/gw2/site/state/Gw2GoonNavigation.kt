package northburns.gw2.site.state

import io.github.aakira.napier.Napier
import io.kvision.navigo.Match
import io.kvision.routing.Routing
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.state.GoonAction.NavigationAction
import northburns.gw2.site.state.Gw2GoonView.HomeView
import northburns.gw2.site.state.Gw2GoonView.HomesteadRefinementView

class Gw2GoonNavigation {
    val routing = Routing.init()

    fun initialize() {
        routing
            // Root
            .on({ goonStore.dispatch(NavigationAction(HomeView)) })
            // Views
            .on(
                HomesteadRefinementView.to,
                { goonStore.dispatch(NavigationAction(HomesteadRefinementView)) }
            )
            // 404
            .notFound({ goonStore.dispatch(NavigationAction(Gw2GoonView.ErrorView("Unexpected path."))) })
            .resolve()

        //routing.navigate("")
    }

    fun navigateTo(view: Gw2GoonView) {
        routing.navigate(view.to)
    }


    fun navigateTo(match: Match) {
        Napier.e { "Navigating:  " + JSON.stringify(match) }
        routing.navigate(match.url)
    }

}

