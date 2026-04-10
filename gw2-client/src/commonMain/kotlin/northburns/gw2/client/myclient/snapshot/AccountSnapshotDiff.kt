package northburns.gw2.client.myclient.snapshot

import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Serializable
data class AccountSnapshotDiff(
    val wallTime: Duration,
    val playTime: Duration?,
    val walletDelta: Map<GW2CurrencyId, Long>,
    val stockDelta: Map<GW2ItemId, Long>,
) {

    companion object {
        fun diff(from: AccountSnapshot, to: AccountSnapshot): AccountSnapshotDiff {
            val wallet = (from.totals.wallet.keys + to.totals.wallet.keys).distinct().associateWith { key ->
                (to.totals.wallet[key] ?: 0) - (from.totals.wallet[key] ?: 0)
            }.filterValues { it != 0L }

            val stock = (from.totals.stock.keys + to.totals.stock.keys).distinct().associateWith { key ->
                (to.totals.stock[key]?.count ?: 0) - (from.totals.stock[key]?.count ?: 0)
            }.filterValues { it != 0L }

            val playTime = (from.account.value?.age to to.account.value?.age).let { (f, t) ->
                if (f != null && t != null) f - t else null
            }?.seconds

            return AccountSnapshotDiff(
                wallTime = to.creationDate - from.creationDate,
                playTime = playTime,
                walletDelta = wallet,
                stockDelta = stock,
            )
        }
    }
}