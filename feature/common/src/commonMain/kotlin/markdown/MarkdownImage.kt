package markdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MarkdownImage(item: MarkdownObject.Image) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(item.path),
            contentDescription = item.alt,
            modifier = Modifier.weight(4f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
