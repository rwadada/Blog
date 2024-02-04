package markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import codeBlockBackgroundColor

@Composable
fun MarkdownCodeBlock(item: MarkdownObject.CodeBlock) {
    Column(
        modifier = Modifier
            .background(color = codeBlockBackgroundColor())
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        item.code.split("\n").forEach {
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

fun main() {
    println("Hello, World!")
}
