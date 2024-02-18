package markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MarkdownContent(
    content: List<MarkdownObject>,
    modifier: Modifier = Modifier,
    vararg composableItem: @Composable () -> Unit = emptyArray()
) {
    var composableItemIndex = 0
    Column(
        modifier = modifier
    ) {
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
                    composableItem[composableItemIndex]()
                    composableItemIndex++
                }
            }
        }
    }
}
