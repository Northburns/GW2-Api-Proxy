package northburns.gw2.client.myclient.log

import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import northburns.gw2.client.myclient.log.GoonLog.Level.ERROR
import northburns.gw2.client.myclient.log.GoonLog.Level.INFO
import northburns.gw2.client.myclient.log.GoonLog.Level.WARN
import kotlin.time.Duration

internal actual fun createGoonLog(name: String): GoonLog = GoonLogJvm(name)

private class GoonLogJvm(name: String) : GoonLog(name) {
    override fun <T : Any> dir(obj: () -> T) {
        TODO("Not yet implemented")
    }

    override fun <T> table(data: () -> Collection<T>) {
        TODO("Not yet implemented")
    }

    override fun timeOpPre(time: String) {
        TODO("Not yet implemented")
    }

    override fun timeOpPost(time: String, duration: Duration?) {
        TODO("Not yet implemented")
    }

    override fun createTimeLog(time: String): TimeLog {
        TODO("Not yet implemented")
    }

    override fun log(
        level: Level,
        tag: String,
        vararg o: Any?
    ) {
        val priority = when (level) {
            INFO -> LogLevel.INFO
            WARN -> LogLevel.WARNING
            ERROR -> LogLevel.ERROR
        }
        val singleThrowable = o.singleOrNull { it is Throwable } as Throwable?
        val message = o.filterNot { it === singleThrowable }.joinToString(" | ")
        Napier.log(priority, tag, singleThrowable, message)
    }

}
