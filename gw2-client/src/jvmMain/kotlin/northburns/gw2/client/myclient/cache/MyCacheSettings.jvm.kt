package northburns.gw2.client.myclient.cache

import northburns.gw2.util.FileUtil
import kotlin.io.path.pathString

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object MyCacheSettings {
    actual fun cacheDir(): String {
        return FileUtil.projectDir.resolve("cache").pathString
    }
}