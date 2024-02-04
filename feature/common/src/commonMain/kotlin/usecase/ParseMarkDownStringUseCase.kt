package usecase

import markdown.MarkdownObject

class ParseMarkDownStringUseCase {
    operator fun invoke(markdownString: String): List<MarkdownObject> {
        val input = markdownString.split("\n")

        return parseMarkDownString(input)
    }

    private fun parseMarkDownString(input: List<String>): List<MarkdownObject> {
        val markdownObjects = mutableListOf<MarkdownObject>()
        var codeBlockObject: MarkdownObject.CodeBlock? = null
        input.forEach {
            if (codeBlockObject != null) {
                codeBlockObject = if (it.startsWith("```")) {
                    markdownObjects.add(codeBlockObject!!.copy(code = codeBlockObject!!.code.dropLast(1)))
                    null
                } else {
                    codeBlockObject?.copy(code = codeBlockObject!!.code + it + "\n")
                }
            } else {
                when {
                    it.startsWith("#") -> {
                        val level = when (it.takeWhile { it == '#' }.length) {
                            1 -> MarkdownObject.Header.Level.H1
                            2 -> MarkdownObject.Header.Level.H2
                            3 -> MarkdownObject.Header.Level.H3
                            4 -> MarkdownObject.Header.Level.H4
                            5 -> MarkdownObject.Header.Level.H5
                            6 -> MarkdownObject.Header.Level.H6
                            else -> throw IllegalArgumentException("Invalid header level")
                        }
                        markdownObjects.add(
                            MarkdownObject.Header(
                                level,
                                it.dropWhile { it == '#' })
                        )
                    }

                    Regex("""^\s*- .+$""").matches(it) -> {
                        val listItemText = it.trimStart().drop(2)
                        val level = (it.length - it.trimStart().length) / 4
                        markdownObjects.add(MarkdownObject.ListItem.DotList(listItemText, level))
                    }

                    Regex("""^\s*(\d+\.|[a-z]\.)\s.*""").matches(it) -> {
                        val listItemText = it.trimStart()
                        val level = (it.length - it.trimStart().length) / 4
                        markdownObjects.add(
                            MarkdownObject.ListItem.OrderedList(
                                listItemText,
                                level
                            )
                        )
                    }

                    Regex("""\*\*([^*]+)\*\*""").matches(it) -> {
                        val boldText = it.replace(Regex("""\*\*([^*]+)\*\*"""), "$1")
                        markdownObjects.add(MarkdownObject.Bold(boldText))
                    }

                    Regex("""\*([^*]+)\*""").matches(it) -> {
                        val italicText = it.replace(Regex("""\*([^*]+)\*"""), "$1")
                        markdownObjects.add(MarkdownObject.Italic(italicText))
                    }

                    Regex("""`([^`]+)`""").matches(it) -> {
                        val inlineCode = it.replace(Regex("""`([^`]+)`"""), "$1")
                        markdownObjects.add(MarkdownObject.InlineCode(inlineCode))
                    }

                    Regex("""~~([^~]+)~~""").matches(it) -> {
                        val strikethroughText = it.replace(Regex("""~~([^~]+)~~"""), "$1")
                        markdownObjects.add(MarkdownObject.Strikethrough(strikethroughText))
                    }

                    Regex("""^\s*>\s.*""").matches(it) -> {
                        val quoteText = it.trimStart().drop(2)
                        markdownObjects.add(MarkdownObject.Quote(quoteText))
                    }

                    Regex("""^\s*!\[.*]\(.*\)""").matches(it) -> {
                        val url = it.substringAfter("(").substringBefore(")")
                        val alt = it.substringAfter("[").substringBefore("]")
                        markdownObjects.add(MarkdownObject.Image(url, alt))
                    }

                    Regex("""^\s*\[.*]\(.*\)""").matches(it) -> {
                        val url = it.substringAfter("(").substringBefore(")")
                        val text = it.substringAfter("[").substringBefore("]")
                        markdownObjects.add(MarkdownObject.Link(text, url))
                    }

                    it == "---" -> markdownObjects.add(MarkdownObject.HorizontalBorder)

                    Regex("""^\s*\[//]: # \(.+\)""").matches(it) -> {
                        val commentText = it.substringAfter(" ").substringAfter("(").substringBefore(")")
                        markdownObjects.add(MarkdownObject.Comment(commentText))
                    }

                    it.startsWith("```") -> {
                        codeBlockObject = MarkdownObject.CodeBlock("", it.drop(3))
                    }

                    it.isEmpty() -> markdownObjects.add(MarkdownObject.BreakLine)

                    else -> markdownObjects.add(MarkdownObject.Text(it))
                }
            }
        }
        return markdownObjects
    }
}
