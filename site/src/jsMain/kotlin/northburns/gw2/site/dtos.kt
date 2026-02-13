package northburns.gw2.site

data class User(
    val username: String,
    val following: Boolean = false,
    val token: String ="",
)

data class Article(
    val slug: String = "SLUG",
    val author: User? = null,
    val title: String?=null,
    val description: String?=null,
    val body: String?=null,
    val tagList: List<String> = emptyList(),
    val favorited: Boolean = false,
)

data class ArticlesDto(
    val articles: List<Article>,
    val articlesCount: Int=100,
)

data class Comment(
    val id: Int,
)

class RemoteRequestException() : Exception()
