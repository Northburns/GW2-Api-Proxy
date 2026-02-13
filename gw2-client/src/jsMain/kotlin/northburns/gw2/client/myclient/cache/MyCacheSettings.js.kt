package northburns.gw2.client.myclient.cache

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object MyCacheSettings {
    actual fun cacheDir(): String {
        return "cacheDir" // TODO What is this used for in JS?
    }
}
