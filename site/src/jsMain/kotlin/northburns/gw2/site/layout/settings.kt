package northburns.gw2.site.layout

import io.kvision.core.Container
import io.kvision.core.onClickLaunch
import io.kvision.html.Align
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.p
import northburns.gw2.client.myclient.log.GoonLogTimeRecords
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.layout.components.CommonComponents.goonH1
import northburns.gw2.site.layout.components.CommonComponents.goonH2
import northburns.gw2.site.layout.components.SimpleTableColumn
import northburns.gw2.site.layout.components.SimpleTableOptions
import northburns.gw2.site.layout.components.goonButton
import northburns.gw2.site.layout.components.simpleTable
import northburns.gw2.site.util.cache.IndexedDBHelper
import northburns.gw2.site.util.reselect.goonBindReselect
import northburns.gw2.site.util.reselect.renderNull1
import kotlin.time.Duration

fun Container.settings() {

    goonH1("Settings")

    p("TODO the UX of this page is awful 😅")

    goonH2("Player & Party")

    div()
        .goonBindReselect { it: GoonState -> it.account }
        .renderNull1 { +"Not logged in..." }
        .render { (auth) ->

            p("Logged in as: " + auth.user.name)

            p("TODO a gw2me prompt button here")

            goonButton(text="prompt=consent") {
                Gw2GoonManager.gw2me.handleNotLoggedIn(promptConsent = true)
            }


            SettingsAccounts(auth).also { add(it) }

        }

    goonH2("Cache")

    val d = div {}

    button("Read indexedDB databases", className = "border-2 border-black").onClickLaunch {
        val x = IndexedDBHelper.read()
        d.apply {
            removeAll()
            p("Cache uses ${x.megabytes ?: '?'} Mb of storage, in following databases:")
            x.dbs.forEach { db ->
                p("DB: '${db.name}', version=${db.version}")
            }
            button("Delete all databases", className = "border-2 border-black").onClickLaunch {
                IndexedDBHelper.deleteAll()
                d.apply {
                    removeAll()
                    p(
                        "ALL databases are now DELETED! App may not work now, please refresh page.",
                        className = "bg-yellow-300"
                    )
                }
            }
        }
    }

    goonH2("Log Time Records")

    val dTimes = div {}

    button("Read Log Time Records", className = "border-2 border-black").onClick {
        val calcs = GoonLogTimeRecords.calculate()

        dTimes.apply {
            removeAll()

            fun Duration.s(): String {
                return toString() // TODO better to string for duration in this table...
            }

            simpleTable(
                data = calcs, options = SimpleTableOptions(
                    columns = listOf(
                        SimpleTableColumn(
                            header = { "Name" },
                            cell = { _, t -> t.name },
                        ),
                        SimpleTableColumn(
                            header = { "Count" },
                            align = Align.RIGHT,
                            cell = { _, t -> t.count.toString() },
                        ),
                        SimpleTableColumn(
                            header = { "Avg" },
                            align = Align.RIGHT,
                            cell = { _, t -> t.avg.s() },
                        ),
                        SimpleTableColumn(
                            header = { "StdDev" },
                            align = Align.RIGHT,
                            cell = { _, t -> t.stddev.s() },
                        ),
                        SimpleTableColumn(
                            header = { "Min" },
                            align = Align.RIGHT,
                            cell = { _, t -> t.min.s() },
                        ),
                        SimpleTableColumn(
                            header = { "Max" },
                            align = Align.RIGHT,
                            cell = { _, t -> t.max.s() },
                        ),
                    )
                )
            )
        }
    }

}
