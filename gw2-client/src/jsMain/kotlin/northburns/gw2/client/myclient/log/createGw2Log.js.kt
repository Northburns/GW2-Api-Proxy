package northburns.gw2.client.myclient.log

import northburns.gw2.client.myclient.log.GoonLog.Level.ERROR
import northburns.gw2.client.myclient.log.GoonLog.Level.INFO
import northburns.gw2.client.myclient.log.GoonLog.Level.WARN
import kotlin.time.Duration


internal actual fun createGoonLog(name: String): GoonLog = GoonLogJs(name)

private class GoonLogJs(name: String) : GoonLog(name) {
    private val c: GoonConsole = goonConsole

    override fun log(
        level: Level,
        tag: String,
        vararg o: Any?
    ) {
        require(o.isNotEmpty()) { "Log must not be empty, calling log \"$tag\"" }
        val t = "[$tag]"
        when (level) {
            INFO -> c.info(t, *o)
            WARN -> c.warn(t, *o)
            ERROR -> c.error(t, *o)
        }
    }

    // TODO https://developer.mozilla.org/en-US/docs/Web/API/Performance
    // TODO Also, if several times with same label -> bad time

    override fun timeOpPre(time: String) = c.time(time)

    override fun timeOpPost(time: String, duration: Duration?) {
        if (duration != null) c.timeEnd(time)
    }

    override fun createTimeLog(time: String) = TimeLog { c.timeLog(time, *it) }

    override fun <T : Any> dir(obj: () -> T) = c.dir(obj())

    override fun <T> table(data: () -> Collection<T>): Unit =
        c.table(data().toTypedArray())


}
