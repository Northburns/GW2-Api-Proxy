package northburns.gw2.site.util.markdown

import com.gw2tb.gw2api.types.GW2ItemId
import io.kvision.core.Component
import northburns.gw2.client.myclient.data.Gw2Ids.findWellKnownItemByName
import northburns.gw2.site.layout.components.TextWithIconFactory

/**
 * Macros are code spans that start with "g!", the format is as follows:
 *
 * `g! macro-name arg-name=1 |arg-name=String | arg-name=String with spaces | value with no arg name`
 *
 * Limitations: Can't have characters `|` anywhere :-/
 */
object GoonMarkdownMacros {
    const val CODESPAN_PREFIX = "g!"

    fun render(code: String): Component {
        val top = code.removePrefix(CODESPAN_PREFIX).trim().split(' ', limit = 2)
        val macroName = top.first().trim()
        val argsL = if (top.size == 2) {
            top[1].trim().split('|').map { argString ->
                if (!argString.contains("=")) null to argString.trim()
                else argString.split('=', limit = 2).let { (k, v) -> k.trim() to v.trim() }
            }
        } else emptyList()
        val args = MacroArgs(argsL)

        return when (macroName) {
            "image-item" -> renderImageItem(args)
            "inline-item" -> renderImageItem(args)
            "achievement-block" -> renderAchievementBlock(args)
            else -> throw IllegalArgumentException("Unexpected macro name: '$macroName'")
        }
    }

    internal class MacroArgs(
        private val args: List<Pair<String?, String>>,
    ) {
        private val argsMap by lazy { args.toMap() }
        operator fun get(k: String) = argsMap[k]
        operator fun get(i: Int) = args[i].second
    }

    // Simple macros are kept in this file, more complex split into their own files

    private fun renderImageItem(args: MacroArgs): Component {
        val size = args["size"]?.toInt() ?: 16
        val itemId = args["id"]?.toLong()?.let(::GW2ItemId)
            ?: findWellKnownItemByName(requireNotNull(args["name"]) { "id is required, unless name is provided" })
        return TextWithIconFactory.itemText(itemId)
    }
}
