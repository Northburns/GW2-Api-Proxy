package northburns.gw2

import java.io.File

object FileUtil {

    fun findDir(dir: String): File {
        var f = File(".")
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        while (f.listFiles().singleOrNull { it.name == dir } == null) {
            f = f.parentFile
        }
        f = f.resolve(dir).normalize()
        return f
    }

}