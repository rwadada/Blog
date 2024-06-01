import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import markdown.MarkdownContent
import markdown.MarkdownObject
import usecase.ParseMarkDownStringUseCase
import usecase.ReadFileUseCase


@Composable
fun TechPage(index: Int?) {
    val readFileUseCase = ReadFileUseCase()
    val parseMarkDownStringUseCase = ParseMarkDownStringUseCase()
    var fileContent: List<MarkdownObject> by remember { mutableStateOf(emptyList()) }
    val techBlogItems = blogItems.filter { it.type == BlogItem.Type.TECH }
    val item = techBlogItems[index ?: techBlogItems.lastIndex]
    LaunchedEffect(Unit) {
        fileContent = parseMarkDownStringUseCase(readFileUseCase(item.path))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        MarkdownContent(
            date = item.date,
            content = fileContent,
            modifier = Modifier.weight(4f),
            composableItem = item.composableItems
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
