package northburns.gw2.site.helper.redux

import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store
import org.reduxkotlin.TypedDispatcher

object MiddlewareFactory {

    fun <T> middleware(block: (store: Store<T>, next: Dispatcher, action: Any) -> Any): Middleware<T> {
        return { store: Store<T> ->
            { next: Dispatcher ->
                { action: Any ->
                    block(store, next, action)
                }
            }
        }
    }

    fun <T, A> middlewareTyped(block: (store: Store<T>, next: TypedDispatcher<A>, action: A) -> Any): Middleware<T> {
        return middleware { store, next, action ->
            @Suppress("UNCHECKED_CAST")
            block(store, next as TypedDispatcher<A>, action as A)
        }
    }

}
