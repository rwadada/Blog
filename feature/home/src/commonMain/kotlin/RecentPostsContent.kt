import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

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
        blogItems.reversed().take(3).forEach {
            Text(
                text = it.title,
                modifier = Modifier.clickable { navigateBlogItem(it) }
            )
        }
    }
}
