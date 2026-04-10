package northburns.gw2.site.util.reselect

import northburns.gw2.ksp.ReSelectGenerate

/**
 * TOOD Unit tests would be nice. And move to commonMain for easier testing.
 */
@ReSelectGenerate(n = 9)
sealed interface ReSelect<S : Any, R> {
    fun select(s: S): R
}
