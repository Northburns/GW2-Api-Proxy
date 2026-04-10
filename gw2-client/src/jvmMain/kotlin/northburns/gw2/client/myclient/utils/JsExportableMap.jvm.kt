package northburns.gw2.client.myclient.utils

class JsExportableMapJvm<K, out V>(
    private val backing: Map<K, V>,
) : JsExportableMap<K, V>, Map<K, V> by backing

actual fun <K, V> Map<K, V>.jsExportable(
    key2string: (K) -> String,
    string2key: (String) -> K,
): JsExportableMap<K, V> {
    return JsExportableMapJvm(this)
}
