package northburns.gw2.pocketbase.service

import kotlinx.coroutines.Deferred
import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.service.RecordService.OAuth2AuthConfig
import northburns.gw2.pocketbase.service.RecordService.RecordAuthResponse
import northburns.gw2.pocketbase.tools.RecordModel

public actual suspend fun <T : RecordModel> doAuthWithOAuth2(
    self: RecordService,
    client: PocketBaseClient,
    options: OAuth2AuthConfig
): Deferred<RecordAuthResponse<T>> {
    // This involves spinning up a http service which can catch the redirects.
    // Let's implement this when we need it.
    TODO("Not yet implemented")
}
