package markdown

sealed interface MarkdownObject {

    data class Header(val level: Level, val text: String) : MarkdownObject {
        enum class Level {
            H1, H2, H3, H4, H5, H6
        }
    }

    sealed interface ListItem : MarkdownObject {
        val text: String
        val level: Int

        data class DotList(override val text: String, override val level: Int) : ListItem
        data class OrderedList(override val text: String, override val level: Int) : ListItem
    }

    data object BreakLine : MarkdownObject

    data class Text(val text: String) : MarkdownObject

    data class InlineCode(val code: String) : MarkdownObject

    data class CodeBlock(val code: String) : MarkdownObject

    data class Link(val text: String, val url: String) : MarkdownObject

    data class Quote(val text: String) : MarkdownObject

    data class Image(
        val url: String,
        val alt: String,
        val width: Int? = null,
        val height: Int? = null
    ) :
        MarkdownObject

    data class Table(
        val headers: List<String>,
        val rows: List<List<String>>
    ) : MarkdownObject

    data class Bold(val text: String) : MarkdownObject

    data class Italic(val text: String) : MarkdownObject

    data class Strikethrough(val text: String) : MarkdownObject

    data object HorizontalBorder : MarkdownObject

    data class Note(
        val title: String,
        val text: String
    ) : MarkdownObject
}
