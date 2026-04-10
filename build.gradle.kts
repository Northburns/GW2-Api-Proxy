import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.gradle.versions) apply true

    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ksp) apply false

    alias(libs.plugins.kotlin.jvm) apply false
    //alias(libs.plugins.ktor)

    alias(libs.plugins.kvision) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
}

group = "fi.northburns"
version = "0.0.1"

/*kotlin {
    jvm()
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
        commonMain.dependencies {
           implementation(libs.gw2api)
           implementation(libs.gw2api.ktor)
           implementation(libs.kotlinx.serialization.json)
            // implementation(libs.ktor.serialization.kotlinx.json)
        }
        jsMain.dependencies {
            implementation(libs.kvision)
            implementation(libs.kvision.bootstrap)
            implementation(libs.kvision.i18n)
        }
        jsTest.dependencies {
            //implementation("io.kvision:kvision-testutils:$kvisionVersion")
        }
        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-apache5:3.4.0")
            //implementation(kotlin("stdlib-jdk8"))
        }
    }
}*/

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
