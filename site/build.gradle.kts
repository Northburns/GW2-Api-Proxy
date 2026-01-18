plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kvision)
}

kotlin {
    js(IR) {
        browser {
            useEsModules()
            commonWebpackConfig {
                sourceMaps = false
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
            implementation(libs.kvision)
            implementation(libs.kvision.bootstrap)
            implementation(libs.kvision.i18n)

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
        }
        jsTest.dependencies {
            //implementation("io.kvision:kvision-testutils:$kvisionVersion")
        }
    }
}