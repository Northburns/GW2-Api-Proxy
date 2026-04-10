package northburns.gw2.site.util

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/**
 * This looks like something arrow lib already has :thinking:
 */
fun <T> eitherFrom(canThrow: () -> T): Either<Throwable, T> =
    try {
        canThrow().right()
    } catch (t: Throwable) {
        t.left()
    }