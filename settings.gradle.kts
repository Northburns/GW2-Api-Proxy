rootProject.name = "gw2-api-proxy"

include(
    "markdown-tool",
    "markdown-model",
    "gw2-client",
    "site",
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
