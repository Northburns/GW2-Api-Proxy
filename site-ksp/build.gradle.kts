plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ksp)
}


// configurations.forEach { println(it.name) }
dependencies {
    // ksp is deprecated. Use kspCommonMainMetadata ?
    add("ksp", project(":goonKSP:processor"))
    add("ksp", libs.arrow.optics.ksp)
}
ksp {
    arg("projectDir", projectDir.absolutePath)

    // XXX This here is a very nasty piece of _circular dependency_!!!
    // This project creates stuff from site's resources, which site then depends on.
    // We could of course move _all_ resources to this project :thinking:
    arg("projectDirSite", project(":site").projectDir.absolutePath)
}


val dirGeneratedMarkdown = layout.buildDirectory.dir("generated-markdown")
tasks.register<JavaExec>("generate") {
    group = "generate"
    mainClass.set("northburns.gw2.generator.MarkdownAstSiteGenerator")
    args(projectDir.resolve("markdown"), dirGeneratedMarkdown.get().asFile.absolutePath)
    classpath(project(":goonKSP:processor").sourceSets["main"].runtimeClasspath)
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.js.ExperimentalJsExport")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
    // jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        val jsMain by getting {
            kotlin.srcDir(dirGeneratedMarkdown)
        }

        jsMain.dependencies {
            api(libs.arrow.core)
            api(libs.arrow.serialization) // https://arrow-kt.io/learn/quickstart/setup/serialization/#kotlinxserialization
            api(libs.arrow.optics)
            api(libs.gw2api)
            implementation(libs.gw2api.ktor)
            api(libs.kotlinx.serialization.json)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.json)
            // implementation(libs.store)
            // implementation(libs.store.cache)

            implementation(libs.kvision)
            implementation(libs.kvision.navigo)
            implementation(libs.kvision.state)

            api(project(":goonKSP:annotation"))
            implementation(project(":markdown-generator"))
            api(project(":gw2-client"))
        }
    }
}
