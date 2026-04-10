package northburns.gw2.client.myclient.utils

import kotlin.time.Clock

fun millisNow(): Long {
    return Clock.System.now().toEpochMilliseconds()
}