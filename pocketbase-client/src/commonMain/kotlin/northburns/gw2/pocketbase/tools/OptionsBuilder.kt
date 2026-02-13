package northburns.gw2.pocketbase.tools

public class OptionsBuilder internal constructor() {
    private val values: MutableMap<String, Any?> = mutableMapOf()

    public var sort: String? by values
    public var filter: String? by values
    public var expand: String? by values
    public var fields: String? by values
    public var skipTotal: Boolean? by values

    /**
     * The request identifier that can be used to cancel pending requests.
     */
    public var requestKey: String? by values

    public companion object {
        public fun asMap(options: OptionsBuilder): Map<String, Any?> =
            options.values

        public fun (OptionsBuilder.() -> Unit).create(): OptionsBuilder {
            return OptionsBuilder().apply(this)
        }
    }

}