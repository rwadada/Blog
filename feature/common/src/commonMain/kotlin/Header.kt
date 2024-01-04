import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Header(
    destination: Destination,
    navigate: (Destination) -> Unit
) {
    Row(
        modifier = Modifier
            .background(backgroundColor())
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource("logo/logo.xml"),
                contentDescription = null,
                tint = selectableTextColor(),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Ryosuke Wada",
                color = accentTextColor(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(3f))
        }
        MainMenu(destination = destination, onMenuSelected = {
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
        })
        IconButton(
            onClick = {
                navigate(Search)
            }
        ) {
            Icon(
                painter = painterResource("logo/search.xml"),
                contentDescription = null,
                tint = selectableTextColor()
            )
        }
    }
}
