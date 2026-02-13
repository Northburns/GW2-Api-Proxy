package northburns.gw2.site.helper

import io.kvision.chart.ChartOptions
import io.kvision.chart.ChartScales
import io.kvision.chart.ChartType
import io.kvision.chart.Configuration
import io.kvision.chart.DataSets
import io.kvision.chart.ScalesType
import io.kvision.chart.chart
import io.kvision.core.Container
import northburns.gw2.site.helper.datefns.fi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

fun Container.chartFoobar() {
    chart(
        Configuration(
            type = ChartType.LINE,
            // labels=listOf("Africa", "Asia"),
            dataSets = listOf(
                DataSets(
                    label = "Demo",
                    data = listOf(
                        mapOf("t" to Clock.System.now(), "y" to 6),
                        mapOf("t" to Clock.System.now().plus(1.seconds), "y" to 12),
                        mapOf("t" to Clock.System.now().plus(2.seconds), "y" to 19),
                        mapOf("t" to Clock.System.now().plus(3.seconds), "y" to 13),
                        mapOf("t" to Clock.System.now().plus(4.seconds), "y" to 7),
                    )
                )
            ),
            options = ChartOptions(
                scales = mapOf(
                    "xAxes" to ChartScales(
                        type = ScalesType.TIME,
                        adapters = mapOf(
                            "date" to mapOf(
                                "local" to fi,
                            ),
                        ),
                    )
                ),
            ),
        )
    )

}