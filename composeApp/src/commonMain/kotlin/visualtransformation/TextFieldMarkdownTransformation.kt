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
    /**
     * Handle the rendering of a bold word.
     */
    override fun AnnotatedString.Builder.handleBoldWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold
            )
        ) {
            append(word)
        }
    }

    /**
     * Handle the rendering of an italic word.
     */
    override fun AnnotatedString.Builder.handleItalicWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic
            )
        ) {
            append(word)
        }
    }

    /**
     * Handle the rendering of a bold and italic word.
     */
    override fun AnnotatedString.Builder.handleBoldAndItalicWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(word)
        }
    }

    override fun AnnotatedString.Builder.handleStrikethroughWord(word: String) {
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.LineThrough
            )
        ) {
            append(word)
        }
    }

    override fun AnnotatedString.Builder.handleCode(word: String) {
        withStyle(
            style = SpanStyle(
                background = KarkdownColorTheme.colorScheme.secondaryContainer
            )
        ) {
            append(word)
        }
    }

    override fun AnnotatedString.Builder.handleImageLink(word: String) {
        withStyle(
            style = SpanStyle(
                color = KarkdownColorTheme.colorScheme.accent,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(word)
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
}