package northburns.gw2

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.runBlocking
import northburns.gw2.client.myclient.MyGw2Client
import northburns.gw2.client.myclient.PlayerId

fun main(): Unit = runBlocking {
    Napier.base(DebugAntilog())


    val c = MyGw2Client.create(this)
    val b = c.general.build.get()
    println(b)

    val ms = c.general.mountsSkins.getAll()
    println(ms.size)

    val acc = c.authenticated.getValue(PlayerId("aki")).account.get()
    println(acc)

}
