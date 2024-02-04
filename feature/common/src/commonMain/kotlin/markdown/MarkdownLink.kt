package markdown

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.browser.window

@Composable
fun MarkdownLink(item: MarkdownObject.Link) {
    Text(
        text = item.text,
        color = Color(0xFF1DB2DC),
        modifier = Modifier.clickable { window.open(item.url) }
    )
}
