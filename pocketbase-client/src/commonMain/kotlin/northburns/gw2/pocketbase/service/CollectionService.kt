package northburns.gw2.pocketbase.service

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.tools.CollectionModel
import northburns.gw2.pocketbase.tools.OptionsBuilder
import northburns.gw2.pocketbase.tools.OptionsBuilder.Companion.create

public class CollectionService(
    override val client: PocketBaseClient,
) : CrudService<CollectionModel>() {

    override val baseCrudPath: List<String> = listOf("api", "collections")

    public suspend fun truncate(string: String) {
        TODO()
    }

    public suspend fun import(
        collections: List<CollectionModel>,
        deleteMissing: Boolean = false,
        options: OptionsBuilder.() -> Unit = {},
    ) {
        TODO()
    }

    public suspend fun getScaffolds(
        options: OptionsBuilder.() -> Unit = {},
    ): Map<String, CollectionModel> {
        return client.doHttpRequest(options.create()) {
            method = HttpMethod.Get
            url {
                appendPathSegments(baseCrudPath)
                appendPathSegments("meta", "scaffolds")
            }
        }
    }
}
