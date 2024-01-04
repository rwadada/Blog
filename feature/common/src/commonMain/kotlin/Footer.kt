import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Footer(destination: Destination, navigate: (Destination) -> Unit) {
    Row(
        modifier = Modifier
            .background(backgroundColor())
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MainMenu(
            destination = destination,
            onMenuSelected = {
                navigate(
                    when (it) {
                        Menu.HOME -> Home(Home.HomeDestination.HOME)
                        Menu.TECH -> Tech
                        Menu.TRAVEL -> Travel
                        Menu.BOOKS -> Books
                        Menu.PHOTO -> Photo
                        Menu.CONTACT -> Contact
                    }
                )
            }
        )
    }
}
