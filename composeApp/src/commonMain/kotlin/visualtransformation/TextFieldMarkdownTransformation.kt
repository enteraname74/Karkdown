package visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import model.textutils.*

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when editing the text field content.
 */
class TextFieldMarkdownTransformation : MarkdownTransformation() {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = buildFinalString(text.toString()),
        offsetMapping = OffsetMapping.Identity
    )

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
            else append(word)
            // We need to append the whitespaces between each word :
            append(whitespaces.getOrElse(index + 1) { "" })
        }
    }
}