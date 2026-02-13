plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kvision)
}

kotlin {
    js(IR) {
        browser {
            useEsModules()
            commonWebpackConfig {
                // https://kvision.gitbook.io/kvision-guide/1.-getting-started-1/debugging
                sourceMaps = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
        }
    }
    sourceSets {
        jsMain.dependencies {
            implementation(project(":gw2-client"))

            implementation(libs.kvision)
            implementation(libs.kvision.bootstrap)
            implementation(libs.kvision.i18n)
            implementation(libs.kvision.navigo)
            implementation(libs.kvision.redux)
            implementation(libs.kvision.pace)
            implementation(libs.kvision.chart)
            implementation(libs.kvision.tabulator)
            implementation(libs.kvision.state)
            implementation(libs.kvision.icons.fontawesome)
            implementation(libs.kvision.icons.bootstrap)

            implementation(libs.napier)

            // https://kvision.gitbook.io/kvision-guide/1.-getting-started-1/modules
            // I'm interested in these:
            // - kvision-datetime
            // - kvision-tom-select
            // - kvision-chart
            // - kvision-pace
            // - kvision-print NICE!
            // - kvision-routing-navigo-ng
            // - kvision-rest (this or Ktor client which we already have)
            // - kvision-state

            // https://github.com/rjaros/kvision/blob/9.3.1/gradle/libs.versions.toml#L35
            // https://github.com/chartjs/chartjs-adapter-date-fns
            implementation(npm("date-fns", "4.1.0"))
            implementation(npm("chartjs-adapter-date-fns", "3.0.0"))
        }
        jsTest.dependencies {
            //implementation("io.kvision:kvision-testutils:$kvisionVersion")
        }
    }
}