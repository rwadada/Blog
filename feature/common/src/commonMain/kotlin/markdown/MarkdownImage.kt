package markdown

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MarkdownImage(item: MarkdownObject.Image) {
    Image(
        painter = painterResource(item.path),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}
