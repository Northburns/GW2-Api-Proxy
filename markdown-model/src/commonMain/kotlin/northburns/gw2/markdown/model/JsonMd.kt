package northburns.gw2.markdown.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object JsonMd {

    @OptIn(ExperimentalSerializationApi::class)
    val json  = Json {
        encodeDefaults = false
        prettyPrint = true
        prettyPrintIndent = "  "
        classDiscriminator = "_"
        explicitNulls = false
    }

}