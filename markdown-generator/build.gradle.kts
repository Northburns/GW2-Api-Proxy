plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.js.ExperimentalJsExport")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            api(libs.arrow.core)
            implementation(libs.napier)
            implementation(libs.jetbrains.markdown)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }
}
