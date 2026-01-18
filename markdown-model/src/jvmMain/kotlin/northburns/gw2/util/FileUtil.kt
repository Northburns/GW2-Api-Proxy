package northburns.gw2.util

import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.exists

object FileUtil {

    val projectDir by lazy {
        var p = Path(".").absolute().normalize()

        while (p != null && !p.resolve("MyGw2.root").exists())
            p = p.parent

        requireNotNull(p) { "MyGw2.root not found, no projectDir..." }
    }

    @JvmStatic
    fun main(vararg args: String) {
        println(projectDir)
    }

}
