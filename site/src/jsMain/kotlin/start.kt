package northburns.gw2.site

import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.Hot
import io.kvision.html.ButtonStyle
import io.kvision.html.Div
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.responsiveGridPanel
import io.kvision.panel.root
import io.kvision.panel.tab
import io.kvision.panel.tabPanel
import io.kvision.startApplication
import io.kvision.theme.Theme
import io.kvision.theme.ThemeManager
import io.kvision.utils.useModule

@JsModule("./modules/css/kvapp.css")
external object kvappCss

class App : Application() {
    init {
        useModule(kvappCss)
        ThemeManager.init(Theme.LIGHT, remember = true)
    }

    override fun start() {
        root("kvapp") {
            responsiveGridPanel {
                options(1, 1) {
                    div("1,1")
                }
                options(3, 1) {
                    div("3,1")
                }
                add(Div("2,2"), 2, 2)
                add(Div("3,3"), 3, 3)
            }
            span("Hello world!")
            tabPanel {
                tab {
                    span("HELLO!")
                    button("Push me","ICON", ButtonStyle.DANGER)
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        js("import.meta.webpackHot").unsafeCast<Hot?>() ?: js("import.meta.hot").unsafeCast<Hot?>(),
        CoreModule,
        BootstrapModule,
        BootstrapCssModule,
        // FontAwesomeModule,
        // TomSelectModule,
        // DatetimeModule,
        // BootstrapUploadModule,
    )
}
