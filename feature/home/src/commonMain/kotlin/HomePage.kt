import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(
    navigateSocialLink: (Home.HomeDestination) -> Unit,
    navigateBlogItem: (BlogItem) -> Unit,
    onUrlClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor())
    ) {
        HomeHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            RecentPostsContent(
                modifier = Modifier.fillMaxWidth(),
                navigateBlogItem = navigateBlogItem
            )

            ProductsContent(
                modifier = Modifier.fillMaxWidth(),
                onUrlClick = onUrlClick
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(14.dp),
                    elevation = 0.dp,
                    backgroundColor = surfaceColor(),
                    border = BorderStroke(1.dp, cardBorderColor())
                ) {
                    AboutMeContent(modifier = Modifier.padding(22.dp))
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    elevation = 0.dp,
                    backgroundColor = surfaceColor(),
                    border = BorderStroke(1.dp, cardBorderColor())
                ) {
                    SocialLinkContent(
                        modifier = Modifier.padding(22.dp),
                        navigate = navigateSocialLink
                    )
                }
            }
        }
    }
}
