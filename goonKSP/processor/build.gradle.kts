plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":goonKSP:annotation"))
    implementation(project(":markdown-generator"))
    implementation(libs.squareup.ksp)
    implementation(libs.kotlinpoet)
}
/*
kotlin {
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(project(":goonKSP:annotation"))
            implementation(libs.squareup.ksp)
            implementation(libs.kotlinpoet)
        }
    }
}
*/