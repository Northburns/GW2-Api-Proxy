package northburns.gw2.site.util.tailwind


fun tw(build: TailwindBuilder.() -> Unit): String {
    return TailwindBuilder().apply(build).build()
}

@DslMarker
@Target(AnnotationTarget.CLASS)
annotation class TailwindMarker

/**
 * https://tailwindcss.com/docs
 *
 * Remember! Don't create anything too clever!
 * The source code is scanned and that determines what CSS classes will be available.
 */
@TailwindMarker
class TailwindBuilder(
    private val list: MutableList<String> = mutableListOf()
) {

    fun build(): String = list.joinToString(" ")

    fun raw(className: String) = apply { list.add(className) }

    operator fun String.unaryPlus(): TailwindBuilder = raw(this)

}

