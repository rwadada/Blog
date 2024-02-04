import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import markdown.MarkdownBoldText
import markdown.MarkdownHeader
import markdown.MarkdownItalicText
import markdown.MarkdownListItem
import markdown.MarkdownObject
import usecase.ParseMarkDownStringUseCase
import usecase.ReadFileUseCase


@Composable
fun TechPage() {
    val readFileUseCase = ReadFileUseCase()
    val parseMarkDownStringUseCase = ParseMarkDownStringUseCase()
    var fileContent: List<MarkdownObject> by remember { mutableStateOf(emptyList()) }
    LaunchedEffect(Unit) {
        fileContent = parseMarkDownStringUseCase(readFileUseCase("article/tech/Sample.md"))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.weight(4f)
        ) {
            fileContent.forEach {
                when (it) {
                    is MarkdownObject.Header -> MarkdownHeader(header = it)
                    is MarkdownObject.ListItem -> MarkdownListItem(item = it)
                    is MarkdownObject.Bold -> MarkdownBoldText(item = it)
                    is MarkdownObject.Italic -> MarkdownItalicText(item = it)
                    is MarkdownObject.InlineCode -> markdown.MarkdownCode(item = it)
                    is MarkdownObject.CodeBlock -> markdown.MarkdownCodeBlock(item = it)
                    is MarkdownObject.Strikethrough -> markdown.MarkdownStrikethrough(item = it)
                    is MarkdownObject.Link -> markdown.MarkdownLink(item = it)
                    is MarkdownObject.Image -> markdown.MarkdownImage(item = it)
                    is MarkdownObject.HorizontalBorder -> markdown.MarkdownHorizontalBorder()
                    is MarkdownObject.Comment -> Unit
                    is MarkdownObject.Text -> Text(text = it.text, color = textColor())
                    is MarkdownObject.Table -> Unit
                    is MarkdownObject.Quote -> Unit
                    is MarkdownObject.BreakLine -> Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
