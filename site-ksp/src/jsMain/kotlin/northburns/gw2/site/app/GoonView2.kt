package northburns.gw2.site.app

import io.kvision.core.StringPair
import kotlinx.serialization.Serializable

@Serializable
enum class GoonPath(
    val path: String,
) {
    TOOL_REFINERY("refinery"),
    TOOL_SESSIONTRACKER("session-tracker"),

    ACCOUNTS_CHARACTERS("acc/characters"),
    ACCOUNTS_CHARACTERS_VIEW("acc/characters/:account/:character"),

    REF_VISION("reference/legendary/vision"),
    REF_DRIZZLEWOODCOAST("reference/drizzlewood-coast"),

    REF_PROFESSION("reference/profession"),
    REF_PROFESSION_GUARDIAN("reference/profession/guardian"),
    REF_PROFESSION_REVENANT("reference/profession/revenant"),
    REF_PROFESSION_WARRIOR("reference/profession/warrior"),
    REF_PROFESSION_ENGINEEER("reference/profession/engineer"),
    REF_PROFESSION_RANGER("reference/profession/ranger"),
    REF_PROFESSION_THIEF("reference/profession/thief"),
    REF_PROFESSION_ELEMENTALIST("reference/profession/elementalist"),
    REF_PROFESSION_MESMER("reference/profession/mesmer"),
    REF_PROFESSION_NECROMANCER("reference/profession/necromancer"),

    SPECIAL_404("404"),
    SPECIAL_HOME(""),
    SPECIAL_MD("md"),
    SPECIAL_SETTINGS("settings"),
    ;

    fun hashPath() = "#$path"


    fun buildPath(vararg els: StringPair): String {
        return "#" + els.fold(path) { acc, el ->
            val placeholder = ":" + el.first
            require(acc.contains(placeholder)) { "Path doesn't contain ${el.first}: " + acc }
            acc.replace(placeholder, el.second)
        }
    }

    companion object {
        val byPath = entries.associateBy { it.path }
    }
}

@Serializable
data class GoonRoute(
    val path: GoonPath,
    val params: Map<String, String> = emptyMap(),
) {

    /**
     * Used for rendering the navbar buttons
     */
    fun isSameViewAs(other: GoonRoute): Boolean = this.path == other.path
    fun isSameViewAs(other: GoonPath): Boolean = this.path == other
    fun isSameOrSubPathOf(other: GoonPath): Boolean = this.path.path.startsWith(other.path)
}

