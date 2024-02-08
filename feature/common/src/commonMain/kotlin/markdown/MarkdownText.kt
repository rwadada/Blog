package markdown

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun MarkdownText(item: MarkdownObject.Text) {
    val annotatedString = convertToAnnotatedString(item.text)
    Text(text = annotatedString)
}

private fun convertToAnnotatedString(input: String): AnnotatedString {
    return buildAnnotatedString {
        val spanInfoArray = getSpanIfoArray(input)
        spanInfoArray.forEach {
            when (it.type) {
                SpanType.INLINE_CODE -> withStyle(
                    SpanStyle(
                        background = Color.LightGray,
                        color = Color.Red
                    )
                ) {
                    append(it.text)
                }

                SpanType.BOLD -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(it.text)
                }

                SpanType.ITALIC -> withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(it.text)
                }

                SpanType.STRIKETHROUGH -> withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append(it.text)
                }

                SpanType.NORMAL -> append(it.text)
            }
        }
    }
}

private fun getSpanIfoArray(input: String): List<SpanInfo> {
    val matchedInlineCode = extractMatchedStrings(input, SpanType.INLINE_CODE)
    val matchedBold = matchedInlineCode.let {
        val result = mutableListOf<SpanInfo>()
        it.forEach { spanInfo ->
            if (spanInfo.type == SpanType.NORMAL) {
                result.addAll(extractMatchedStrings(spanInfo.text, SpanType.BOLD))
            } else {
                result.add(spanInfo)
            }
        }
        result
    }
    val matchedItalic = matchedBold.let {
        val result = mutableListOf<SpanInfo>()
        it.forEach { spanInfo ->
            if (spanInfo.type == SpanType.NORMAL) {
                result.addAll(extractMatchedStrings(spanInfo.text, SpanType.ITALIC))
            } else {
                result.add(spanInfo)
            }
        }
        result
    }
    val matchedStrikethrough = matchedItalic.let {
        val result = mutableListOf<SpanInfo>()
        it.forEach { spanInfo ->
            if (spanInfo.type == SpanType.NORMAL) {
                result.addAll(extractMatchedStrings(spanInfo.text, SpanType.STRIKETHROUGH))
            } else {
                result.add(spanInfo)
            }
        }
        result
    }
    return matchedStrikethrough
}

private fun extractMatchedStrings(input: String, type: SpanType): List<SpanInfo> {
    val matchedStrings = mutableListOf<SpanInfo>()
    var currentIndex = 0
    type.regex.findAll(input).forEach { matchResult ->
        matchedStrings.add(
            SpanInfo(
                text = input.substring(currentIndex, matchResult.range.first),
                type = SpanType.NORMAL
            )
        )
        matchedStrings.add(
            SpanInfo(
                text = matchResult.value.let {
                    when (type) {
                        SpanType.INLINE_CODE -> it.drop(1).dropLast(1)
                        SpanType.BOLD -> it.drop(2).dropLast(2)
                        SpanType.ITALIC -> it.drop(1).dropLast(1)
                        SpanType.STRIKETHROUGH -> it.drop(2).dropLast(2)
                        SpanType.NORMAL -> it
                    }
                },
                type = type
            )
        )
        currentIndex = matchResult.range.last + 1
    }
    if (currentIndex < input.length) {
        matchedStrings.add(
            SpanInfo(
                text = input.substring(currentIndex),
                type = SpanType.NORMAL
            )
        )
    }
    return matchedStrings
}

private data class SpanInfo(val text: String, val type: SpanType)
private enum class SpanType(val regex: Regex) {
    INLINE_CODE(Regex("`([^`]*)`")),
    BOLD(Regex("\\*\\*([^*]*)\\*\\*")),
    ITALIC(Regex("\\*([^*]*)\\*")),
    STRIKETHROUGH(Regex("~~([^~]*)~~")),
    NORMAL(Regex(".*"))
}
