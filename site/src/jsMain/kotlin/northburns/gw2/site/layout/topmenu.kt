package northburns.gw2.site.layout

import io.kvision.core.Container
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.layout.components.GoonTabsheet
import northburns.gw2.site.layout.components.GoonTabsheet.GoonTab
import northburns.gw2.site.layout.components.TextWithIcon

fun Container.topmenu() {
    val currentView = goonStore.getState().currentView

    fun Container.navb(text: String, nav: GoonPath) {
        val current = currentView.isSameOrSubPathOf(nav)
        TextWithIcon(
            text = if(!current) text else "[ $text ]",
            href = nav.hashPath(),
        ).also {
            add(it)
        }
//        goonButton(
//            text = text,
//            style = if (!currentView.isSameViewAs(nav)) GoonButtonStyle.ACTION
//            else GoonButtonStyle.ACTION_YELLOW,
//        ) {
//            goonStore.dispatch(NavigationAction(nav))
//        }
    }

    fun buildGoonTabFor(
        title: String,
        routes: List<Pair<String, GoonPath>>,
    ): GoonTab {
        return GoonTab(
            title = title,
            selectedByDefault = routes.any { currentView.isSameOrSubPathOf(it.second) },
            build = {
                routes.forEach { (text, route) -> navb(text, route) }
            },
        )
    }

    /*
     * Current implementation uses NavigationAction to navigate.
     * We're using Navigo, so we could also use #-links.
     * I'm not entirely sure on the implications of either solution, yet.
     * But this is fine for now!
     */

    // TODO Current view highlight somehow (e.g. there's a yellow button style)

    GoonTabsheet(
        tabs = listOf(
            buildGoonTabFor(
                title = "Account",
                routes = listOf(
                    "Characters" to GoonPath.ACCOUNTS_CHARACTERS,
                ),
            ),
            buildGoonTabFor(
                title = "Tools",
                routes = listOf(
                    "Settings" to GoonPath.SPECIAL_SETTINGS,
                    "Refinery" to GoonPath.TOOL_REFINERY,
                    "Session Tracker" to GoonPath.TOOL_SESSIONTRACKER,
                ),
            ),
            buildGoonTabFor(
                title = "Reference",
                routes = listOf(
                    "Vision" to GoonPath.REF_VISION,
                    "Professions" to GoonPath.REF_PROFESSION,
                    "Drizzlewood Coast" to GoonPath.REF_DRIZZLEWOODCOAST,
                ),
            ),
        ),
    ).also { add(it) }

}
