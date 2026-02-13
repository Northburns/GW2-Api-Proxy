package northburns.gw2.site

import io.github.aakira.napier.Napier
import io.kvision.Application
import io.kvision.html.footer
import io.kvision.html.header
import io.kvision.html.main
import io.kvision.navigo.Match
import io.kvision.pace.Pace
import io.kvision.pace.PaceOptions
import io.kvision.panel.root
import io.kvision.state.bind
import io.kvision.theme.Theme
import io.kvision.theme.ThemeManager
import io.kvision.utils.useModule
import kotlinx.coroutines.cancel
import northburns.gw2.site.helper.datefns.chartjsAdapterDateFns
import northburns.gw2.site.helper.datefns.dateFns
import northburns.gw2.site.layout.headerNav
import northburns.gw2.site.state.GoonAction
import northburns.gw2.site.state.GoonState


class Gw2GoonApplication : Application() {
    init {
        useModule(kvappCss)
        useModule(dateFns)
        useModule(chartjsAdapterDateFns)
        ThemeManager.init(Theme.LIGHT, remember = true)
    }

    override fun start(state: Map<String, Any>) {
        Pace.init(paceProgressbarCss)
        Pace.setOptions(
            PaceOptions(
                manual = true
            )
        )
        Gw2GoonManager.initialize()

        root("kvapp") {
            header().bind(Gw2GoonManager.goonStore) { state ->
                headerNav()
            }
            main().bind(Gw2GoonManager.goonStore, { it.context }) { context ->
                context.view.build(this)
            }
            footer()

            state["store"]?.also { s ->
                Gw2GoonManager.goonStore.dispatch(GoonAction.ResetState(s as GoonState))
            }
            state["location"]?.also { l ->
                @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
                Gw2GoonManager.goonNav.navigateTo(l as Match)
            }
        }
    }

    override fun dispose(): Map<String, Any> {
        val state = mapOf(
            "store" to Gw2GoonManager.goonStore.getState(),
            "location" to Gw2GoonManager.goonNav.routing.getCurrentLocation(),
        )
        Napier.e { "Closing app with state: " + JSON.stringify(state) }
        Gw2GoonManager.cancel("App is disposed")
        return state
    }
}