package markdown

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import textColor

@Composable
fun MarkdownItalicText(item: MarkdownObject.Italic) {
    Text(text = item.text, color = textColor(), fontStyle = FontStyle.Italic)
}
