package northburns.gw2.site.state

import io.kvision.core.Container
import northburns.gw2.site.layout._404
import northburns.gw2.site.layout.home
import northburns.gw2.site.layout.refinery

sealed interface Gw2GoonView {
    val to: String

    fun build(c: Container)

    object HomeView : Gw2GoonView {
        override val to: String = "/"
        override fun build(c: Container) = c.home()
    }

    object HomesteadRefinementView : Gw2GoonView {
        override val to: String = "/refine"
        override fun build(c: Container) = c.refinery()
    }

    data class ErrorView(val message: String) : Gw2GoonView {
        override val to: String = "/not-found"

        override fun build(c: Container) = c._404(message)

    }


}

