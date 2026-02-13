package northburns.gw2.pocketbase.store

public class InMemoryAuthStore : AuthStore {
    private var token: String? = null

    override suspend fun token(): String? {
        return token
    }

    override suspend fun save(token: String) {
        this.token = token
    }

    override suspend fun clear() {
        this.token = null
    }
}
