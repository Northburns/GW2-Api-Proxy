package northburns.gw2.site

import io.kvision.CoreModule
import io.kvision.FontAwesomeModule
import io.kvision.Hot
import io.kvision.TabulatorCssSimpleModule
import io.kvision.TabulatorModule
import io.kvision.startApplication
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.helper.datefns.ChartModuleWithDateFns


@JsModule("modules/css/kvapp.css")
external object kvappCss

@JsModule("pace-progressbar/themes/green/pace-theme-bounce.css")
external object paceProgressbarCss

fun main() {
    GoonLog["/"].info("Starting application")
    startApplication(
        //::Gw2GoonApplication,
        ::Gw2GoonApplication,
        js("import.meta.webpackHot").unsafeCast<Hot?>() ?: js("import.meta.hot").unsafeCast<Hot?>(),
        CoreModule,
        // BootstrapModule,
        // BootstrapCssModule,
        // BootstrapIconsModule,
        //TailwindcssModule,
        ChartModuleWithDateFns,
        FontAwesomeModule,
        TabulatorModule,
        // TabulatorCssBootstrapModule, // There's other CSS frameworks supported by Tabulator! E.g. Bulma. Cool.
        TabulatorCssSimpleModule, // There's also TabulatorCssStandardModule
        // TomSelectModule,
        // DatetimeModule,
        // BootstrapUploadModule,
    )
}
