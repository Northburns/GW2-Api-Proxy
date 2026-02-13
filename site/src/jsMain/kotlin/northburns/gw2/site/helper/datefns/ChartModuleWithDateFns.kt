package northburns.gw2.site.helper.datefns

import io.kvision.ModuleInitializer

// useModule these, so they're not shaken off.

@JsModule("date-fns")
external object dateFns
@JsModule("chartjs-adapter-date-fns")
external object chartjsAdapterDateFns

object ChartModuleWithDateFns : ModuleInitializer{
    override fun initialize() {
        io.kvision.ChartModule.initialize()
        js("import('date-fns')")
        js("import('chart.js')")
        js("import('chartjs-adapter-date-fns')")
    }

}
