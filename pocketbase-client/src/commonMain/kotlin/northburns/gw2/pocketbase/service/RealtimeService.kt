package northburns.gw2.pocketbase.service

import northburns.gw2.pocketbase.PocketBaseClient

public typealias UnsubscribeFunc = suspend () -> Unit

public class RealtimeService(client: PocketBaseClient) {
    public fun unsubscribe() {
        TODO("Not yet implemented")
    }

}
