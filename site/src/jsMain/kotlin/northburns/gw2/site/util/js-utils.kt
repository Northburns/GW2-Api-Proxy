package northburns.gw2.site.util

inline fun jso(init: dynamic.() -> Unit): dynamic {
    val o = js("{}")
    init(o)
    return o
}
