package northburns.gw2.site.app.snapshotstatus

import io.kvision.core.Container
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.flexPanel
import io.kvision.state.bind
import io.kvision.state.bindEach
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Fresh
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.Stale
import northburns.gw2.client.myclient.snapshot.AccountSnapshot.UpdateRequested
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.layout.components.bootstrap.CardSmall.CardSmallOptions
import northburns.gw2.site.layout.components.bootstrap.cardSmall

fun Container.snapshotStatusView() {

    div(className = "text-bg-info p-2")
        .bind(goonStore, { it.players }) { playerIds ->
            if (playerIds == null) {
                span("Select player first")
                return@bind
            }

            flexPanel(direction = FlexDirection.ROW, wrap = FlexWrap.WRAP, spacing = 1) {
                bindEach(
                    goonStore,
                    sub = { SnapshotStatusRow.fromState(it) },
                //    equalizer = { a, b -> a.id == b.id }
                ) { row ->

                    cardSmall(
                        CardSmallOptions(
                            imageSize = 64,
                            imageSubGw2 = { row.icon },
                            headerSubGw2 = { row.text },
                        )
                    ) {
                        span {
                            when (row.state) {
                                Fresh -> +"✅"
                                Stale -> +"⚠️ Stale"
                                is UpdateRequested -> +"🔄️"
                                is AccountSnapshot.Error -> "🛑 ${row.state.message}"
                            }
                        }
                    }

                }


            }

        }

}

