package markdown

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.MarkdownElementTypes
import com.mikepenz.markdown.compose.elements.MarkdownHeader
import androidx.compose.ui.text.TextStyle
import TitleLine
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import accentTextColor
import codeBlockBackgroundColor
import com.mikepenz.markdown.model.ImageTransformer
import com.mikepenz.markdown.model.ImageData
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.compose.LocalPlatformContext
import kotlinx.browser.window
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.AsyncImagePainter

@Composable
fun CustomHeader1(content: String, node: ASTNode, style: TextStyle) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 32.sp,
                lineHeight = 40.sp
            )
        )
        TitleLine()
    }
}

@Composable
fun CustomHeader2(content: String, node: ASTNode, style: TextStyle) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                lineHeight = 36.sp
            )
        )
        TitleLine()
    }
}

@Composable
fun CustomHeader3(content: String, node: ASTNode, style: TextStyle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                lineHeight = 32.sp
            )
        )
    }
}

@Composable
fun CustomHeader4(content: String, node: ASTNode, style: TextStyle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                lineHeight = 28.sp
            )
        )
    }
}

@Composable
fun CustomHeader5(content: String, node: ASTNode, style: TextStyle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                lineHeight = 26.sp
            )
        )
    }
}

@Composable
fun CustomHeader6(content: String, node: ASTNode, style: TextStyle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        MarkdownHeader(
            content = content,
            node = node,
            style = style.copy(
                color = accentTextColor(),
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        )
    }
}

@Composable
fun CustomCodeBlock(content: String, node: ASTNode) {
    val isFence = node.type == MarkdownElementTypes.CODE_FENCE
    val languageName = node.children.find { it.type == org.intellij.markdown.MarkdownTokenTypes.FENCE_LANG }?.let {
        content.subSequence(it.startOffset, it.endOffset).toString()
    }
    val syntaxLanguage = remember(languageName) { languageName?.let { dev.snipme.highlights.model.SyntaxLanguage.getByName(it) } }

    val code = if (isFence) {
        if (node.children.size >= 3) {
            val start = node.children[2].startOffset
            val end = node.children[(node.children.size - 2).coerceAtLeast(2)].endOffset
            content.subSequence(start, end).toString().trimIndent()
        } else ""
    } else {
        if (node.children.isNotEmpty()) {
            val start = node.children[0].startOffset
            val end = node.children[node.children.size - 1].endOffset
            content.subSequence(start, end).toString().trimIndent()
        } else ""
    }

    val codeHighlights = remember(code) {
        dev.snipme.highlights.Highlights.Builder()
            .code(code)
            .theme(dev.snipme.highlights.model.SyntaxThemes.darcula(darkMode = true))
            .let { if (syntaxLanguage != null) it.language(syntaxLanguage) else it }
            .build()
    }

    Column(
        modifier = Modifier
            .background(color = codeBlockBackgroundColor())
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append(codeHighlights.getCode())
                codeHighlights.getHighlights()
                    .filterIsInstance<dev.snipme.highlights.model.ColorHighlight>()
                    .forEach {
                        addStyle(
                            SpanStyle(color = Color(it.rgb).copy(alpha = 1f)),
                            start = it.location.start,
                            end = it.location.end,
                        )
                    }
                codeHighlights.getHighlights()
                    .filterIsInstance<dev.snipme.highlights.model.BoldHighlight>()
                    .forEach {
                        addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start = it.location.start,
                            end = it.location.end,
                        )
                    }
            },
            color = Color.White
        )
    }
}



object CustomImageTransformer : ImageTransformer {
    @Composable
    override fun transform(link: String): ImageData {
        val resolvedLink = if (link.startsWith("http")) link else {
            val base = window.location.origin + window.location.pathname
            val separator = if (base.endsWith("/") || link.startsWith("/")) "" else "/"
            "$base$separator$link"
        }
        return rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(resolvedLink)
                .size(coil3.size.Size.ORIGINAL)
                .build()
        ).let { ImageData(it) }
    }

    @Composable
    override fun intrinsicSize(painter: Painter): Size {
        var size by remember(painter) { mutableStateOf(painter.intrinsicSize) }
        if (painter is AsyncImagePainter) {
            val painterState = painter.state.collectAsState()
            val intrinsicSize = painterState.value.painter?.intrinsicSize
            intrinsicSize?.also { size = it }
        }
        return size
    }
}
