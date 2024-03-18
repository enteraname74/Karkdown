package visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Handles the style transformation of markdown elements.
 */
abstract class MarkdownTransformation : VisualTransformation {
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
     * Extract a list of each markdown elements in a sentence.
     */
    fun extractMarkdownAndWordsWithPosition(sentence: String): List<String> {
        val markdownPattern = Regex("""\*{1,2}.*?\*{1,2}|\[.*?]\(.*?\)""")
        val matches = ArrayList(markdownPattern.findAll(sentence).map { it.value to it.range }.toList())

        val wordsPattern = Regex("""(?:\*{1,2}.*?\*{1,2}|\[.*?]\(.*?\))|\b(\w+)\b""")
        val wordMatches = ArrayList(
            wordsPattern.findAll(sentence).mapNotNull {
                if (it.groups[1] != null) it.groups[1]!!.value to it.groups[1]!!.range else null
            }.toList()
        )

        matches.addAll(wordMatches)

        return matches.sortedBy { match ->
            match.second.first
        }.map {
            it.first
        }
    }


    /**
     * Build the final string used by a text field.
     * @param text the initial text to transform
     *
     * @return the transformed text as an AnnotatedString.
     */
    protected abstract fun buildFinalString(text: String): AnnotatedString
}