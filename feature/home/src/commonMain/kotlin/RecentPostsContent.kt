import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentPostsContent(
    modifier: Modifier = Modifier,
    navigateBlogItem: (BlogItem) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "RECENT POSTS",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = accentTextColor(), // Reverted to white for Dark Background
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val recentPosts = remember { blogItems.reversed().take(3) }
        recentPosts.forEach {
            BlogItemSummary(it, navigateBlogItem)
        }
    }
}

@Composable
fun BlogItemSummary(blogItem: BlogItem, onClickItem: (BlogItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(blogItem) },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = surfaceColor()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = blogItem.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = selectedTextColor()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = blogItem.date,
                fontSize = 14.sp,
                color = secondaryTextColor()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = blogItem.summary,
                fontSize = 16.sp,
                color = secondaryTextColor(), // Changed from textColor()
                lineHeight = 24.sp,
                maxLines = 3
            )
        }
    }
}
