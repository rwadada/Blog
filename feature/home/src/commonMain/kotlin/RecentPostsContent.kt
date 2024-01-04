import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecentPostsContent(modifier: Modifier = Modifier) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No items",
                color = textColor()
            )
        }
    }
}
