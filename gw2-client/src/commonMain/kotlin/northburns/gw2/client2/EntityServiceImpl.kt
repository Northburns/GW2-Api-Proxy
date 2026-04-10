package northburns.gw2.client2

import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import northburns.gw2.client.myclient.Gw2Json
import northburns.gw2.client.myclient.cache.KottageStorage
import northburns.gw2.client.myclient.log.GoonLog
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class EntityServiceImpl<K : Any, V : Any>(
    private val scope: CoroutineScope,
    private val kType: KType,
    private val kListSerializer: KSerializer<List<K>>,
    private val kSerializer: KSerializer<K>,
    private val vSerializer: KSerializer<V>,
    private val kListType: KType,
    private val vType: KType,
    private val fetchIds: suspend () -> List<K>,
    private val fetchMany: suspend (keys: List<K>) -> Collection<V>,
    private val keyOf: (V) -> K,
    private val kottage: KottageStorage,
) : MyGw2Client2.EntityService<K, V> {

    private val kottageTyped = KottageTyped<K, V>(
        kottage = kottage,
        kSerializer = kSerializer,
        vSerializer = vSerializer,
    )

    override suspend fun getIds(): List<K> = kottage.get(KEYS_KEY)?.json
        ?.let { Gw2Json.json.decodeFromString(kListSerializer, it) }
        ?: fetchIds().also {
            kottage.set(
                KEYS_KEY,
                Gw2Json.json.encodeToString(kListSerializer, it)
            )
        }

    override suspend fun get(k: K): V = getMany(listOf(k)).single()

    override suspend fun getMany(ks: Collection<K>): Collection<V> {
        if (ks.isEmpty()) return emptyList()

        val (fromCache, missing) = getManyOnlyCached(ks)
        if (missing.isEmpty()) return fromCache

        // {"text":"id list too long; this endpoint is limited to 200 ids at once"}
        val fromApi = missing.chunked(200).flatMap { chunk ->
            try {
                fetchMany(chunk).also { fromApi ->
                    fromApi.forEach { v -> kottageTyped.put(keyOf(v), v) }
                }
            } catch (t: Throwable) {
                GoonLog["EntityServiceImpl"].e { log("Error fetching chunk ($kType):", chunk) }
                throw t
            }
        }
        return fromCache + fromApi
    }

    override suspend fun getManyOnlyCached(ks: Collection<K>): Pair<Collection<V>, Collection<K>> {
        val missing = mutableListOf<K>()
        return ks.distinct().mapNotNull { key ->
            kottageTyped.read(key)
                .also { if (it == null) missing += key }
        } to missing
    }

    override suspend fun getAll(): Map<K, V> {
        return getMany(getIds()).associateBy(keyOf)
    }

    companion object {
        private const val KEYS_KEY = "__keys"

        inline fun <reified K : Any, reified V : Any> CoroutineScope.entityService(
            noinline fetchIds: suspend () -> List<K>,
            noinline fetch: suspend (keys: List<K>) -> Collection<V>,
            noinline keyOf: (V) -> K,
            kottage: KottageStorage,
        ): EntityServiceImpl<K, V> {
            return EntityServiceImpl(
                scope = this,
                fetchIds = fetchIds,
                fetchMany = fetch,
                kottage = kottage,
                kType = typeOf<K>(),
                vType = typeOf<V>(),
                keyOf = keyOf,
                kListType = typeOf<List<K>>(),
                kListSerializer = Gw2Json.json.serializersModule.serializer<List<K>>(),
                kSerializer = Gw2Json.json.serializersModule.serializer<K>(),
                vSerializer = Gw2Json.json.serializersModule.serializer<V>(),
            )
        }
    }

}
