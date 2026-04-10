package northburns.gw2.site

import io.kvision.state.ObservableState
import io.kvision.state.sub
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.app.GoonState

object Gw2GoonState {
    val goonState: ObservableState<GoonState> = goonStore
    val apiState = goonState.sub { it.api }
    val account = goonState.sub { it.account }
    val party = goonState.sub { it.account?.party }
}
