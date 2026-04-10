package northburns.gw2.client.myclient.log

import kotlin.time.Duration
import kotlin.time.measureTimedValue

abstract class GoonLog(
    private val name: String,
) {
    fun i(op: Logger.() -> Unit) = Logger { log(Level.INFO, name, *it) }.op()
    fun w(op: Logger.() -> Unit) = Logger { log(Level.WARN, name, *it) }.op()
    fun e(op: Logger.() -> Unit) = Logger { log(Level.ERROR, name, *it) }.op()

    fun info(vararg o: Any?) = log(Level.INFO, name, *o)
    fun warn(vararg o: Any?) = log(Level.WARN, name, *o)
    fun error(vararg o: Any?) = log(Level.ERROR, name, *o)

    fun time(time: String = name, op: TimeLog.() -> Unit): Unit = timeOp(time) { op(it) }
    fun <T> timeValue(time: String, op: TimeLog.() -> T): T = timeOp(time) { op(it) }
    suspend fun time(time: String = name, op: suspend TimeLog.() -> Unit): Unit = timeOp(time) { op(it) }
    suspend fun <T> timeValue(time: String, op: suspend TimeLog.() -> T): T = timeOp(time) { op(it) }

    abstract fun <T : Any> dir(obj: () -> T)
    abstract fun <T> table(data: () -> Collection<T>)

    protected abstract fun timeOpPre(time: String)
    protected abstract fun timeOpPost(time: String, duration: Duration?)
    protected abstract fun createTimeLog(time: String): TimeLog

    protected inline fun <T> timeOp(time: String, op: (TimeLog) -> T): T {
        timeOpPre(time)
        try {
            val (value, duration) = measureTimedValue { op(createTimeLog(time)) }
            GoonLogTimeRecords.record(time, duration)
            timeOpPost(time, duration)
            return value
        } catch (t: Throwable) {
            timeOpPost(time, null)
            throw t
        }
    }

    protected abstract fun log(level: Level, tag: String, vararg o: Any?)

    fun interface Logger {
        fun log(vararg o: Any?)
    }

    /**
     * Used by [timeValue], you can call [timeLog] to print the current elapsed time,
     * without stopping the timer.
     */
    fun interface TimeLog {
        fun timeLog(vararg valN: Any)
    }

    protected enum class Level { INFO, WARN, ERROR }

    companion object {
        operator fun get(name: String): GoonLog = loggers.getOrPut(name) { createLogger(name) }

        private val loggers = mutableMapOf<String, GoonLog>()
        private fun createLogger(name: String): GoonLog = when (name) {
            "Logger-We-Want-To-Silence" -> NopLogger
            else -> createGoonLog(name)
        }

        private object NopLogger : GoonLog("NOP") {
            override fun log(level: Level, tag: String, vararg o: Any?) = Unit
            override fun <T> table(data: () -> Collection<T>) = Unit
            override fun <T : Any> dir(obj: () -> T) = Unit
            override fun timeOpPre(time: String) = Unit
            override fun timeOpPost(time: String, duration: Duration?) = Unit
            override fun createTimeLog(time: String): TimeLog = nopTimeLog

            private val nopTimeLog = TimeLog { }
        }
    }


}
