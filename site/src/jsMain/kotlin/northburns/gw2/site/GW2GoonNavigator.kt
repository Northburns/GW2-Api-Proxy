package northburns.gw2.site

import io.kvision.navigo.Match
import io.kvision.routing.Routing
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.actions.NavigationAction
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.app.GoonRoute
import northburns.gw2.site.util.dynamicToMap
import northburns.gw2.site.util.jso

class GW2GoonNavigator {
    private val navigo = createNavigo()
    fun resolve() {
        navigo.resolve()
    }


    /**
     * Use this in application code. This ensures that Navigo is up-to-date on our navigations.
     */
    fun navigateTo(gp: GoonPath, params: Map<String, String> = emptyMap()) {
        val data = jso {
            params.forEach { (k, v) -> this[k] = v }
        }
        val success = navigo.navigateByName(gp.name, data)
        require(success) { "Error navigating to $gp $params" }
    }

    fun navigateTo(route: GoonRoute) = navigateTo(route.path, route.params)


    /**
     * Use only in development context
     */
    fun getCurrentLocation(): Match = navigo.getCurrentLocation()

    /**
     * Use outside this class only in development context
     */
    fun dispatchNavAction(match: Match) {
        GoonLog["manager"].info("Navigating", match)
        val gp = GoonPath.byPath.getValue(match.route.path)
        val data = dynamicToMap(match.data)?.mapValues { (_, v) -> v.toString() } ?: emptyMap()
        val params = dynamicToMap(match.params)?.mapValues { (_, v) -> v.toString() } ?: emptyMap()

        dispatchNavAction(gp, data + params)
        // goonNav.navigate(match.hashString)
    }

    //////////////////////////////////////////////////////////////////////////

    private fun dispatchNavAction(path: GoonPath, params: Map<String, String>) {
        goonStore.dispatch(NavigationAction(GoonRoute(path, params)))
    }

    private fun createNavigo(): Routing {

        val routing = Routing.init()

        // I just use fixed paths and customize views with query params.
        // We need more comples setup if we want to grab elements from path,
        // or do some other Regex stuff. But for now, this'll do.
        GoonPath.entries.forEach { goonPath ->
            val on = jso {
                this[goonPath.path] = jso {
                    this["as"] = goonPath.name
                    this["uses"] = { match: Match -> dispatchNavAction(match) }
                }
            }
            routing.on(map = on)
//            routing.on(path = goonPath.path, f = )
        }

        routing
            .notFound({ match ->
                dispatchNavAction(
                    GoonPath.SPECIAL_404,
                    mapOf("message" to JSON.stringify(match, null, 2))
                )
            })

        return routing
    }

}

