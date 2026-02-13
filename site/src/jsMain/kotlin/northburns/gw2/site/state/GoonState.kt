package northburns.gw2.site.state

import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2Item

data class GoonState(
    val appLoading: Boolean = true,
    val context: Context = Context(
        view = Gw2GoonView.HomeView,
    ),
    val api: ApiData = ApiData(),
) {

    data class Context(
        val view: Gw2GoonView = Gw2GoonView.HomeView,
    )

    data class ApiData(
        val items: Map<GW2ItemId, GW2v2Item> = emptyMap(),
    )

}
