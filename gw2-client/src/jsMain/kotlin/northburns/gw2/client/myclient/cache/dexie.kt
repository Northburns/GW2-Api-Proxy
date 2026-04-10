package northburns.gw2.client.myclient.cache

import kotlin.js.Promise


@JsModule("dexie")
@JsNonModule
external class Dexie(name: String) {
    fun version(v: Int): DexieVersion
    fun <T> table(name: String): DexieTable<T>
}

external class DexieVersion {
    fun stores(schema: dynamic): DexieVersion
}

external class DexieTable<T> {
    fun get(key: String): Promise<T?>
    fun bulkGet(keys: Array<String>): Promise<Array<T?>>
    fun put(item: T): Promise<dynamic>
    fun bulkPut(items: Array<T>): Promise<dynamic>
    fun delete(key: String): Promise<Unit>
    fun where(field: String): DexieWhereClause<T>
    fun clear(): Promise<Unit>
}

external class DexieWhereClause<T> {
    fun below(value: dynamic): DexieCollection<T>
}

external class DexieCollection<T> {
    fun delete(): Promise<Unit>
    fun count(): Promise<Int>
}
