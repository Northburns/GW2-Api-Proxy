package northburns.gw2.site.app.sessiontracker

import io.kvision.core.Container
import io.kvision.form.select.select
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.h1
import io.kvision.html.li
import io.kvision.html.span
import io.kvision.html.ul
import io.kvision.state.bind
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.snapshot.AccountSnapshotDiff
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.actions.SnapshotIdSelected

fun Container.sessiontracker() {

    h1("Simple Session Tracker")

    bind(goonStore, { it.players?.first() }) { currentPlayer ->
        if (currentPlayer == null) {
            span("Select player.")
            return@bind
        }

        div().bind(goonStore, { it.snapshotIds to it.sessiontracker }) { (snapshotIds, state) ->
            val keys = snapshotIds.entries.sortedBy { it.value.creationDate }
                .map { Gw2Json.json.encodeToString(it.key) to it.value }
            val snapshots = keys.toMap()

            select(
                options = keys.map { k -> k.first to snapshots.getValue(k.first).displayName },
            ).subscribe { snapshotId ->
                if (snapshotId != null)
                    goonStore.dispatch(SnapshotIdSelected(currentPlayer, snapshots.getValue(snapshotId).id))
            }

            span().bind(goonStore, { it.sessiontracker.fromSnapshot }) { snapshot ->
                content = if (snapshot == null) "No base snapshot in memory" else "✅"
            }

            button("Load current snapshot").onClick {
                Gw2GoonManager.api.requestSnapshot(PlayerId("northburns"))
            }

            span().bind(goonStore, { it.snapshots.snapshots[currentPlayer] }) { snapshot ->
                content = if (snapshot == null) "No current snapshot in memory" else "✅"
            }
        }

        div().bind(
            goonStore,
            { it.sessiontracker.fromSnapshot to it.snapshots.snapshots[currentPlayer] }) { (from, to) ->
            if (from == null || to == null) span("...")
            else buildDiff(AccountSnapshotDiff.diff(from, to))
        }

        Gw2GoonManager.api.requestSnapshotIds(currentPlayer)
    }

}

private fun Container.buildDiff(diff: AccountSnapshotDiff) {

    div {
        +"Wall Time: ${diff.wallTime}"
    }
    div {
        +"Play Time: ${diff.playTime}"
    }

    ul {
        diff.walletDelta.forEach { (key, delta) ->
            li("$key -> $delta")
        }
    }


    ul {
        diff.stockDelta.forEach { (key, delta) ->
            li("$key -> $delta")
        }
    }

    span("HELLO DIFF!")
}
