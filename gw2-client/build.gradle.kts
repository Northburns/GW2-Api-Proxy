plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ksp)
}


// configurations.forEach { println(it.name) }
dependencies {
    // ksp is deprecated. Use kspCommonMainMetadata ?
    add("ksp", libs.arrow.optics.ksp)
}
ksp {
    arg("projectDir", projectDir.absolutePath)

    // XXX This here is a very nasty piece of _circular dependency_!!!
    // This project creates stuff from site's resources, which site then depends on.
    // We could of course move _all_ resources to this project :thinking:
    arg("projectDirSite", project(":site").projectDir.absolutePath)
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
            api(libs.arrow.serialization) // https://arrow-kt.io/learn/quickstart/setup/serialization/#kotlinxserialization
            api(libs.arrow.optics)
            api(libs.gw2api)
            implementation(libs.gw2api.ktor)
            api(libs.kotlinx.serialization.json)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.json)
            //implementation(libs.kottage)
            // implementation(libs.store)
            // implementation(libs.store.cache)

            implementation(project(":markdown-model"))

        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmMain.dependencies {
            implementation(libs.napier)
            implementation(libs.kotlin.datetime)
            // implementation("io.ktor:ktor-client-apache5:3.4.0")
            implementation("io.ktor:ktor-client-cio:3.4.0")
            implementation("org.slf4j:slf4j-simple:2.0.17")

            implementation("org.xerial:sqlite-jdbc:3.45.1.0")

            implementation("com.jsoizo:kotlin-csv-jvm:1.10.0")
        }
        jsMain.dependencies {
            // implementation(libs.juul.indexeddb)
            implementation(npm("dexie", "4.3.0"))
        }
    }
}
