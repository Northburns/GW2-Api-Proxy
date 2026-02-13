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
            api(libs.arrow.core)
            api(libs.gw2api)
            implementation(libs.gw2api.ktor)
            api(libs.kotlinx.serialization.json)
            api(libs.napier)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.json)
            implementation(libs.kottage)

            api(project(":markdown-model"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmMain.dependencies {
            // implementation("io.ktor:ktor-client-apache5:3.4.0")
            implementation("io.ktor:ktor-client-cio:3.4.0")
            implementation("org.slf4j:slf4j-simple:2.0.17")
        }
        jsMain.dependencies {
            // implementation(libs.juul.indexeddb)
        }
    }
}
