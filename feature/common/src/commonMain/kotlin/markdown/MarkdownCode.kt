package markdown

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import codeBlockBackgroundColor

@Composable
fun MarkdownCode(item: MarkdownObject.InlineCode) {
    Text(
        text = item.code,
        color = Color.White,
        modifier = Modifier.background(codeBlockBackgroundColor())
    )
}
