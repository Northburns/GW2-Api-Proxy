package northburns.gw2.client.myclient.log

import kotlin.js.Console

external interface GoonConsole : Console {

    fun group(label: String, vararg css: String): GoonConsole
    fun groupCollapsed(label: String, vararg css: String)
    fun groupEnd()

    fun table(o: Any)

    fun time(label: String)
    fun timeEnd(label: String)
    fun timeLog(label: String, vararg valN: Any)
}

val goonConsole: GoonConsole = console.unsafeCast<GoonConsole>()
