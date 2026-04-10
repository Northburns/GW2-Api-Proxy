package northburns.gw2.client.myclient.utils

import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2Json
import kotlin.js.JsExport
import kotlin.jvm.JvmName

inline fun <reified K,V> jsExportableMapOf(vararg pairs: Pair<K,V>) = mapOf(*pairs).jsExportable()

@JvmName("jsExportable1")
fun <V> Map<String, V>.jsExportable(): JsExportableMap<String, V> = jsExportable(
    key2string = { it },
    string2key = { it },
)

@JvmName("jsExportable2")
inline fun <reified K, V> Map<K, V>.jsExportable(): JsExportableMap<K, V> {
    val s = Gw2Json.json.serializersModule.serializer<K>()
    return this.jsExportable(
        key2string = { k: K -> Gw2Json.json.encodeToString(s, k) },
        string2key = { k: String -> Gw2Json.json.decodeFromString(s, k) },
    )
}

expect fun <K, V> Map<K, V>.jsExportable(
    key2string: (K) -> String,
    string2key: (String) -> K,
): JsExportableMap<K, V>

@JsExport
interface JsExportableMap<K, out V> : Map<K, V> {
    override val size: Int
    override val keys: Set<K>
    override val values: Collection<V>
    override val entries: Set<Map.Entry<K, V>>
    override fun get(key: K): V?

    override fun isEmpty(): Boolean
    override fun containsKey(key: K): Boolean
}
