package visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import model.textutils.*

/**
 * Handles the style transformation of markdown elements.
 */
abstract class MarkdownTransformation : VisualTransformation {

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
    protected abstract fun AnnotatedString.Builder.handleBoldWord(word: String)

    /**
     * Handle the rendering of an italic word.
     */
    protected abstract fun AnnotatedString.Builder.handleItalicWord(word: String)

    /**
     * Handle the rendering of a bold and italic word.
     */
    protected abstract fun AnnotatedString.Builder.handleBoldAndItalicWord(word: String)

    /**
     * Handle the rendering of a strikethrough word.
     */
    protected abstract fun AnnotatedString.Builder.handleStrikethroughWord(word: String)

    /**
     * Handle the rendering of a link word.
     */
    protected abstract fun AnnotatedString.Builder.handleLinkWord(word: String)

    /**
     * Handle the rendering of a code word.
     */
    protected abstract fun AnnotatedString.Builder.handleCode(word: String)

    /**
     * Extract a list of each markdown elements in a sentence.
     */
    private fun extractMarkdownAndWordsWithPosition(sentence: String): List<String> {
        val star = """\*{1,3}.*?\*{1,3}""".toRegex()
        val link = """\[.*?]\(.*?\)""".toRegex()
        val strike = """~~.*?~~""".toRegex()
        val code = """(`{1,3})[^`]+\1""".toRegex()

        val markdownPattern = Regex("""$star|$link|$strike|$code""")
        val markdownMatches = markdownPattern.findAll(sentence).map { it.value to it.range }.toList().map { it.first }.toTypedArray()

        val sentenceWithoutMarkdown = sentence.split(*markdownMatches)

        val finalList = ArrayList<String>()

        markdownMatches.forEachIndexed { i, s ->
            finalList.add(sentenceWithoutMarkdown[i])
            finalList.add(s)
        }
        finalList.add(sentenceWithoutMarkdown.last())

        return finalList
    }


    /**
     * Build the final string used by a text field.
     * @param text the initial text to transform
     *
     * @return the transformed text as an AnnotatedString.
     */
    private fun buildFinalString(text: String): AnnotatedString = buildAnnotatedString {
        extractMarkdownAndWordsWithPosition(sentence = text).forEach { word ->
            if (word.isBoldAndItalic()) handleBoldAndItalicWord(word = word)
            else if (word.isBold()) handleBoldWord(word = word)
            else if (word.isItalic()) handleItalicWord(word = word)
            else if (word.isStrikethrough()) handleStrikethroughWord(word = word)
            else if (word.isLink()) handleLinkWord(word = word)
            else if (word.isCode()) handleCode(word = word)
            else append(word)
        }
    }
}