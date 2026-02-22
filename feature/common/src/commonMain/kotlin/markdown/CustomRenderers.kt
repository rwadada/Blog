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

    Column(
        modifier = Modifier
            .background(color = codeBlockBackgroundColor())
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        code.split("\n").forEach {
            val rawText = it.trimStart()
            val indent = it.length - rawText.length
            val indentText = " ".repeat(indent)
            Text(
                text = buildAnnotatedString {
                    append(indentText)
                }.plus(convertToAnnotatedString(rawText)),
                color = Color.White
            )
        }
    }
}

private enum class TokenType {
    KEYWORD, // ex) fun
    FUNCTION_NAME, // ex) main
    STRING, // ex) "Hello, World!"
    OTHER // White color for anything else
}

private data class Token(val text: String, val type: TokenType) {
    fun getText() = when (type) {
        TokenType.KEYWORD -> "$text "
        TokenType.FUNCTION_NAME -> text
        TokenType.STRING -> "$text "
        TokenType.OTHER -> "$text "
    }
}

private fun getColorForTokenType(type: TokenType): Color {
    return when (type) {
        TokenType.KEYWORD -> Color(0xFFD17555) // Orange
        TokenType.FUNCTION_NAME -> Color(0xFF1DB2DC) // Blue
        TokenType.STRING -> Color(0xFF3F8D52) // Green
        TokenType.OTHER -> Color(0xFFFFFFFF) // White
    }
}

private fun determineTokenType(text: String): TokenType {
    return when {
        text == "fun" -> TokenType.KEYWORD
        text.matches(Regex("""\b\w+\b""")) -> TokenType.FUNCTION_NAME
        text.matches(Regex("""".+?"""")) -> TokenType.STRING
        else -> TokenType.OTHER
    }
}

private fun tokenizeCode(code: String): List<Token> {
    val regex = Regex("""(\bfun\b|\b\w+\b|".+?"|\S)""")
    val matches = regex.findAll(code)
    return matches.map { Token(it.value, determineTokenType(it.value)) }.toList()
}

private fun convertToAnnotatedString(code: String): AnnotatedString {
    val tokens = tokenizeCode(code)
    val annotatedString = buildAnnotatedString {
        for (token in tokens) {
            withStyle(style = SpanStyle(color = getColorForTokenType(token.type))) {
                append(token.getText())
            }
        }
    }
    return annotatedString
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
