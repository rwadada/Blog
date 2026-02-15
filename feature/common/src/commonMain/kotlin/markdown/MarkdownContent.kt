package markdown

import MarkdownText
import accentTextColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownContent(
    date: String,
    content: List<MarkdownObject>,
    modifier: Modifier = Modifier,
    composableItem: List<@Composable () -> Unit> = emptyList()
) {
    var composableItemIndex = 0
    Column(
        modifier = modifier
    ) {
        Text(
            text = date,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = accentTextColor(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        content.forEach {
            when (it) {
                is MarkdownObject.Header -> MarkdownHeader(header = it)
                is MarkdownObject.ListItem -> MarkdownListItem(item = it)
                is MarkdownObject.Bold -> MarkdownBoldText(item = it)
                is MarkdownObject.Italic -> MarkdownItalicText(item = it)
                is MarkdownObject.InlineCode -> MarkdownCode(item = it)
                is MarkdownObject.CodeBlock -> MarkdownCodeBlock(item = it)
                is MarkdownObject.Strikethrough -> MarkdownStrikethrough(item = it)
                is MarkdownObject.Link -> MarkdownLink(item = it)
                is MarkdownObject.Image -> MarkdownImage(item = it)
                is MarkdownObject.HorizontalBorder -> MarkdownHorizontalBorder()
                is MarkdownObject.Comment -> Unit
                is MarkdownObject.Text -> MarkdownText(item = it)
                is MarkdownObject.Table -> Unit
                is MarkdownObject.Quote -> Unit
                is MarkdownObject.BreakLine -> Spacer(modifier = Modifier.height(20.dp))
                is MarkdownObject.ComposableContent -> {
                    if (composableItemIndex < composableItem.size) {
                        composableItem[composableItemIndex]()
                        composableItemIndex++
                    }
                }
            }
        }
    }
}
