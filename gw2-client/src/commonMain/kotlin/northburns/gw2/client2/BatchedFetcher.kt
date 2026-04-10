package northburns.gw2.client2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.client.myclient.utils.chunked
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class BatchedFetcher<K : Any, V : Any>(
    scope: CoroutineScope,
    chunkDuration: Duration = 1_000.milliseconds,
    private val onError: (Throwable?) -> Unit = {
        GoonLog["BatchedFetcher"].error(
            "Error in BatchedFetcher: " + (it?.message ?: "") + " " + it?.stackTraceToString(),
            it,
        )
    },
    private val keyOf: (V) -> K,
    private val fetch: suspend (keys: List<K>) -> Collection<V>,
) {

    // The method called by Store - At this point the new value is wanted
    fun requestFetchedValue(k: K): Flow<V> {
        GoonLog["BatchedFetcher"].e { "Requesting fetched value for key $k" }
        val flow = getOrCreateItemFlow(k)
        val result = channelRequest.trySend(k)
        if (result.isClosed || result.isFailure) throw result.exceptionOrNull() ?: IllegalStateException("")
        return flow
            // StateFlow is already hot and shared, no need for shareIn!
            .filterNotNull()
    }

    companion object {
        fun <K : Any, V : Any> CoroutineScope.batchedFetcher(
            keyOf: (V) -> K,
            fetch: suspend (keys: List<K>) -> Collection<V>,
        ) =
            BatchedFetcher(scope = this, keyOf = keyOf, fetch = fetch)
    }

    // ==============================================================================

    private val channelRequest = Channel<K>(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    init {
        scope.launch {
            channelRequest.consumeAsFlow()
                .chunked(10, chunkDuration)
                .collect { keys: List<K> ->
                    try {
                        if (keys.isNotEmpty()) fetchBatch(keys.distinct())
                    } catch (e: Throwable) {
                        onError(e)
                    }
                }
        }.invokeOnCompletion {
            channelRequest.close()
        }
    }

    // getOrPut doesn't suspend, so it's "atomic" in JS
    private fun getOrCreateItemFlow(k: K) = itemFlows.getOrPut(k) { MutableStateFlow(null) }
    private val itemFlows = mutableMapOf<K, MutableStateFlow<V?>>()

    private suspend fun fetchBatch(keys: List<K>) {
        fetch(keys).forEach { v ->
            val k = keyOf(v)
            itemFlows[k]?.emit(v)
        }
    }

}
