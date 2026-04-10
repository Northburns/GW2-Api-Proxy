package northburns.gw2.client.myclient

import northburns.gw2.util.FileUtil
import kotlin.io.path.readText

actual suspend fun initializePlayerDataRead(): Pair<String, String> {
    val dir = FileUtil.projectDir.resolve("gw2-client/src/commonMain/resources/players")

    return dir.resolve("players.json").readText() to
            dir.resolve("players-secrets.json").readText()
}
