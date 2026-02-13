package northburns.gw2.site.layout

import com.gw2tb.gw2api.types.GW2ItemId
import io.kvision.core.Container
import io.kvision.html.Span
import io.kvision.html.h1
import io.kvision.html.p
import io.kvision.tabulator.Align
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.data.HomesteadRefinementRecipes
import northburns.gw2.client.myclient.data.HomesteadRefinementRecipes.HomesteadRefinementRecipe
import northburns.gw2.site.layout.components.ItemComponent
import northburns.gw2.site.layout.components.ItemComponentFactory

fun Container.refinery() {

    val icf = ItemComponentFactory()
    val itemIds = mutableSetOf<GW2ItemId>()

    h1("hello refinery kt!")
    p {
        +"Cheapest (sell price) of materials to buy all Homestead refinements for the week. "
        +"We assume your efficiency tiers for all are maxed out. "
        +"For convenience, owned materials are shown as well."
    }

    tabulator(
        data = HomesteadRefinementRecipes.forge,
        options = TabulatorOptions(
            columns = listOf(
                ColumnDefinition(
                    title = "Item",
                    field = "item",
                    headerSort = false,
                    formatterComponentFunction = { _, _, data: HomesteadRefinementRecipe ->
                        ItemComponent(data.item)
                    },
                ),
                ColumnDefinition(
                    title = "Count",
                    field = "count",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    // align = Align.RIGHT,
                    formatterComponentFunction = { _, _, data ->
                        Span("x${data.count}")
                    }
                ),
                ColumnDefinition(
                    title = "Sell price / Item",
                    hozAlign = Align.RIGHT,
                    headerHozAlign = Align.RIGHT,
                    mutator = { value, data, type, params, cell -> "MUTATED" },
                    mutatorData = { value, data, type, params, cell -> "MUTATEDDATAE" },
                    formatterComponentFunction = { _, _, data ->
                        Span("x${data.count}")
                    }
                ),
            )
        ),
        serializer = serializer(),
    ) {

        HomesteadRefinementRecipes.forge
    }

}

@Serializable
data class RefineRow(
    val out: Int,
)
