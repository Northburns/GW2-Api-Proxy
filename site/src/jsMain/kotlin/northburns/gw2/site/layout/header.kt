package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.core.onClickLaunch
import io.kvision.dropdown.dropDown
import io.kvision.form.check.checkBox
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.navbar.nav
import io.kvision.navbar.navForm
import io.kvision.navbar.navLink
import io.kvision.navbar.navbar
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.state.Gw2GoonView
import northburns.gw2.site.state.Gw2GoonView.HomeView
import northburns.gw2.site.state.Gw2GoonView.HomesteadRefinementView

fun Container.headerNav() {
    navbar {
        nav {
            navLink("Home", icon = "fas fa-file") {
                onClickLaunch { Gw2GoonManager.goonNav.navigateTo(HomeView) }
            }
            navLink("🧮 Refinery", icon = "fas fa-bars") {
                onClickLaunch { Gw2GoonManager.goonNav.navigateTo(HomesteadRefinementView) }
            }
            dropDown(
                "Favourites",
                listOf("HTML" to "#!/basic", "Forms" to "#!/forms"),
                icon = "fas fa-star",
                forNavbar = true
            )
        }
        navForm {
            text(label = "Search:")
            checkBox(label = "Search") {
                inline = true
            }
        }
        nav(rightAlign = true) {
            navLink("System", icon = "fab fa-windows")
            button("Purge cache") {
                onClickLaunch {
                    Gw2GoonManager.api.getCacheStuff()
                }
            }
        }
    }
}