package northburns.gw2.site

import io.kvision.Application
import io.kvision.html.footer
import io.kvision.html.header
import io.kvision.html.main
import io.kvision.navigo.Match
import io.kvision.pace.Pace
import io.kvision.pace.PaceOptions
import io.kvision.panel.Root
import io.kvision.panel.root
import io.kvision.state.bind
import io.kvision.utils.useModule
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.actions.ResetState
import northburns.gw2.site.app.GoonPath.ACCOUNTS_CHARACTERS
import northburns.gw2.site.app.GoonPath.ACCOUNTS_CHARACTERS_VIEW
import northburns.gw2.site.app.GoonPath.REF_DRIZZLEWOODCOAST
import northburns.gw2.site.app.GoonPath.REF_PROFESSION
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_ELEMENTALIST
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_ENGINEEER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_GUARDIAN
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_MESMER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_NECROMANCER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_RANGER
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_REVENANT
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_THIEF
import northburns.gw2.site.app.GoonPath.REF_PROFESSION_WARRIOR
import northburns.gw2.site.app.GoonPath.REF_VISION
import northburns.gw2.site.app.GoonPath.SPECIAL_404
import northburns.gw2.site.app.GoonPath.SPECIAL_HOME
import northburns.gw2.site.app.GoonPath.SPECIAL_MD
import northburns.gw2.site.app.GoonPath.SPECIAL_SETTINGS
import northburns.gw2.site.app.GoonPath.TOOL_REFINERY
import northburns.gw2.site.app.GoonPath.TOOL_SESSIONTRACKER
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.app.account.allCharacters
import northburns.gw2.site.app.account.character
import northburns.gw2.site.app.content.`guide-drizzlewood-coast`
import northburns.gw2.site.app.content.guideLegeVision
import northburns.gw2.site.app.content.professionReference
import northburns.gw2.site.app.markdownview.markdownview
import northburns.gw2.site.app.refinery.refinery
import northburns.gw2.site.app.sessiontracker.sessiontracker
import northburns.gw2.site.helper.datefns.chartjsAdapterDateFns
import northburns.gw2.site.helper.datefns.dateFns
import northburns.gw2.site.layout._404
import northburns.gw2.site.layout.headerNav
import northburns.gw2.site.layout.home
import northburns.gw2.site.layout.settings
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.tailwind.tw

class Gw2GoonApplication : Application() {
    init {
        useModule(kvappCss)
        useModule(dateFns)
        useModule(chartjsAdapterDateFns)
    }

    override fun start(state: Map<String, Any>) {
        Pace.init(paceProgressbarCss)
        Pace.setOptions(
            PaceOptions(
                manual = true
            )
        )
        Gw2GoonManager.initialize()

        root("kvapp")
            .goonBindReselect(
                { it.loggedIn },
                { it.fatalError }
            )
            .render { (loggedIn, fatalError) ->
                when {

                    fatalError != null -> {
                        +"Fatal Error: "
                        +fatalError.toString()
                    }

                    !loggedIn -> {
                        +"Logging in..."
                        Gw2GoonManager.gw2me.handleNotLoggedIn()
                    }

                    else -> {
                        renderApp(state)
                    }
                }
            }
    }

    private fun Root.renderApp(state: Map<String, Any>) {
        header(
            className = tw {
                +"print:hidden"
            }
        ).bind(goonStore, { it.currentView }) { view ->
            headerNav()
        }
        main(className = tw {
            +"container mx-auto p-4 pb-8 max-w-4xl"
            +"shadow-xl/80"
//                md {
//                    +"max-w-md"
//                }
        }).bind(goonStore, { it.currentView }) { view ->

            /*
             * REMEMBER!
             *
             * If those functions bind something to the receiver,
             * _you're gonna have a bad time_.
             *
             * Instead, create a div or something in them, and bind that!
             */

            when (view.path) {
                SPECIAL_HOME -> home()

                ACCOUNTS_CHARACTERS -> allCharacters()
                ACCOUNTS_CHARACTERS_VIEW -> character(view.params)

                TOOL_REFINERY -> refinery()
                TOOL_SESSIONTRACKER -> sessiontracker()

                REF_VISION -> guideLegeVision()
                REF_DRIZZLEWOODCOAST -> `guide-drizzlewood-coast`()

                REF_PROFESSION -> professionReference(view.path, view.params)
                REF_PROFESSION_GUARDIAN -> professionReference(view.path, view.params)
                REF_PROFESSION_REVENANT -> professionReference(view.path, view.params)
                REF_PROFESSION_WARRIOR -> professionReference(view.path, view.params)
                REF_PROFESSION_ENGINEEER -> professionReference(view.path, view.params)
                REF_PROFESSION_RANGER -> professionReference(view.path, view.params)
                REF_PROFESSION_THIEF -> professionReference(view.path, view.params)
                REF_PROFESSION_ELEMENTALIST -> professionReference(view.path, view.params)
                REF_PROFESSION_MESMER -> professionReference(view.path, view.params)
                REF_PROFESSION_NECROMANCER -> professionReference(view.path, view.params)

                SPECIAL_404 -> _404(view.params)
                SPECIAL_MD -> markdownview(view.params)
                SPECIAL_SETTINGS -> settings()
            }

        }

        footer(className = tw {
            +"print:hidden"
        })

        state["store"]?.also { s ->
            goonStore.dispatch(ResetState(s as GoonState))
        }
        state["location"]?.also { l ->
            @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
            Gw2GoonManager.goonNav.dispatchNavAction(l as Match)
        }
    }

    override fun dispose(): Map<String, Any> {
        val state = mapOf(
            "store" to goonStore.getState(),
            "location" to Gw2GoonManager.goonNav.getCurrentLocation(),
        )
        console.log("Closing app with state", goonStore.getState())
        // Gw2GoonManager.cancel("App is disposed")
        return state
    }
}