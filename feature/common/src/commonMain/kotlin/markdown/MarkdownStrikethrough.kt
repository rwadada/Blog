package markdown

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import textColor

@Composable
fun MarkdownStrikethrough(item: MarkdownObject.Strikethrough) {
    Text(text = item.text, textDecoration = TextDecoration.LineThrough, color = textColor())
}
