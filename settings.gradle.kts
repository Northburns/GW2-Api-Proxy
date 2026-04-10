rootProject.name = "gw2-api-proxy"

include(
    "markdown-tool",
    "markdown-model",
    "markdown-generator",
    "gw2-client",
    //"pocketbase-client",
    "site",
    "site-ksp",
    "goonKSP:annotation",
    "goonKSP:processor",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

// When I need to patch stuff locally temporarily in the GW2 Client library,
// Uncomment these, pull the latest changes,
// ~~and edit file `GW2v2.kt`.~~
// edit build.gradle.kts and remove the generator, and edit the desired api-types directly.

//includeBuild("C:\\git\\github\\GW2ToolBelt\\GW2APIClient") {
//    dependencySubstitution {
//        substitute(module("com.gw2tb.gw2api:api-types"))
//            .withClassifier(":api-types")
//    }
//}

//includeBuild("C:\\git\\github\\GW2ToolBelt\\api-generator") {
//    dependencySubstitution {
//        substitute(module("com.gw2tb.api-generator:api-generator"))
//            .withClassifier(":api-types")
//    }
//}