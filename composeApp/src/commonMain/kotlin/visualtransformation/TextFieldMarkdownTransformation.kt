package visualtransformation

import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextDecoration
import model.textutils.*
import theme.KarkdownColorTheme

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when editing the text field content.
 */
class TextFieldMarkdownTransformation : MarkdownTransformation() {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildFinalString(text.toString())
        return TransformedText(
            text = transformedText,
            offsetMapping = MarkdownOffsetMapping(maxTextOffset = transformedText.length)
        )
    }

    /**
     * Handle the rendering of a bold word.
     */
    override fun AnnotatedString.Builder.handleBoldWord(word: String) {
        val subParts = word.split("\\*{2}|_{2}".toRegex())
        subParts.forEachIndexed { i, subPart ->
            if (i % 2 == 0) {
                append(subPart)
            } else {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    val boldCharacters = if (word.isStarBold()) "**" else "__"
                    append("$boldCharacters$subPart$boldCharacters")
                }
            }
        }
    }

    /**
     * Handle the rendering of an italic word.
     */
    override fun AnnotatedString.Builder.handleItalicWord(word: String) {
        val subParts = word.split("[*_]".toRegex())
        subParts.forEachIndexed { i, subPart ->
            if (i % 2 == 0) {
                append(subPart)
            } else {
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    val italicCharacters = if (word.isStarItalic()) "*" else "_"
                    append("$italicCharacters$subPart$italicCharacters")
                }
            }
        }
    }

    /**
     * Handle the rendering of a bold and italic word.
     */
    override fun AnnotatedString.Builder.handleBoldAndItalicWord(word: String) {
        val subParts = word.split("\\*{3}|_{3}".toRegex())
        subParts.forEachIndexed { i, subPart ->
            if (i % 2 == 0) {
                append(subPart)
            } else {
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    val boldAndItalicCharacters = if (word.isStarBoldAndItalic()) "***" else "___"
                    append("$boldAndItalicCharacters$subPart$boldAndItalicCharacters")
                }
            }
        }
    }

    override fun AnnotatedString.Builder.handleStrikethroughWord(word: String) {
        val subParts = word.split("~{2}".toRegex())
        subParts.forEachIndexed { i, subPart ->
            if (i % 2 == 0) {
                append(subPart)
            } else {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.LineThrough
                    )
                ) {
                    append("~~$subPart~~")
                }
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    override fun AnnotatedString.Builder.handleLinkWord(word: String) {
        val regex = Regex("(.*)(\\[.*\\]\\(.*?\\))(.*)")
        val subParts = regex.find(word)!!.destructured.toList()

        val startUrlIndex = subParts[0].length
        val endUrlIndex = startUrlIndex + subParts[1].length

        append(subParts[0])
        withStyle(
            style = SpanStyle(
                color = KarkdownColorTheme.colorScheme.accent,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(subParts[1])
        }
        append(subParts[2])

        addUrlAnnotation(
            UrlAnnotation(url = subParts[1].linkUrl()),
            start = startUrlIndex,
            end = endUrlIndex
        )
    }

    fun extractMarkdownAndWordsWithPosition(sentence: String): Pair<List<Pair<String, IntRange>>, List<Pair<String, IntRange>>> {
        val markdownPattern = Regex("""\*{1,2}.*?\*{1,2}|\[.*?]\(.*?\)""")
        val markdownMatches = markdownPattern.findAll(sentence).map { it.value to it.range }.toList()

        val wordsPattern = Regex("""(?:\*{1,2}.*?\*{1,2}|\[.*?]\(.*?\))|\b(\w+)\b""")
        val wordMatches = wordsPattern.findAll(sentence).mapNotNull {
            if (it.groups[1] != null) it.groups[1]!!.value to it.groups[1]!!.range else null
        }.toList()

        return Pair(markdownMatches, wordMatches)
    }

    /**
     * Build the final string used by a text field.
     * @param text the initial text to transform
     *
     * @return the transformed text as an AnnotatedString.
     */
    override fun buildFinalString(text: String): AnnotatedString = buildAnnotatedString {
        val whitespaces = text.split("\\S+".toRegex())
        val words = text.split("\\s+".toRegex())

        words.forEachIndexed { index, word ->
            if (word.isBold()) handleBoldWord(word = word)
            else if (word.isItalic()) handleItalicWord(word = word)
            else if (word.isBoldAndItalic()) handleBoldAndItalicWord(word = word)
            else if (word.isStrikethrough()) handleStrikethroughWord(word = word)
            else if (word.isLink()) handleLinkWord(word = word)
            else append(word)
            // We need to append the whitespaces between each word :
            append(whitespaces.getOrElse(index + 1) { "" })
        }
    }
}