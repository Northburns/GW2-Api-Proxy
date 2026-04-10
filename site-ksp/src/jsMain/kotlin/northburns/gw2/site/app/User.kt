package northburns.gw2.site.app

@JsExport
sealed interface User {
}

data class Gw2MeUser(
    val x: String,
): User

object DummyUser : User {

}
