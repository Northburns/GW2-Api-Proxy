package northburns.gw2.site.state

data class GoonState(
    val appLoading: Boolean = true,
    val context: Context = Context(
        view = Gw2GoonView.HomeView,
    ),
) {

    data class Context(
        val view: Gw2GoonView = Gw2GoonView.HomeView,
    )

}
