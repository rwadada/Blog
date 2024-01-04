import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(navigate: (Home.HomeDestination) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(contentBackgroundColor())
    ) {
        HomeHeader()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RecentPostsContent(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, borderColor())
                    .padding(8.dp)
            ) {
                AboutMeContent(modifier = Modifier.fillMaxWidth())
                SocialLinkContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    navigate = navigate
                )
            }
        }
    }
}
