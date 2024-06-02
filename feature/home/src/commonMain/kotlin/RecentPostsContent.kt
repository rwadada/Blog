import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentPostsContent(
    modifier: Modifier = Modifier,
    navigateBlogItem: (BlogItem) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        SelectionContainer {
            Text(
                text = "RECENT POSTS",
                fontWeight = FontWeight.Bold,
                color = textColor()
            )
        }
        TitleLine()
        blogItems.reversed().take(5).forEach {
            BlogItemSummary(it, navigateBlogItem)
        }
    }
}

@Composable
fun BlogItemSummary(blogItem: BlogItem, onClickItem: (BlogItem) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { onClickItem(blogItem) }
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = blogItem.title,
                    fontSize = 20.sp,
                    color = textColor(),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date: ${blogItem.date}",
                    color = textColor()
                )
            }
            Text(
                text = blogItem.summary,
                color = textColor(),
                modifier = Modifier.weight(7f)
            )
        }
    }
}
