import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Tech : Route

    @Serializable
    data class TechArticle(val slug: String) : Route

    @Serializable
    data object Travel : Route

    @Serializable
    data object Books : Route

    @Serializable
    data object Photo : Route

    @Serializable
    data object Contact : Route

    @Serializable
    data object Search : Route
}
