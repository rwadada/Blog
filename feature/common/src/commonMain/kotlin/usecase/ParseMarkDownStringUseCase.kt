package usecase

import markdown.MarkdownObject

class ParseMarkDownStringUseCase {
    operator fun invoke(markdownString: String): List<MarkdownObject> {
        val input = markdownString.split("\n")

        return parseMarkDownString(input)
    }

    private fun parseMarkDownString(input: List<String>): List<MarkdownObject> {
        val markdownObjects = mutableListOf<MarkdownObject>()
        input.forEach {
            when {
                it.startsWith("#") -> {
                    val level = when(it.takeWhile { it == '#' }.length) {
                        1 -> MarkdownObject.Header.Level.H1
                        2 -> MarkdownObject.Header.Level.H2
                        3 -> MarkdownObject.Header.Level.H3
                        4 -> MarkdownObject.Header.Level.H4
                        5 -> MarkdownObject.Header.Level.H5
                        6 -> MarkdownObject.Header.Level.H6
                        else -> throw IllegalArgumentException("Invalid header level")
                    }
                    markdownObjects.add(MarkdownObject.Header(level, it.dropWhile { it == '#' }))
                }
                Regex("""^\s*- .+$""").matches(it) -> {
                    val listItemText = it.trimStart().drop(2)
                    val level = (it.length - it.trimStart().length) / 4
                    markdownObjects.add(MarkdownObject.ListItem.DotList(listItemText, level))
                }
                Regex("""^\s*(\d+\.|[a-z]\.)\s.*""").matches(it) -> {
                    val listItemText = it.trimStart()
                    val level = (it.length - it.trimStart().length) / 4
                    markdownObjects.add(MarkdownObject.ListItem.OrderedList(listItemText, level))
                }
                else -> markdownObjects.add(MarkdownObject.Text(it))
            }
        }
        return markdownObjects
    }
}
