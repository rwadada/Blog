import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import markdown.MarkdownHeader
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        fileContent.forEach {
            when (it) {
                is MarkdownObject.Header -> {
                    MarkdownHeader(header = it)
                }

                is MarkdownObject.ListItem -> {
                    MarkdownListItem(item = it)
                }

                is MarkdownObject.Text -> {
                    Text(text = it.text, color = textColor())
                }

                else -> {
                    Unit
                }
            }
        }
    }
}
