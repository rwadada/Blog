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
        GITHUB("https://github.com/rwadada"),
        EMAIL("mailto:wadada0420@gmail.com"),
        INSTAGRAM("https://www.instagram.com/ryosuke.wada.925"),
        LINKEDIN("https://www.linkedin.com/in/rwadada0420"),
    }
}

data object Tech : Destination {
    override val path: String = "#/tech"
}

data object Travel : Destination {
    override val path: String = "#/travel"
}

data object Books : Destination {
    override val path: String = "#/books"
}

data object Photo : Destination {
    override val path: String = "#/photo"
}

data object Contact : Destination {
    override val path: String = "#/contact"
}

data object Search : Destination {
    override val path: String = "#/search"
}
