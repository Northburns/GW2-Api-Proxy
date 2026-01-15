plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    //implementation(libs.flexmark.java)
    implementation("com.vladsch.flexmark:flexmark-all:0.64.8")
    // TODO use only the needed libraries!

    implementation(project(":markdown-model"))

}
