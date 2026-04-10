package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.v2.GW2v2Character
import com.gw2tb.gw2api.types.v2.GW2v2Item
import com.gw2tb.gw2api.types.v2.GW2v2Mini
import com.gw2tb.gw2api.types.v2.GW2v2Profession
import com.gw2tb.gw2api.types.v2.GW2v2Skin
import com.gw2tb.gw2api.types.v2.GW2v2Specialization
import com.gw2tb.gw2api.types.v2.GW2v2Trait
import northburns.gw2.client.myclient.data.AchievementsCalc.AchievementFull
import northburns.gw2.client.myclient.data.Gw2WikiProfessionIcons.Companion.professionIcons
import northburns.gw2.client.myclient.data.calc.ProfessionEliteId

sealed interface Gw2Image {
    /**
     * Returns the image URL.
     * If the implementation knows different sizes for it, returns one that is _at least_ the requested size.
     *
     * If possible, use [imageAttributes] for full image attributes.
     */
    fun src(size: Int): String
    fun src(): String = src(defaultSize)

    fun imageAttributes() = imageAttributes(defaultSize)
    fun imageAttributes(size: Int): ImageAttributes {
        val src = src(size)
        val src2x = src(2 * size)
        return ImageAttributes(
            src = src,
            srcset = if (src != src2x) "$src2x 2x" else "",
            width = "${size}px",
            widthPx = size,
            height = "${size}px",
            heightPx = size,
        )
    }

    fun addStyle(property: String, value: String): Gw2Image
    fun addClassname(classname: String): Gw2Image

    val defaultSize: Int

    companion object {
        private fun String?.img() = this?.let { Gw2TreasuresIcon(it) } ?: Placeholder

        /**
         * Use this if you're absolutely sure the string is ok for this.
         */
        fun String?.gw2Image() = img()

        fun GW2v2Item?.gw2Image() = this?.icon.img()
        fun GW2v2Mini?.gw2Image() = this?.icon.img()
        fun GW2v2Skin?.gw2Image() = this?.icon.img()
        fun GW2v2Profession?.gw2Image() = this?.icon.img()
        fun GW2v2Profession?.gw2ImageBig() = this?.bigIcon.img()
        fun GW2v2Specialization?.gw2Image() = this?.icon.img()
        fun GW2v2Trait?.gw2Image() = this?.icon.img()
        fun GW2v2Specialization?.gw2ImageProfession() = this?.professionIcon.img()
        fun GW2v2Specialization?.gw2ImageProfessionBig() = this?.bigProfessionIcon.img()

        fun AchievementFull?.gw2Image() = (this?.a?.icon ?: this?.c?.icon).img()

        fun ProfessionEliteId?.gw2ImageSmallColor() = if (this == null) Placeholder else
            Gw2WikiIcon(professionIcons.getValue(this).smallColorized, 20)

        fun GW2v2Character?.gw2ImageProfessionEliteSmallColor() = if (this == null) Placeholder else
            ProfessionEliteId.forCharacter(this).gw2ImageSmallColor()

    }

    @Suppress("SpellCheckingInspection")
    data class ImageAttributes(
        val src: String,
        val srcset: String,
        val width: String,
        val widthPx: Int,
        val height: String,
        val heightPx: Int,
        val alt: String = "",
        // val crossorigin: String = "anonymous", We're not doing canvas manipulation or anything like that, skip this.
        val referrerpolicy: String = "no-referrer",
        val loading: String = "lazy",
        val decoding: String = "async",
    )

    object Placeholder : Gw2Image {
        override fun src(size: Int): String = "https://placehold.co/$size"
        override fun addStyle(
            property: String,
            value: String
        ): Gw2Image {
            return this
        }

        override fun addClassname(classname: String): Gw2Image {
            return this
        }

        override val defaultSize: Int = 16
    }

    data class Gw2WikiIcon(
        val iconUrl: String,
        override val defaultSize: Int,
    ) : Gw2Image {
        override fun src(size: Int): String = iconUrl
        override fun addStyle(
            property: String,
            value: String
        ): Gw2Image {
            TODO("Not yet implemented")
        }

        override fun addClassname(classname: String): Gw2Image {
            TODO("Not yet implemented")
        }
    }

    data class Gw2TreasuresIcon(
        val apiIconUrl: String,
        override val defaultSize: Int = 64,
        val classNames: String = "",
        val styleProperties: Map<String, String> = mapOf(),
    ) : Gw2Image {
        override fun src(size: Int): String {
            val size: Int = if (size > 32) 64 else if (size > 16) 32 else 16
            return "$URLPRE/$signature/$fileId-${size}px.png"
        }

        // TODO addStyle/classname: create an abstract base class for these?

        override fun addStyle(
            property: String,
            value: String
        ): Gw2Image {
            return copy(styleProperties = styleProperties.plus(property to value))
        }

        override fun addClassname(classname: String): Gw2Image {
            return copy(classNames = "$classNames $classname")
        }

        private val signature: String
        private val fileId: String

        init {
            val mr = requireNotNull(r.matchEntire(apiIconUrl)) { "Doesn't match regex: $apiIconUrl" }
            val (_, signature, fileId) = mr.groupValues
            this.signature = signature
            this.fileId = fileId
        }

        private companion object {
            private val r = Regex("https://render\\.guildwars2\\.com/file/([A-Z0-9]+)/([A-Z0-9]+)\\.png")
            private const val URLPRE = "https://icons-gw2.darthmaim-cdn.com"
        }
    }


}