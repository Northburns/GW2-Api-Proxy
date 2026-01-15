package fi.northburns

import northburns.gw2.client.myclient.MyGw2Client

fun main() {

    val c = MyGw2Client.create()
    val b = c.general.build.get()
    println(b)

    val ms = c.general.mountsSkins.getAll()
    println(ms)

    TODO("Hello World!")
}
