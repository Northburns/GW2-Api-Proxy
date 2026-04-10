@file:ReSelect2Generate(
    n = 8,
    /*name = "BindBuilderSlicer",
    typeParamTypes = [Component::class, Nothing::class],
    typeParamNames = ["W", "S"],
    typeParamVariance = ["", ""],*/
)
package northburns.gw2.site.util.reselect

import northburns.gw2.ksp.ReSelect2Generate
import northburns.gw2.site.util.reselect.ReSelectBase

//
//package northburns.gw2.site.util.reselect
//
//import io.kvision.core.Component
//import io.kvision.core.Container
//import io.kvision.state.ObservableState
//import northburns.gw2.ksp.ReSelect2Generate
//import northburns.gw2.site.app.GoonState
//import northburns.gw2.site.util.reselect.ReSelect2Defaults.onNullRendererDefault
//
//private typealias OS<S> = ObservableState<S>
//
//
///*
// * ReSelect. Very much inspired by https://github.com/reduxjs/reselect
// *
// * Our version creates instances of (S)->R but memoized, which should be easy
// * to integrate with KVision's bind methods. I'm thinking which one makes more sense:
// * to create these in a centralized place, or in UI creation. I think both are good!
// *
// */
//// DEMO:
//
//fun <W : Component, S : Any> W.x(s: ObservableState<S>) {
//    goonBindTo(s)
//        .slice { s.toString() }
//        .slice { s.toString().length }
//        .select { } // <-- this should be optional!
//        .render { //
//
//        }
//}
//
//
//fun foobar() {
//
//    val builder = ReSelectBuilder(initialSource = GoonState())
//
//    val builder1: SlicerDaisyChainLink<GoonState, GoonState.ApiData, SlicerDaisyChainLink<GoonState, GoonState.ApiData, Nothing>> =
//        builder
//            .slice { it.api }
//            .slice { it.api }
//
//}
//
//// //////////////////////////////////////////////////////////////////////////////
//sealed class ReSelectBase667<S, R>(
//    size: Int,
//    initialSource: S,
//) {
//    private val hash = Array(size) { HashHolder() }
//    private var r: R = calc(initialSource)
//
//    fun select(s: S): R {
//        val old = this.r
//    }
//
//    protected abstract fun calc(s: S): R
//
//    protected infix fun
//
//    protected class HashHolder(
//        var isNull: Boolean = false,
//        var hash: Int = 0,
//    ) {
//        fun from(o: Any?): Boolean =
//            if (o == null) setNull()
//            else setHash(o.hashCode())
//
//        private fun setNull(): Boolean = (!isNull).also {
//            isNull = true
//            hash = 0
//        }
//
//        private fun setHash(newHash: Int): Boolean =
//            (isNull || hash != newHash).also {
//                isNull = false
//                hash = newHash
//            }
//    }
//}
//
//
//
//
//interface ReSelectSlicersHolder<S, TUPLE> {
//    fun <TUPLE2, T> addSlicer(s: (S) -> T): ReSelectSlicersHolder<S, TUPLE2>
//}
//
//
//class ReSelectSlicersHolderEmpty<S> : ReSelectSlicersHolder<S>
//
//class ReSelectBuilder<S, HOLDER : ReSelectSlicersHolder<S, *>>(
//    private val initialSource: S,
//    private val holder: HOLDER,
//) {
//
//    /**
//     * Add new slicer
//     */
//    fun <HOLDER2 : ReSelectSlicersHolder<S, *>, T> slice(s: (S) -> T): ReSelectBuilder<S, HOLDER2> {
//        ReSelectBuilder<S, HOLDER2>(initialSource = initialSource)
//    }
//
//    companion object {
//        fun <S> reselectBuilder(initialSource: S) = ReSelectBuilder(
//            initialSource = initialSource,
//            holder = ReSelectSlicersHolderEmpty<S>(),
//        )
//
//        fun <S> reselectBuilder(initialSource: ObservableState<S>) = reselectBuilder(initialSource.getState())
//    }
//}
//
//interface ReSelect666Slices<S, SELECT> {
//    fun <R> select(select: SELECT): ReSelect666Final<S, R>
//}
//
//interface ReSelect666Final<S, R> {
//    fun select(s: S): R
//}
//
//interface ReSelect666<S, R, SELECT> {
//    fun select(s: S): R
//    fun asLambda(): (S) -> R = ::select
//
//    /**
//     * Create new reselect, with a new slicer added, but set select to undefined state
//     */
//    fun slice(slice:)
//
//    /**
//     * Create new reselect, with new select function
//     */
//    fun <RR> select(select: SELECT): ReSelect666<S, RR, SELECT>
//
//}
//
//
//fun <S> reselect(): ReSelect666<S, S> = TODO("new NOP instance")
//
//
//class SlicerDaisyChainLink<S, T, PREV>(
//    val slicer: (S) -> T,
//    val previous: PREV? = null,
//) {
//    fun <TT> slice(s: (S) -> TT) = SlicerDaisyChainLink<S, TT, SlicerDaisyChainLink<S, T, PREV>>(
//        slicer = s,
//        previous = this,
//    )
//}
//
//
//class ReSelect222<S>(
//    private val sourceState: ObservableState<S>,
//)
//
//fun <S : Any> createSubYoYo(
//    source: OS<S>,
//) {
//    source.subscribe()
//}
//
//interface ReSelect22<S : Any, R> : OS<R> {
//
//}
//
//fun <W : Component, S : Any> W.goonBindTo(s: ObservableState<S>): BindBuilderSlicer<W, S> {
//    val w: W = this
//
//}
//
//interface BindBuilderCanSelect
//
//
//interface BindBuilderSlicerBase<W : Component, S : Any, SELECT : Function<Unit>> {
//    fun <T> slice(slice: (S) -> T): BindBuilderSlicerBase<W, S, SELECT>
//    fun notNull(onNullRenderer: W.() -> Unit = onNullRendererDefault): BindBuilderSlicerBase<W, S, SELECT>
//    // fun select(select: SELECT)
//    // fun render(select: SELECT)
//}
//
//class BindBuilderSlicer0<W : Component, S : Any> : BindBuilderSlicerBase<W, S, (String) -> Unit> {
//    override fun <T> slice(slice: (S) -> T): BindBuilderSlicerBase<W, S, () -> Unit> {
//        TODO("Not yet implemented")
//    }
//
//    override fun select(select: (String) -> Unit) {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun notNull(onNullRenderer: W.() -> Unit): BindBuilderSlicerBase<W, S, () -> Unit> {
//        TODO("Not yet implemented")
//    }
//
//}
//
//interface BindBuilderSlicePrototypeX<S : Any, T1> : BindBuilderCanSelect {
//    fun slice(slice: (S) -> T1): Nothing
//}
//
//fun interface BindGoonRenderInvoker<T, W : Component> {
//    fun internalRender(
//        removeChildren: Boolean,
//        runImmediately: Boolean,
//        runEvenWhenNotChanged: Boolean,
//        factory: (W.(T) -> Unit),
//    ): W
//
//    fun render(
//        removeChildren: Boolean = true,
//        runImmediately: Boolean = true,
//        runEvenWhenNotChanged: Boolean = false,
//        factory: (W.(T) -> Unit),
//    ): W = internalRender(removeChildren, runImmediately, runEvenWhenNotChanged, factory)
//}
//
//
//object ReSelect2Defaults {
//    val onNullRendererDefault: Component.() -> Unit = {
//        require(this is Container) { "TODO Can't do default null render unless a Container" }
//        +"🔄️"
//    }
//}