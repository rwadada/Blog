fun findDestination(path: String) = when  {
    path.startsWith("#/home") -> Home(Home.HomeDestination.HOME)
    path.startsWith("#/tech") -> Tech
    path.startsWith("#/travel") -> Travel
    path.startsWith("#/books") -> Books
    path.startsWith("#/photo") -> Photo
    path.startsWith("#/contact") -> Contact
    path.startsWith("#/search") -> Search
    else -> Home(Home.HomeDestination.HOME)
}
sealed interface Destination {
    val path: String
}

class Home(homeDestination: HomeDestination) : Destination {
    override val path: String = homeDestination.url ?: "#/home"

    enum class HomeDestination(val url: String?) {
        HOME(null),
        FACEBOOK("https://www.facebook.com/ryosuke.wada.925"),
        INSTAGRAM("https://www.instagram.com/ryosuke.wada.925"),
        TWITTER("https://twitter.com/r_wadada"),
        YOUTUBE("https://www.youtube.com/@rw8141"),
        GITHUB("https://github.com/rwadada"),
        EMAIL("mailto:wadada0420@gmail.com")
    }
}

object Tech : Destination {
    override val path: String = "#/tech"
}

object Travel : Destination {
    override val path: String = "#/travel"
}

object Books : Destination {
    override val path: String = "#/books"
}

object Photo : Destination {
    override val path: String = "#/photo"
}

object Contact : Destination {
    override val path: String = "#/contact"
}

object Search : Destination {
    override val path: String = "#/search"
}
