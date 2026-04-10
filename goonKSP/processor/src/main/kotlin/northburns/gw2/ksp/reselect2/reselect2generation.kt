package northburns.gw2.ksp.reselect2

import northburns.gw2.ksp.ReSelect2Generate


class ReSelect2GenerateOptions(
    val ann : ReSelect2Generate,
) {

    

    companion object {
        fun ReSelect2Generate.toOptions() = ReSelect2GenerateOptions(this)
    }
}