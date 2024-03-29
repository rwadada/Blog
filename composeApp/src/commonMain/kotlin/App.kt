import androidx.compose.foundation.layout.Column
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
    LaunchedEffect(destination) {
        scrollState.scrollTo(0)
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Header(
                destination = destination,
                navigate = {
                    navigate(it.path)
                }
            )
            when (destination) {
                is Home -> HomePage(
                    navigate = {
                        navigate(Home(it).path)
                    }
                )

                is Tech -> TechPage(0)
                is Travel -> ComingSoonPage()
                is Books -> ComingSoonPage()
                is Photo -> ComingSoonPage()
                is Contact -> ComingSoonPage()
                is Search -> ComingSoonPage()
            }
            Footer(
                destination = destination,
                navigate = {
                    navigate(it.path)
                }
            )
        }
    }
}
