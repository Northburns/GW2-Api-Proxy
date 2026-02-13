package northburns.gw2.client.myclient.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration

fun <T> Flow<T>.chunked(
    duration: Duration
): Flow<List<T>> = channelFlow {
    // I used this as source: https://kt.academy/article/s_chunked
    // Should re-implement this: https://github.com/ReactiveX/RxJava/wiki/Phantom-Operators#chunkify
    var chunk = mutableListOf<T>()
    val mutex = Mutex()
    val sendIfNotEmpty = suspend {
        if (chunk.isNotEmpty()) {
            mutex.withLock {
                send(chunk)
                chunk = mutableListOf<T>()
            }
        }
    }

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
        }
    } finally {
        timerJob.cancel()
    }
}
