package northburns.gw2.site

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapIconsModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.FontAwesomeModule
import io.kvision.Hot
import io.kvision.TabulatorCssBootstrapModule
import io.kvision.TabulatorModule
import io.kvision.startApplication
import northburns.gw2.site.helper.datefns.ChartModuleWithDateFns
import northburns.gw2.site.state.Gw2GoonView.*


@JsModule("modules/css/kvapp.css")
external object kvappCss

@JsModule("pace-progressbar/themes/green/pace-theme-bounce.css")
external object paceProgressbarCss

fun main() {
    Napier.base(DebugAntilog())
    Napier.i("Starting application")
    startApplication(
        ::Gw2GoonApplication,
        js("import.meta.webpackHot").unsafeCast<Hot?>() ?: js("import.meta.hot").unsafeCast<Hot?>(),
        CoreModule,
        BootstrapModule,
        BootstrapCssModule,
        ChartModuleWithDateFns,
        FontAwesomeModule,
        BootstrapIconsModule,
        TabulatorModule,
        TabulatorCssBootstrapModule, // There's other CSS frameworks supported by Tabulator! E.g. Bulma. Cool.
        // TomSelectModule,
        // DatetimeModule,
        // BootstrapUploadModule,
    )
}
