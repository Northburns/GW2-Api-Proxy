package northburns.gw2.site

import io.kvision.redux.RAction
import northburns.gw2.site.state.Gw2GoonView

enum class FeedType {
    USER,
    GLOBAL,
    TAG,
    PROFILE,
    PROFILE_FAVORITED
}


sealed class ConduitAction : RAction {
    object AppLoaded : ConduitAction()
    object HomePage : ConduitAction()
    data class SelectFeed(
        val feedType: FeedType,
        val tag: String?,
        val profile: User?
    ) : ConduitAction()

    object ArticlesLoading : ConduitAction()
    data class ArticlesLoaded(val articles: List<Article>, val articlesCount: Int) : ConduitAction()
    data class SelectPage(val selectedPage: Int) : ConduitAction()

    data class ShowArticle(val article: Article) : ConduitAction()
    data class ShowArticleCommets(val articleComments: List<Comment>) : ConduitAction()
    data class ArticleUpdated(val article: Article) : ConduitAction()

    object TagsLoading : ConduitAction()
    data class TagsLoaded(val tags: List<String>) : ConduitAction()

    data class AddComment(val comment: Comment) : ConduitAction()
    data class DeleteComment(val id: Int) : ConduitAction()

    data class ProfilePage(val feedType: FeedType) : ConduitAction()
    data class ProfileFollowChanged(val user: User) : ConduitAction()

    object LoginPage : ConduitAction()
    data class Login(val user: User) : ConduitAction()
    data class LoginError(val errors: List<String>) : ConduitAction()

    object SettingsPage : ConduitAction()
    data class SettingsError(val errors: List<String>) : ConduitAction()

    object RegisterPage : ConduitAction()
    data class RegisterError(val username: String?, val email: String?, val errors: List<String>) : ConduitAction()

    object Logout : ConduitAction()

    data class EditorPage(val article: Article?) : ConduitAction()
    data class EditorError(
        val article: Article,
        val errors: List<String>
    ) : ConduitAction()
}

