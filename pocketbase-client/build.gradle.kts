plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    explicitApi()

    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.client.core)
            api(libs.ktor.client.logging)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.json)

            api(libs.kotlin.coroutines)
            // api(libs.kotlin.datetime)
            api(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlin.coroutines.test)
            // implementation(libs.kotlin.coroutines.debug)
        }
        jvmMain.dependencies {
        }
        jvmTest.dependencies {
            runtimeOnly(libs.ktor.client.engine.cio)
            implementation("org.slf4j:slf4j-simple:2.0.17")
        }
        jsMain.dependencies {
        }
    }
}
