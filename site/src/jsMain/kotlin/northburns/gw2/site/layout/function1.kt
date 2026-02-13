package northburns.gw2.site.layout

import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import io.github.aakira.napier.Napier
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import io.kvision.html.ol
import io.kvision.html.span
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.Gw2GoonManager.withProgress
import northburns.gw2.site.layout.components.GoonTimelineChart
import northburns.gw2.site.layout.components.progressPlaceHolder
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Container.function1() {

    Napier.i("function1")
    val chart = GoonTimelineChart<String, AccountSnapshot>(
        listOf(
            "Imperial Favor",
            "Writ's IF Value"
        )
    ) { snapshot ->
        snapshot.creationDate to mapOf(
            "Imperial Favor" to snapshot.account.currency(GW2CurrencyId(68L)),
            "Writ's IF Value" to 5L * listOf(
                GW2ItemId(96680L),
                GW2ItemId(96533L),
                GW2ItemId(96561L),
                GW2ItemId(95692L),
            ).sumOf { snapshot.account.items(it) },
        )
    }




    div {
        add(chart.chart)

        ol {
            addAfterInsertHook {
                withProgress {
                    Gw2GoonManager.api.getSnapshotIds().forEach { id ->

                        li {
                            span(id.toString())

                            progressPlaceHolder({ Gw2GoonManager.api.isSnapshotInCache(id) }) { isInCache ->
                                if (isInCache) {
                                    progressPlaceHolder({ Gw2GoonManager.api.getSnapshot(id) }) { snapshot ->
                                        if (snapshot != null) {
                                            chart.addValue(snapshot)
                                            span("✅")
                                        } else span("Unable to get snapshot...")
                                    }
                                } else {
                                    span("❌")
                                }
                            }

                        }

                    }
                }


//            api.getSnapshotIds().forEach { i ->
//
//                withProgress {
//
//                    val snapshot = api.getSnapshot(i)
//                    div {
//                        h5(i.toString())
//                        span(snapshot.toString().substring(0, 200))
//                    }
//
//                }
//
//
//            }


            }
        }
    }

}