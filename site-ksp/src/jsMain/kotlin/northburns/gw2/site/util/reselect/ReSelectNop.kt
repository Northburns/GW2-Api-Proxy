package northburns.gw2.site.util.reselect

class ReSelectNop<S : Any, R>(
    private val result: (S) -> R
) : ReSelect<S, R> {
    override fun select(s: S): R = result(s)
}
