package northburns.gw2.site.layout.components

import io.kvision.chart.Chart
import io.kvision.chart.ChartOptions
import io.kvision.chart.ChartScales
import io.kvision.chart.ChartType
import io.kvision.chart.Configuration
import io.kvision.chart.DataSets
import io.kvision.chart.ScalesType
import io.kvision.chart.TickOptions
import io.kvision.chart.js.ChartDatasetProperties
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.helper.datefns.fi
import northburns.gw2.site.util.jso
import kotlin.time.Instant
import kotlin.time.toJSDate

class GoonTimelineChart<DATASET_K : Any, DATASET_V : Any>(
    datasetKeys: List<DATASET_K>,
    dataSets: (DATASET_K) -> DataSets = { key ->
        DataSets(
            label = key.toString(),
            data = emptyList(),
        )
    },
    private val dataExtract: (DATASET_V) -> Pair<Instant, Map<DATASET_K, Number?>>,
) {
    private val datasetIndices: Map<DATASET_K, Int> = datasetKeys.mapIndexed { index, k -> k to index }.toMap()
    val chart: Chart = Chart(
        chartWidth = null,
        chartHeight = null,
        className = null,
        configuration = Configuration(
            type = ChartType.LINE,
            // labels= listOf("Red", "Blue"),
            dataSets = datasetKeys.map(dataSets),
            options = ChartOptions(
                scales = mapOf(
                    "x" to ChartScales(
                        type = ScalesType.TIME,
                        time = jso {
                            //"unit" to "month",
                        },
                        ticks = TickOptions(
                            // source = "data",
                        ),
                        //min = Clock.System.now().minus(5.days).toJSDate(),
                        //max = Clock.System.now().toJSDate(),
                        adapters = jso {
                            date = jso {
                                local = fi
                            }
                        },
                    ),
                    "y" to ChartScales(
                        //beginAtZero = true,
                        //min = 0,
                        //max = 20,
                    ),
                )
            ),
        ),
    )

    fun addValue(v: DATASET_V) {
        GoonLog["GoonTimelineChart"].info("ADDING SNAPSHOT!")

        val (t, values) = dataExtract(v)

        datasetIndices.keys.forEach { key ->
            val value = values[key]
            if (value != null)
                addDatapoint(key, t.toJSDate(), value)
        }

        chart.update()
    }

    private fun addDatapoint(
        datasetKey: DATASET_K,
        datapointT: kotlin.js.Date,
        datapointV: Number,
    ) {
        val nativeConfig = requireNotNull(chart.getNativeConfig()) { "chart.getNativeConfig is null enexpectedly" }
        val nativeData = requireNotNull(nativeConfig.data) { "nativeConfig.data is null enexpectedly" }

        // Get the live data array
        @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
        val data = (nativeData.datasets[datasetIndices.getValue(datasetKey)] as ChartDatasetProperties).data

        // Find index to insert into
        var i = 0
        fun datumBeforeCurrent(index: Int): Boolean {
            return (data.at(index).x as kotlin.js.Date).getTime() < datapointT.getTime()
        }
        while (i < (data.length as Int) && datumBeforeCurrent(i)) {
            i++
        }

        data.splice(i, 0, jso {
            x = datapointT
            y = datapointV
        })
    }

}

