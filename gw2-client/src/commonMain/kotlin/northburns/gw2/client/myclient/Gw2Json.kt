package northburns.gw2.client.myclient

import com.gw2tb.gw2api.client.Gw2ApiClientDefaults
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object Gw2Json  {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json(Gw2ApiClientDefaults.json) {
        encodeDefaults = false
        prettyPrint = true
        prettyPrintIndent = "  "
        classDiscriminator = "type"
        explicitNulls = false
        ignoreUnknownKeys = true

        // for AccountSnapshotId as key (when it's no longer an object hierarchy, consider setting this to false)
        allowStructuredMapKeys = true
    }
}
