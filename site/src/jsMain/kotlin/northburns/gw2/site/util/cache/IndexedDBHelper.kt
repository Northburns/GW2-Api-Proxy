package northburns.gw2.site.util.cache

import kotlinx.browser.window
import web.idb.IDBFactory
import web.idb.databases
import web.navigator.Navigator
import web.storage.estimate
import kotlin.math.roundToInt

object IndexedDBHelper {
    private val indexedDB: IDBFactory
        get() = window.asDynamic().indexedDB as IDBFactory

    private val navigator: Navigator
        get() = window.asDynamic().navigator as Navigator

    class StorageRaport(
        val megabytes: Int?,
        val dbs: List<DatabaseInfo>,
    )

    class DatabaseInfo(
        val name: String?,
        val version: Double?,
    )


    suspend fun read(): StorageRaport {
        val mbs = navigator.storage.estimate().usage?.div(1024 * 1024)
        val dbs = indexedDB.databases()
            .map { i ->
                DatabaseInfo(
                    name = i.name,
                    version = i.version,
                )
            }

        return StorageRaport(mbs?.roundToInt(), dbs)
    }

    suspend fun deleteAll() {
        indexedDB.databases().forEach { db ->
            db.name?.let { name ->
                indexedDB.deleteDatabase(name)
            }
        }
    }
}