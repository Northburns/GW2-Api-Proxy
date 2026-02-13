package northburns.gw2.pocketbase.service

import northburns.gw2.pocketbase.PocketBaseClient
import northburns.gw2.pocketbase.tools.BaseModel
import northburns.gw2.pocketbase.tools.ListResult
import northburns.gw2.pocketbase.tools.OptionsBuilder

public abstract class CrudService<M : BaseModel> {
    public abstract val client: PocketBaseClient
    public abstract val baseCrudPath: List<String>

    public suspend fun <T : M> getList(
        page: Int,
        perPage: Int,
        options: OptionsBuilder.() -> Unit = {},
    ): ListResult<T> {
        TODO()
    }

    /**
     * Ignores skipTotal, sets to true
     */
    public suspend fun <T : M> getFullList(
        options: OptionsBuilder.() -> Unit = {},
    ): List<T> {
        TODO()
    }

    /**
     * Ignores skipTotal, sets to true
     */
    public suspend fun <T : M> getFirstListItem(
        filter: String,
        options: OptionsBuilder.() -> Unit = {},
    ): T {
        TODO()
    }

    public suspend fun <T : M> getOne(
        id: String,
        options: OptionsBuilder.() -> Unit = {},
    ): T {
        TODO()
    }

    public suspend fun <T : M> create(
        body: T,
        options: OptionsBuilder.() -> Unit = {},
    ): T {
        TODO()
    }

    /**
     * TODO partial update! Accept map? String?
     */
    public suspend fun <T : M> update(
        id: String,
        body: T,
        options: OptionsBuilder.() -> Unit = {},
    ): T {
        TODO()
    }

    public suspend fun delete(id: String): Boolean {
        TODO()
    }

    private companion object {
        private const val BATCH_DEFAULT = 1000
    }
}