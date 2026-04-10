package northburns.gw2.client.myclient.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration

fun <T> Flow<T>.chunked(
    chunkSize: Int,
    duration: Duration
): Flow<List<T>> = channelFlow {
    // I used this as source: https://kt.academy/article/s_chunked
    // Should re-implement this: https://github.com/ReactiveX/RxJava/wiki/Phantom-Operators#chunkify
    var chunk = mutableListOf<T>()
    val mutex = Mutex()

    val sendIf = suspend { b: Boolean ->
        if (b) {
            mutex.withLock {
                send(chunk)
                chunk = mutableListOf<T>()
            }
        }
    }
    val sendIfBigEnough = suspend { sendIf(chunk.size >= chunkSize) }
    val sendIfNotEmpty = suspend { sendIf(chunk.isNotEmpty()) }

    val timerJob = launch {
        try {
            while (true) {
                delay(duration)
                sendIfNotEmpty()
            }
        } finally {
            sendIfNotEmpty()
        }
    }

    try {
        collect {
            chunk.add(it)
            sendIfBigEnough()
        }
    } finally {
        timerJob.cancel()
    }
}

/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/src/channels/TickerChannels.kt#L58
 */
fun CoroutineScope.myTicker(
    delayMillis: Long,
    initialDelayMillis: Long = delayMillis,
    context: CoroutineContext = EmptyCoroutineContext,
): ReceiveChannel<Unit> {
    require(delayMillis >= 0) { "Expected non-negative delay, but has $delayMillis ms" }
    require(initialDelayMillis >= 0) { "Expected non-negative initial delay, but has $initialDelayMillis ms" }

    @OptIn(ExperimentalCoroutinesApi::class)
    return produce(context = Dispatchers.Unconfined + context, capacity = Channel.RENDEZVOUS) {
        var deadline = millisNow() + initialDelayMillis
        delay(initialDelayMillis)
        while (true) {
            deadline += delayMillis
            channel.send(Unit)
            val now = millisNow()
            val nextDelay = (deadline - now).coerceAtLeast(0)
            if (nextDelay == 0L && delayMillis != 0L) {
                val adjustedDelay = delayMillis - (now - deadline) % delayMillis
                deadline = now + adjustedDelay
                delay(adjustedDelay)
            } else {
                delay(nextDelay)
            }
        }
    }
}