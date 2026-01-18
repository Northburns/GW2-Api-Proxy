package northburns.gw2

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import northburns.gw2.client.myclient.MyGw2Client

suspend fun main() {
    Napier.base(DebugAntilog())

    val c = MyGw2Client.create()
    val b = c.general.build.get()
    println(b)

    val ms = c.general.mountsSkins.getAll()
    println(ms)

    TODO("Hello World!")
}
