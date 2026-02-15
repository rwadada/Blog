import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(
    navigateSocialLink: (Home.HomeDestination) -> Unit,
    navigateBlogItem: (BlogItem) -> Unit,
    onUrlClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor())
    ) {
        HomeHeader()
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp), // Increased padding for modern look
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Bento Grid Row: Profile + Social
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Profile Card (Larger)
                Card(
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(24.dp), // More rounded for modern feel
                    elevation = 4.dp,
                    backgroundColor = surfaceColor()
                ) {
                    // Padding handled inside content usually, but AboutMeContent handles its own layout. 
                    // Let's add padding here to contain it nicely.
                    AboutMeContent(modifier = Modifier.padding(24.dp))
                }

                // Social Card (Smaller)
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    elevation = 4.dp,
                    backgroundColor = surfaceColor()
                ) {
                   SocialLinkContent(
                       modifier = Modifier.padding(24.dp),
                       navigate = navigateSocialLink
                   )
                }
            }
            
            // Products Section
            ProductsContent(
                modifier = Modifier.fillMaxWidth(),
                onUrlClick = onUrlClick
            )

            // Recent Posts Section
            // RecentPostsContent has its own "Recent Posts" header and cards loop. 
            // We just place it here.
            RecentPostsContent(
                modifier = Modifier.fillMaxWidth(),
                navigateBlogItem = navigateBlogItem
            )
            
            // Add some bottom spacing
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
