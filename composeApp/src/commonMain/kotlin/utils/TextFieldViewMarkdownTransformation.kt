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
import model.textutils.boldContent
import model.textutils.isBold
import model.textutils.isItalic
import model.textutils.italicContent

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when the text field is not in edit mode.
 */
class TextFieldViewMarkdownTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = buildFinalString(text.toString()),
        offsetMapping = OffsetMapping.Identity
    )


}

/**
 * Build the final string used by a text field.
 * @param text the initial text to transform
 *
 * @return the transformed text as an AnnotatedString.
 */
fun buildFinalString(text: String): AnnotatedString = buildAnnotatedString {
    val whitespaces = text.split("\\S+".toRegex())
    val words = text.split("\\s+".toRegex())

    words.forEachIndexed { index, word ->
        if (word.isBold()) {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(word.boldContent())
                addStringAnnotation("amogus baka ?", word.boldContent(), 0, word.boldContent().length)
            }
        } else if (word.isItalic()) {
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic
                )
            ) {
                append(word.italicContent())
            }
        } else append(word)
        // We need to append the whitespaces between each word :
        append(whitespaces.getOrElse(index + 1) { "" })
    }
}

fun String.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    append(this@toAnnotatedString)
}