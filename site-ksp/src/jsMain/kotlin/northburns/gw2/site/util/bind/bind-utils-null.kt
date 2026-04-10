package northburns.gw2.site.util.bind

import io.kvision.core.Component
import io.kvision.core.Container
import northburns.gw2.client.myclient.log.GoonLog


object NullRenderer {

    fun loading(): Component.(Any?) -> Unit = {
        if (this is Container) +"🔃"
        else GoonLog["NullRenderer"].warn("Can't render anything for null value 🤷")
    }


}

@Suppress("FunctionName")
internal fun <W : Component, N, T1, T2> BindGoonRenderInvoker<T1, W>._internalNullHelper(
    getNullableComponent: (T1) -> N?,
    renderOnNullComponent: W.(N?) -> Unit,
): BindGoonRenderInvoker<T2, W> {
    val self = this
    return BindGoonRenderInvoker {
            removeChildren: Boolean,
            runImmediately: Boolean,
            factory: (W.(T2) -> Unit),
        ->
        self.render(removeChildren = removeChildren, runImmediately = runImmediately) { t ->
            @Suppress("UNCHECKED_CAST")
            if (getNullableComponent(t) == null) renderOnNullComponent(null)
            else factory(t as T2)
        }
    }
}

