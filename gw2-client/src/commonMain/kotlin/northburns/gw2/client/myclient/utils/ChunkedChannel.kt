package northburns.gw2.client.myclient.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.log.GoonLog
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class ChunkedChannel<T : Any>(
    scope: CoroutineScope,
    name: String,
    chunkSize: Int = 100,
    chunkDuration: Duration = 1_000.milliseconds,
    distinctSelector: (T) -> Any = { it },
    private val logger: GoonLog = GoonLog["ChunkedChannel.$name"],
    private val onErrorT: (List<T>, Throwable?) -> Unit = { input, t ->
        logger.error("input:", input, t?.message, t?.stackTraceToString(), t)
    },
    private val onError: (Throwable?) -> Unit = { t ->
        logger.error(t?.message, t?.stackTraceToString(), t)
    },
    private val collector: suspend (List<T>) -> Unit,
) {
    private val channel = Channel<T>(Channel.UNLIMITED)

    operator fun invoke(element: T) = request(element)
    fun request(element: T) = channel.trySend(element).onFailure(onError)
    operator fun invoke(elements: Collection<T>) = request(elements)
    fun request(elements: Collection<T>) = elements.forEach { request(it) }

    init {
        scope.launch {
            channel.consumeAsFlow()
                .chunked(chunkSize, chunkDuration)
                .collect {
                    try {
                        if (it.isNotEmpty()) collector(it.distinctBy(distinctSelector))
                    } catch (e: Throwable) {
                        onErrorT(it, e)
                    }
                }
        }.invokeOnCompletion {
            channel.close()
        }
    }

    companion object {
        fun ChunkedChannel<Unit>.request() = request(Unit)
    }

}
