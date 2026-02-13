rootProject.name = "gw2-api-proxy"

include(
    "markdown-tool",
    "markdown-model",
    "gw2-client",
    "pocketbase-client",
    "site",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
