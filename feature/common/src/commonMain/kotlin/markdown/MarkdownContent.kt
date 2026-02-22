import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import markdown.*

@Composable
fun MarkdownContent(
    date: String,
    content: String,
    modifier: Modifier = Modifier,
    composableItem: List<@Composable () -> Unit> = emptyList()
) {
    Column(modifier = modifier) {
        Text(
            text = date,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = accentTextColor(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        val parts = content.split("[COMPOSABLE_CONTENT]")

        parts.forEachIndexed { index, part ->
            if (part.isNotBlank()) {
                Markdown(
                    content = part,
                    colors = DefaultMarkdownColors(
                        text = accentTextColor(),
                        codeText = accentTextColor(),
                        inlineCodeText = accentTextColor(),
                        linkText = selectedTextColor(),
                        codeBackground = surfaceColor(),
                        inlineCodeBackground = surfaceColor(),
                        dividerColor = secondaryTextColor()
                    ),
                    typography = DefaultMarkdownTypography(
                        h1 = MaterialTheme.typography.h1,
                        h2 = MaterialTheme.typography.h2,
                        h3 = MaterialTheme.typography.h3,
                        h4 = MaterialTheme.typography.h4,
                        h5 = MaterialTheme.typography.h5,
                        h6 = MaterialTheme.typography.h6,
                        text = MaterialTheme.typography.body1,
                        code = MaterialTheme.typography.body2.copy(fontFamily = FontFamily.Monospace),
                        inlineCode = MaterialTheme.typography.body2.copy(fontFamily = FontFamily.Monospace),
                        quote = MaterialTheme.typography.body2,
                        paragraph = MaterialTheme.typography.body1,
                        ordered = MaterialTheme.typography.body1,
                        bullet = MaterialTheme.typography.body1,
                        list = MaterialTheme.typography.body1,
                        link = MaterialTheme.typography.body1
                    ),
                    components = markdownComponents(
                        heading1 = { CustomHeader1(it.content, it.node, it.typography.h1) },
                        heading2 = { CustomHeader2(it.content, it.node, it.typography.h2) },
                        heading3 = { CustomHeader3(it.content, it.node, it.typography.h3) },
                        heading4 = { CustomHeader4(it.content, it.node, it.typography.h4) },
                        heading5 = { CustomHeader5(it.content, it.node, it.typography.h5) },
                        heading6 = { CustomHeader6(it.content, it.node, it.typography.h6) },
                        codeBlock = { CustomCodeBlock(it.content, it.node) },
                        codeFence = { CustomCodeBlock(it.content, it.node) }
                    ),
                    imageTransformer = CustomImageTransformer
                )
            }

            if (index < parts.size - 1 && index < composableItem.size) {
                composableItem[index]()
            }
        }
    }
}
