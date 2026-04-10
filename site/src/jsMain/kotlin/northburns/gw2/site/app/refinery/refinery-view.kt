package northburns.gw2.site.app.refinery

import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.v2.GW2v2CommercePrices
import com.gw2tb.gw2api.types.v2.GW2v2Item
import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.html.TextNode
import io.kvision.html.h1
import io.kvision.html.h2
import io.kvision.html.p
import io.kvision.state.bind
import io.kvision.tabulator.Align
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Formatter
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.client.myclient.data.Gw2Image.Placeholder
import northburns.gw2.client.myclient.data.HomesteadRefinementRecipes
import northburns.gw2.client.myclient.data.HomesteadRefinementRecipes.HomesteadRefinementRecipe
import northburns.gw2.client.myclient.snapshot.AccountSnapshot
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.layout.components.CoinComponent
import kotlin.math.max

fun Container.refinery() {


    h1("hello refinery kt!")
    p {
        +"Cheapest (sell price) of materials to buy all Homestead refinements for the week. "
        +"We assume your efficiency tiers for all are maxed out. "
        +"For convenience, owned materials are shown as well."
    }





    refineryInternalTable(
        name = "Farm",
        recipes = HomesteadRefinementRecipes.farm,
        maxTransactions = 800,
        maxPerTransaction = 100,
    )


    refineryInternalTable(
        name = "Lumber Mill",
        recipes = HomesteadRefinementRecipes.mill,
        maxTransactions = 800,
        maxPerTransaction = 250,
    )

    refineryInternalTable(
        name = "Forge",
        recipes = HomesteadRefinementRecipes.forge,
        maxTransactions = 800,
        maxPerTransaction = 250,
    )


}

fun Container.refineryInternalTable(
    name: String,
    recipes: List<HomesteadRefinementRecipe>,
    maxTransactions: Int,
    maxPerTransaction: Int,
) {

    h2(name)
    p {
        +"You can do a total max of $maxTransactions transactions, and max $maxPerTransaction per item."
    }

    val itemIds = recipes.map { it.item }.toSet()
    val data: List<RefineRow> = recipes.map {
        RefineRow(
            id = it.item.raw,
            count = it.count,
            materials = it.materials,
            itemIcon = Placeholder.src(16)
        )
    }

    val tabulator = tabulator(
        data = data,
        options = TabulatorOptions(
            // data = data.toTypedArray(),
            index = "id",
            layout = Layout.FITDATATABLE,
            autoResize = true,
            columns = listOf(
                ColumnDefinition(
                    title = "",
                    field = "itemIcon",
                    headerSort = false,
                    formatter = Formatter.IMAGE,
                ),
                ColumnDefinition(
                    title = "Item",
                    field = "itemName",
                    formatterComponentFunction = { _, _, data ->
                        //ItemComponent(data.item)
                        // ItemComponent(data.item)
                        Span(data.itemName)
                    },
                ),
                ColumnDefinition(
                    title = "Count",
                    field = "count",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    // align = Align.RIGHT,
                    formatterComponentFunction = { _, _, data ->
                        val c = data.count
                        Span("x${c}")
                    },
                ),
                ColumnDefinition(
                    title = "price / Transaction (Sell)",
                    field = "sellPerTransaction",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    formatterComponentFunction = { _, _, data ->
                        //Span("=${JSON.stringify(data)}")
                        data.sellPerTransaction?.let(::CoinComponent) ?: TextNode("🔄️")
                    },
                ),
                ColumnDefinition(
                    title = "Mats",
                    field = "materials",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    formatterComponentFunction = { _, _, data ->
                        Span("x${data.materials}")
                    },
                ),
                ColumnDefinition(
                    title = "Max",
                    field = "countToMax",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                ),
                ColumnDefinition(
                    title = "Own",
                    field = "itemOwned",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                ),
                ColumnDefinition(
                    title = "To buy",
                    field = "itemToBuy",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                ),
                ColumnDefinition(
                    title = "Price to buy (Sell)",
                    field = "itemToBuySell",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    formatterComponentFunction = { _, _, data ->
                        data.itemToBuySell?.let(::CoinComponent) ?: TextNode("🔄️")
                    },
                ),
                ColumnDefinition(
                    title = "Prio",
                    field = "priority",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                ),


                )
        ),
        serializer = serializer(),
        serializersModule = Gw2Json.json.serializersModule,
    ) {

    }

    fun updateData(
        account: AccountSnapshot?,
        items: Map<GW2ItemId, GW2v2Item>,
        prices: Map<GW2ItemId, GW2v2CommercePrices>
    ) {
        // Mutate the internal data
        data.forEach { d ->
            val itemId = GW2ItemId(d.id)
            val item = items[itemId]
            d.sellPerItem = prices[itemId]?.sells?.unitPrice
            d.sellPerTransaction = d.sellPerItem?.times(d.count)
            d.sellPerMaterial = d.sellPerTransaction?.div(d.materials)
            d.itemName = item?.name
            d.itemIcon = item.gw2Image().src(16)

            val countToMax = d.count * maxPerTransaction
            d.countToMax = countToMax
            d.itemOwned = account?.totals?.stock[itemId]?.count
            d.itemToBuy = d.itemOwned?.let { max(0, countToMax - it) }
            d.itemToBuySell = d.sellPerItem?.let { sellPerItem -> d.itemToBuy?.times(sellPerItem) }
        }
        data.sortedWith(
            compareBy({ -it.materials }, { it.itemToBuySell })
        ).forEachIndexed { index, row ->
            row.priority = if (row.itemToBuySell != null) index + 1 else null
        }

        // Set data (updateData could be better, but let's do this way for now)
        tabulator.setData(data.toTypedArray())

    }

    tabulator.addAfterInsertHook {
        // Napier.e("after insert")
        tabulator.jsTabulator!!.on("tableBuilt") {
            tabulator.bind(
                observableState = Gw2GoonManager.goonStore,
                sub = { state ->
                    Triple(
                        state.snapshots.snapshots[state.players!!.first()],
                        state.api.items.filterKeys(itemIds::contains),
                        state.api.prices.filterKeys(itemIds::contains),
                    )
                },
                removeChildren = false,
                factory = { (account, items, prices) ->
                    updateData(account, items, prices)
                },
            )
            Gw2GoonManager.r.items.request(itemIds)
            Gw2GoonManager.r.tradingpostPrices.request(itemIds)
            Gw2GoonManager.api.requestSnapshot(PlayerId("northburns"))
        }
        tabulator.jsTabulator!!.on("dataChanged") {
            tabulator.redraw() // This doesn't work?...
        }
    }

}

@Serializable
@JsExport
data class RefineRow(
    val id: Long,
    val count: Int,
    val materials: Int,
    var itemName: String? = null,
    var itemIcon: String,
    var sellPerItem: Long? = null,
    var sellPerTransaction: Long? = null,
    var sellPerMaterial: Long? = null,
    var countToMax: Int? = null,
    var itemOwned: Long? = null,
    var itemToBuy: Long? = null,
    var itemToBuySell: Long? = null,
    var priority: Int? = null,
)
