package northburns.gw2.site.util.bind

import io.kvision.core.Component
import io.kvision.state.ObservableState
import io.kvision.state.bind
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.app.GoonState
import northburns.gw2.site.util.reselect.ReSelect
import northburns.gw2.site.util.reselect.ReSelectGlobalState


fun <T, W : Component> W.goonBind(
    reselector: ObservableState<GoonState>.() -> ReSelect<GoonState, T>
): BindGoonRenderInvoker<T, W> = goonBind(ReSelectGlobalState.gs, reselector)

fun <T, S : Any, W : Component> W.goonBind(
    state: ObservableState<S>,
    reselector: ObservableState<S>.() -> ReSelect<S, T>,
): BindGoonRenderInvoker<T, W> {
    return BindGoonRenderInvoker {
            removeChildren: Boolean,
            runImmediately: Boolean,
            factory: (W.(T) -> Unit),
        ->
        // TODO Now, we could wrap the factory in a lambda which adds debug information to the page
        bind(state, state.reselector()::select, removeChildren, runImmediately, factory)
    }
}

fun interface BindGoonRenderInvoker<T, W : Component> {
    fun internalRender(
        removeChildren: Boolean,
        runImmediately: Boolean,
        factory: (W.(T) -> Unit),
    ): W

    fun render(
        removeChildren: Boolean = true,
        runImmediately: Boolean = true,
        factory: (W.(T) -> Unit),
    ): W = internalRender(removeChildren, runImmediately) { t ->
        try {
            factory(t)
        } catch (e: Throwable) {
            // TODO render an error box?
            GoonLog["BindGoonRenderInvoker"].error("Render threw an exception. TODO: Render a error box?", e)
            // But as long as we don't do that, let's pass it on:
            throw e
        }
    }

}

