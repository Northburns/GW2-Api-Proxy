package northburns.gw2.site.app

/*
sealed interface GoonRoute {
    val path: String
    fun hashPath(): String = "#$path"

    /**
     * Used for rendering the navbar buttons
     */
    fun isSameViewAs(other: GoonRoute): Boolean = this::class == other::class
}

sealed interface StaticGoonRoute : GoonRoute {
    override fun isSameViewAs(other: GoonRoute): Boolean = this == other
}

object HomeRoute : StaticGoonRoute {
    override val path = "/"
}

object RefineryRoute : StaticGoonRoute {
    override val path = "/refinery"
}

object SessionTrackerRoute : StaticGoonRoute {
    override val path = "/session-tracker"
}

sealed interface MarkdownViewRoute : StaticGoonRoute

object GuideLegendaryVisionRoute : MarkdownViewRoute {
    override val path = "guides/legendary/vision"
}

sealed interface ParametrizedGoonRoute : GoonRoute

data class ExampleRoute(val gw2ItemId: String) : ParametrizedGoonRoute {
    override val path: String = "/example/$gw2ItemId"
}

data class NotFoundRoute(val message: String) : ParametrizedGoonRoute {
    override val path: String = "/404"
}
*/