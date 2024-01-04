import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App(navigate: (String) -> Unit, currentPath: String) {
    val destination = findDestination(currentPath)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
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
                is Tech -> Unit
                is Travel -> Unit
                is Books -> Unit
                is Photo -> Unit
                is Contact -> Unit
                is Search -> Unit
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
