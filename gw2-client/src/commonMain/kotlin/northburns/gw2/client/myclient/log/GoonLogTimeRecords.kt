package northburns.gw2.client.myclient.log

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Any non-NOP GoonLog records all [GoonLog.timeValue] et al. times here.
 */
object GoonLogTimeRecords {

    private val records = mutableMapOf<String, MutableList<Duration>>()

    fun record(time: String, duration: Duration): Unit {
        records.getOrPut(time) { mutableListOf() }.add(duration)
    }

    class TimeCalc(
        val name: String,
        val count: Int,
        val avg: Duration,
        val stddev: Duration,
        val min: Duration,
        val max: Duration,
    )

    fun calculate() = records.map { (time, durations) ->
        val count = durations.size
        val mean = durations.map { d -> d.inWholeMilliseconds }.average()
        val variance = durations.map { d -> (d.inWholeMilliseconds - mean).pow(2) }.average()
        TimeCalc(
            name = time,
            count = count,
            avg = mean.milliseconds,
            stddev = sqrt(variance).milliseconds,
            min = durations.min(),
            max = durations.max(),
        )
    }

}