import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(selectedMenu: Menu?, onMenuSelected: (Menu) -> Unit) {
    Row {
        Menu.entries.forEach {
            val textColor = if (it == selectedMenu) selectedTextColor() else selectableTextColor()
            TextButton(onClick = { onMenuSelected(it) }) {
                Text(text = it.name, color = textColor, fontSize = 12.sp)
            }
        }
    }
}

enum class Menu {
    HOME,
    TECH,
    TRAVEL,
    BOOKS,
    PHOTO,
    CONTACT
}

val Menu.route: Route
    get() = when (this) {
        Menu.HOME -> Route.Home
        Menu.TECH -> Route.Tech
        Menu.TRAVEL -> Route.Travel
        Menu.BOOKS -> Route.Books
        Menu.PHOTO -> Route.Photo
        Menu.CONTACT -> Route.Contact
    }
