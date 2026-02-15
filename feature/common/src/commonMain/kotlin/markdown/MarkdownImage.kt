package markdown

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun MarkdownImage(item: MarkdownObject.Image) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = item.path,
            contentDescription = item.alt,
            modifier = Modifier.weight(4f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
