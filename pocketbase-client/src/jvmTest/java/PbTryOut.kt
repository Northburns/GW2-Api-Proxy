import kotlinx.coroutines.test.runTest
import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.service.RecordService
import northburns.gw2.pocketbase.tools.RecordModel
import kotlin.test.Test

class PbTryOut {
    @Test
    fun tryOut()  = runTest {
        val pb = PocketBaseClient("http://127.0.0.1:$PORT")

        pb.collection("_superusers").authWithPassword<RecordModel>(ADMIN1_USERNAME, ADMIN1_PASSWORD)

        val scaffolds = pb.collections.getScaffolds()

        println(scaffolds)

        pb.collection("_superusers").authWithOAuth2<RecordModel>(RecordService.OAuth2AuthConfig(provider="google"))
    }

    companion object {
        private const val PORT = "8091"
        private const val ADMIN1_USERNAME = "admin@test.com"
        private const val ADMIN1_PASSWORD = "password"
    }
}