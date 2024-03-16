package visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import model.textutils.*

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when the text field is not in edit mode.
 */
class TextFieldViewMarkdownTransformation(
    private val rowData: String
) : MarkdownTransformation() {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildFinalString(text.toString())
        return TransformedText(
            text = transformedText,
            offsetMapping = MarkdownOffsetMapping(maxTextOffset = transformedText.length)
        )
    }

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
                    append(subPart)
                }
            }
        }
    }

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
                    append(subPart)
                }
            }
        }
    }

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
                    append(subPart)
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
                    append(subPart)
                }
            }
        }
    }

    override fun buildFinalString(text: String): AnnotatedString = buildAnnotatedString {
        val whitespaces = text.split("\\S+".toRegex())
        val words = text.split("\\s+".toRegex())
        val rowWords = rowData.split("\\s+".toRegex())

        val step = if (rowData.isHeader()) 1 else 0

        words.forEachIndexed { index, word ->
            val realIndex = index + step

            if (rowWords[realIndex].isBold()) handleBoldWord(word = rowWords[realIndex])
            else if (rowWords[realIndex].isItalic()) handleItalicWord(word = rowWords[realIndex])
            else if (rowWords[realIndex].isBoldAndItalic()) handleBoldAndItalicWord(word = rowWords[realIndex])
            else if (rowWords[realIndex].isStrikethrough()) handleStrikethroughWord(word = rowWords[realIndex])
            else append(word)
            // We need to append the whitespaces between each word :
            append(whitespaces.getOrElse(index + 1) { "" })
        }
    }
}