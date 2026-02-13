package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.v2.GW2v2Item
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons.Gw2tIconSize.`16`
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons.Gw2tIconSize.`32`
import northburns.gw2.client.myclient.data.Gw2TreasuresIcons.Gw2tIconSize.`64`
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * https://en.gw2treasures.com/dev/icons
 */
object Gw2TreasuresIcons {

    private val r = Regex("https://render\\.guildwars2\\.com/file/([A-Z0-9]+)/([A-Z0-9]+)\\.png")
    private const val URLPRE = "https://icons-gw2.darthmaim-cdn.com"

    fun GW2v2Item.gw2tIcon(size: Gw2tIconSize) = gw2tIcon(icon, size)

    @OptIn(ExperimentalContracts::class)
    fun gw2tIcon(gw2IconUrl: String?, size: Gw2tIconSize): ImageAttributes? {
        contract {
            returns() implies (gw2IconUrl != null)
        }
        if(gw2IconUrl == null) return null
        val (_, signature, fileId) = vals(gw2IconUrl)
        return ImageAttributes(
            src = "$URLPRE/$signature/$fileId-${size.s}px.png",
            srcset = when (size) {
                `16` -> "$URLPRE/$signature/$fileId-${`32`.s}px.png 2x"
                `32` -> "$URLPRE/$signature/$fileId-${`64`.s}px.png 2x"
                `64` -> null
            },
            width = size.s,
            height = size.s,
        )
    }

    @Suppress("SpellCheckingInspection")
    data class ImageAttributes(
        val src: String,
        val srcset: String?,
        val width: String,
        val height: String,
        val alt: String = "",
        val crossorigin: String = "anonymous",
        val referrerpolicy: String = "no-referrer",
        val loading: String = "lazy",
        val decoding: String = "async",
    )

    private fun vals(gw2IconUrl: String): List<String> {
        val mr = requireNotNull(r.matchEntire(gw2IconUrl)) { "Doesn't match regex: $gw2IconUrl" }
        return mr.groupValues
    }


    @Suppress("EnumEntryName")
    enum class Gw2tIconSize(internal val s: String) {
        `16`("16"), `32`("32"), `64`("64"),
    }

}
