package northburns.gw2.site.util

import kotlin.reflect.KClass
import kotlin.reflect.typeOf

inline fun jso(init: dynamic.() -> Unit): dynamic {
    val o = js("{}")
    init(o)
    return o
}

inline fun <T> jsObject(init: T.()->Unit) : T {
    val o = js("{}").unsafeCast<T>()
    return o.apply(init)
}

fun dynamicToMap(obj: dynamic): Map<String, dynamic>? {
    if (obj == null) return null
    val keys = js("Object").keys(obj).unsafeCast<Array<String>>()
    return keys.associateWith { obj[it] }
}
