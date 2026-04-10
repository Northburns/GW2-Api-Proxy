plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kvision)
}



kotlin {
    compilerOptions {
        optIn.add("kotlin.js.ExperimentalJsExport")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")

        // https://kotlinlang.org/docs/compiler-reference.html#xes-long-as-bigint
        // freeCompilerArgs.add("-Xes-long-as-bigint")
        // TypeError: Do not know how to serialize a BigInt
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/BigInt#use_within_json
    }
    js(IR) {
        browser {
            useEsModules()

            // https://kotlinlang.org/docs/js-project-setup.html#building-executables
            // all `.js` files in `webpack.config.d`
            // merged to `build/js/packages/projectName/webpack.config.js`
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
        val jsMain by getting {
            // Force resources from the dependency to be included
            resources.srcDir(project(":gw2-client").file("src/commonMain/resources"))
            // Is a temporary solution.

        }
        jsMain.dependencies {
            implementation(project(":gw2-client"))
            implementation(project(":site-ksp"))

            implementation(libs.kotlin.wrapper.browser)

            implementation(libs.kvision)
            // implementation(libs.kvision.bootstrap)
            // implementation(libs.kvision.tailwindcss) We do tailwind ourselves
            implementation(libs.kvision.i18n)
            implementation(libs.kvision.navigo)
            implementation(libs.kvision.redux)
            implementation(libs.kvision.pace)
            implementation(libs.kvision.chart)
            implementation(libs.kvision.tabulator)
            implementation(libs.kvision.state)
            implementation(libs.kvision.icons.fontawesome)
            implementation(libs.kvision.icons.bootstrap)

            implementation(project(":markdown-generator"))

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

            implementation(npm("@gw2me/client", "0.9.3"))

            // Tailwind https://tailwindcss.com/docs/installation/using-postcss
            //  tailwindcss @tailwindcss/postcss postcss
            implementation(npm("tailwindcss", "4.2.1"))
            implementation(devNpm("@tailwindcss/webpack", "4.2.1"))
            implementation(devNpm("mini-css-extract-plugin", "2.10.0"))
            //implementation(npm("@tailwindcss/postcss","4.2.1" ))
            //implementation(npm("postcss-loader","8.2.1" ))
            //implementation(npm("postcss","8.5.8" ))

        }
        jsTest.dependencies {
            //implementation("io.kvision:kvision-testutils:$kvisionVersion")
        }
    }
}