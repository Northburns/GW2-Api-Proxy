plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.gw2api)
            implementation(libs.gw2api.ktor)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.napier)
            implementation(libs.kottage)
        }
        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-apache5:3.3.2")
            implementation("org.slf4j:slf4j-simple:2.0.17")
        }
        jsMain.dependencies {
            // implementation(libs.juul.indexeddb)
        }
    }
}
