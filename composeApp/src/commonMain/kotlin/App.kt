import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun App(navigate: (String) -> Unit, currentPath: String) {
    val destination = findDestination(currentPath)
    val scrollState = rememberScrollState()
    LaunchedEffect(currentPath) {
        scrollState.scrollTo(0)
    }
    MaterialTheme(typography = AppTypography) {
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor())) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Sticky Header at the top
                Header(
                    destination = destination,
                    navigate = {
                        navigate(it.path)
                    }
                )
                
                // Scrollable content area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    when (destination) {
                        is Home -> HomePage(
                            navigateSocialLink = { navigate(Home(it).path) },
                            navigateBlogItem = { navigate(it.getDestinationPath()) },
                            onUrlClick = { navigate(it) }
                        )
                        is Tech -> TechPage(
                            index = getBlogIndex(currentPath),
                            navigate = navigate
                        )
                        is Travel -> ComingSoonPage()
                        is Books -> ComingSoonPage()
                        is Photo -> ComingSoonPage()
                        is Contact -> ComingSoonPage()
                        is Search -> SearchPage(navigate = { navigate(it) })
                    }
                    Footer()
                }
            }
        }
    }
}

private fun getBlogIndex(path: String) = try {
    path.split("/").last().toInt()
} catch (_: Exception) {
    null
}
