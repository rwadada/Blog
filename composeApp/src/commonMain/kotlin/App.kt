import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun App(
    onOpenUrl: (String) -> Unit,
    onTitleChange: (String) -> Unit = {},
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val selectedMenu = remember(currentDestination) {
        when {
            currentDestination.matches<Route.Home>() -> Menu.HOME
            currentDestination.matches<Route.Tech>() ||
                currentDestination.matches<Route.TechArticle>() -> Menu.TECH
            currentDestination.matches<Route.Travel>() -> Menu.TRAVEL
            currentDestination.matches<Route.Books>() -> Menu.BOOKS
            currentDestination.matches<Route.Photo>() -> Menu.PHOTO
            currentDestination.matches<Route.Contact>() -> Menu.CONTACT
            else -> null
        }
    }

    val scrollState = rememberScrollState()
    LaunchedEffect(backStackEntry) {
        scrollState.scrollTo(0)
    }

    LaunchedEffect(currentDestination) {
        onTitleChange(titleFor(currentDestination))
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    MaterialTheme(typography = AppTypography) {
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor())) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Sticky Header at the top
                Header(
                    selectedMenu = selectedMenu,
                    navigate = { route -> navController.navigate(route) { launchSingleTop = true } }
                )

                // Scrollable content area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    NavHost(navController = navController, startDestination = Route.Home) {
                        composable<Route.Home> {
                            HomePage(
                                navigateBlogItem = { navController.navigate(it.route) },
                                onUrlClick = onOpenUrl
                            )
                        }
                        composable<Route.Tech> {
                            TechPage(slug = null, navigate = { navController.navigate(it) })
                        }
                        composable<Route.TechArticle> { entry ->
                            val route: Route.TechArticle = entry.toRoute()
                            TechPage(slug = route.slug, navigate = { navController.navigate(it) })
                        }
                        composable<Route.Travel> { ComingSoonPage() }
                        composable<Route.Books> { ComingSoonPage() }
                        composable<Route.Photo> { ComingSoonPage() }
                        composable<Route.Contact> { ComingSoonPage() }
                        composable<Route.Search> {
                            SearchPage(navigate = { navController.navigate(it) })
                        }
                    }
                    Footer()
                }
            }
        }
    }
}

private inline fun <reified T : Any> NavDestination?.matches(): Boolean =
    this?.hierarchy?.any { it.hasRoute<T>() } == true

private fun titleFor(destination: NavDestination?): String {
    val suffix = when {
        destination.matches<Route.Tech>() || destination.matches<Route.TechArticle>() -> " - Tech"
        destination.matches<Route.Travel>() -> " - Travel"
        destination.matches<Route.Books>() -> " - Books"
        destination.matches<Route.Photo>() -> " - Photo"
        destination.matches<Route.Contact>() -> " - Contact"
        destination.matches<Route.Search>() -> " - Search"
        else -> ""
    }
    return "Ryosuke Wada$suffix"
}
