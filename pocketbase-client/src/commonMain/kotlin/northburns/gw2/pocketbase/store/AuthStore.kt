package northburns.gw2.pocketbase.store

public interface AuthStore {
    public suspend fun token(): String?
    public suspend fun save(token: String)

    public suspend fun clear()
}
