package northburns.gw2.site.util

import io.kvision.state.MutableState

class ValueHolder<S>(
    initialValue: S,
) : MutableState<S> {
    private val observers = mutableListOf<(S) -> Unit>()
    private var value: S = initialValue
        set(value) {
            field = value
            observers.forEach { observer -> observer(value) }
        }

    override fun getState(): S = value
    override fun setState(state: S) {
        value = state
    }

    override fun subscribe(observer: (S) -> Unit): () -> Unit {
        observers += observer
        observer(value)
        return {
            observers -= observer
        }
    }

    companion object {
        fun <S> S.asState(): MutableState<S> = ValueHolder(this)
    }
}
