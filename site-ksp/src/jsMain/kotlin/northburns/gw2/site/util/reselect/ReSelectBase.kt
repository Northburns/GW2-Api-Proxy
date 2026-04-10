package northburns.gw2.site.util.reselect

import io.kvision.core.Component
import northburns.gw2.site.app.GoonState

sealed class ReSelectBase<S : Any, R> : ReSelect<S, R> {
    protected val r = ResultHolder<R>()

    protected class ResultHolder<R> {
        var exists: Boolean = false
        private var result: R? = null

        fun read(): R {
            require(exists) { "Trying to read result without it existing!" }
            @Suppress("UNCHECKED_CAST")
            return result as R
        }

        fun set(r: R): Boolean {
            // If it didn't exist, it changed now. Otherwise, compare to old.
            val changed = if (!exists) true else result != r
            result = r
            exists = true
            return changed
        }
    }

    protected class HashHolder(
        var isNull: Boolean = false,
        var hash: Int = 0,
    ) {
        fun from(o: Any?): Boolean =
            if (o == null) setNull()
            else setHash(o.hashCode())

        private fun setNull(): Boolean = (!isNull).also {
            isNull = true
            hash = 0
        }

        private fun setHash(newHash: Int): Boolean =
            (isNull || hash != newHash).also {
                isNull = false
                hash = newHash
            }
    }

    protected inline infix fun Boolean.store(calc: () -> R): R {
        val changed = this
        if (changed || !r.exists)
            r.set(calc())
        return r.read()
    }

}
