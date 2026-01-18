package northburns.gw2.client.myclient.cache

import com.juul.indexeddb.Key
import com.juul.indexeddb.KeyPath
import com.juul.indexeddb.bound
import com.juul.indexeddb.logs.PrintLogger
import com.juul.indexeddb.openDatabase

actual class MyCacheStorage {
    actual fun foo() {

    }

    private class Foobar {

    }

    suspend fun bar() {
        val database = openDatabase("tietokanta", 1, PrintLogger) { database, oldVersion, newVersion ->
            if (oldVersion < 1) {
                val store = database.createObjectStore("customers", KeyPath("ssn"))
                store.createIndex("name", KeyPath("name"), unique = false)
                store.createIndex("age", KeyPath("age"), unique = false)
                store.createIndex("email", KeyPath("email"), unique = true)
            }
        }
        database.writeTransaction("customers") {
            val store = objectStore("customers")
            store.add(Foobar())
        }
        val bill = database.transaction("customers") {
            objectStore("customers").get(Key("444-44-4444")) as Foobar
        }
        val donna = database.transaction("customers") {
            objectStore("customers").index("age").get(bound(30, 32)) as Foobar
        }
    }
}
