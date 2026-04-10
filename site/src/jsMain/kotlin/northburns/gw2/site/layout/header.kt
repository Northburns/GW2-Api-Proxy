package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.nav
import kotlinx.browser.window
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.app.snapshotstatus.snapshotStatusView
import northburns.gw2.site.layout.components.goonButton
import northburns.gw2.site.layout.style.PanelBackgrounds.PanelBackground.BROWN_DAMAGED
import northburns.gw2.site.layout.style.PanelBackgrounds.PanelBackground.METAL
import northburns.gw2.site.layout.style.PanelBackgrounds.setPanelBackground
import northburns.gw2.site.layout.style.SvgInline.DRAGONHEAD
import northburns.gw2.site.layout.style.svg
import northburns.gw2.site.layout.style.toggleClass
import northburns.gw2.site.util.tailwind.tw
import kotlin.uuid.Uuid


fun Container.headerNav() {
    val snapshotAreaId = Uuid.random().toString()
    val menuId = Uuid.random().toString()
    val snapshotId = Uuid.random().toString()
    val menuHiddenByDefault = false // TODO should be true!
    val snapshotHiddenByDefault = true
    // insp from
    // https://tailwindcss.com/plus/ui-blocks/application-ui/navigation/navbars
    nav(className = tw {
        +"shadow-md p-2"
        // +"after:pointer-events-none after:absolute after:inset-x-0 after:bottom-0 after:h-px after:bg-white/10"
    }) {
        setPanelBackground(panel = METAL, borderWidth = "0px 8px 8px 8px")

        // Header - TOP ROW
        div(className = "flex justify-content-center items-center") {
            div(className = tw {
                +"grow"
                +"flex justify-content-center items-center"
                +"cursor-pointer"
            }) {
                svg(DRAGONHEAD, tw {
                    +"inline align-baseline size-[3rem]"
                    +"mr-1"
                    +"fill-black"
                })
                h1("GW2 Goon", className = tw {
                    +"limelight-regular" // google font
                    +"text-3xl"
                    +"grow"
                    // +"shadow-md"
                })
                onClick {
                    Gw2GoonManager.goonNav.navigateTo(GoonPath.SPECIAL_HOME)
                }
            }

            div(className = "flex") {
                goonButton(
                    // TODO bind this button and change text according to snapshot status
                    text = "✅",
                    className = "",
                    onClick = {
                        window.document.getElementById(snapshotId)?.toggleClass("hidden")
                    },
                )
                goonButton(
                    text = "☰",
                    className = "md:hidden",
                    onClick = {
                        window.document.getElementById(menuId)?.toggleClass("hidden")
                    },
                )
            }
        }

        // Header - Bottom Row
        div(className = tw {
            +"block justify-content-center"
            if (menuHiddenByDefault) +"hidden"
            +"px-3"
        }) {
            id = menuId

            topmenu()
        }

    }
    // Header - snapshot status and party setup (or below header?..)
    nav(className = tw {
        +"shadow-md p-2 mx-2"
        if (snapshotHiddenByDefault) +"hidden"
        // +"after:pointer-events-none after:absolute after:inset-x-0 after:bottom-0 after:h-px after:bg-white/10"
    }) {
        id = snapshotId
        setPanelBackground(panel = BROWN_DAMAGED, borderWidth = "0px 8px 8px 8px")

        snapshotStatusView()
    }
    /*
    navbar {
        nav {
            navLink("Home", icon = "fas fa-file") {
                onClickLaunch { Gw2GoonManager.navigateTo(HomeRoute) }
            }

            dropDown(
                "Tools",
                listOf(
                        "Refinery" to RefineryRoute.hashPath(), //  "#!/basic",
                    "Session Track" to SessionTrackerRoute.hashPath(), //  "#!/forms",
                ),
                icon = "fas fa-money-bill-wheat",
                forNavbar = true
            )

            dropDown(
                "Guides",
                listOf(
                    "Legendaries" to DD.HEADER.option,
                    "Vision" to GuideLegendaryVisionRoute.hashPath(),
                    "" to DD.SEPARATOR.option,
                    "Forms" to "#/forms",
                ),
                icon = "fas fa-star",
                forNavbar = true
            )

    //            navLink("Refinery", icon = "fas fa-filter") {
    //                onClickLaunch { Gw2GoonManager.navigateTo(RefineryRoute) }
    //            }
    //            navLink("Session Track", icon = "fas fa-money-bill-wheat") {
    //                onClickLaunch { Gw2GoonManager.navigateTo(SessionTrackerRoute) }
    //            }
        }

        nav(rightAlign = true) {
            navLink("System", icon = "fab fa-windows")
            button("Status") {
                forCollapse(snapshotAreaId)
            }
            select().bind(
                Gw2GoonManager.goonStore,
                { it.players?.first() to it.playerData }) { (currentPlayer, players) ->
                options = players.entries.sortedBy { it.value.order }.map { it.key.id to it.value.name }
                value = currentPlayer?.id
                subscribe { v ->
                    GoonLog["PlayerSelect"].w("Player selector does nothing. We need to implement the party selector thing.")
                }
            }
            button("Purge cache") {
                onClickLaunch {
                    //Gw2GoonManager.api.getCacheStuff()
                    window.alert("Purge cache")
                }
            }
        }
    }
    collapse(id = snapshotAreaId, opened = true) {
        snapshotStatusView()
    }
    */

}