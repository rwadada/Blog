package markdown

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import textColor

@Composable
fun MarkdownBoldText(item: MarkdownObject.Bold) {
    Text(text = item.text, fontWeight = FontWeight.Bold, color = textColor())
}
