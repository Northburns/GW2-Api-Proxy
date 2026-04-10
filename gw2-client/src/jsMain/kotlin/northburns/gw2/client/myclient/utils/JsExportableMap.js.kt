package northburns.gw2.client.myclient.utils

actual fun <K, V> Map<K, V>.jsExportable(
    key2string: (K) -> String,
    string2key: (String) -> K,
): JsExportableMap<K, V> {
    val jsMap = js("Object").create(this)
    forEach { (k, v) -> jsMap[key2string(k)] = v }
    return jsMap
}
