import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(destination: Destination, onMenuSelected: (Menu) -> Unit) {
    val selectedMenu = Menu.findMenu(destination)
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
    CONTACT;
    companion object {
        fun findMenu(destination: Destination) = when(destination) {
            is Home -> HOME
            is Tech -> TECH
            is Travel -> TRAVEL
            is Books -> BOOKS
            is Photo -> PHOTO
            is Contact -> CONTACT
            is Search -> null
        }
    }
}
