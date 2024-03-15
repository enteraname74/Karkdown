package utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import model.textutils.isBold
import model.textutils.isItalic

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when editing the text field content.
 */
class TextFieldMarkdownTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = buildFinalString(text.toString()),
        offsetMapping = OffsetMapping.Identity
    )

    /**
     * Build the final string used by a text field.
     * @param text the initial text to transform
     *
     * @return the transformed text as an AnnotatedString.
     */
    private fun buildFinalString(text: String): AnnotatedString = buildAnnotatedString {
        val whitespaces = text.split("\\S+".toRegex())
        val words = text.split("\\s+".toRegex())

        words.forEachIndexed { index, word ->
            if (word.isBold()) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(word)
                }
            } else if (word.isItalic()) {
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append(word)
                }
            } else append(word)
            // We need to append the whitespaces between each word :
            append(whitespaces.getOrElse(index + 1) { "" })
        }
    }
}