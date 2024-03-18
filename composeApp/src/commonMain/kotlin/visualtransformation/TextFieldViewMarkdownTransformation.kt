package visualtransformation

import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import model.textutils.*
import theme.KarkdownColorTheme

/**
 * Implementation of the VisualTransformation interface for building personalized text field content based on markdown
 * properties when the text field is not in edit mode.
 */
class TextFieldViewMarkdownTransformation(
    private val rowData: String
) : MarkdownTransformation() {
    override fun AnnotatedString.Builder.handleBoldWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold
            )
        ) {
            append(word.boldContent())
        }
    }

    override fun AnnotatedString.Builder.handleItalicWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic
            )
        ) {
            append(word.italicContent())
        }
    }

    override fun AnnotatedString.Builder.handleBoldAndItalicWord(word: String) {
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(word.boldAndItalicContent())
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

    @OptIn(ExperimentalTextApi::class)
    override fun AnnotatedString.Builder.handleLinkWord(word: String) {
        val regex = Regex("(.*)(\\[.*\\]\\(.*?\\))(.*)")
        val subParts = regex.find(word)!!.destructured.toList()

        val startUrlIndex = subParts[0].length
        val endUrlIndex = startUrlIndex + subParts[1].linkName().length

        append(subParts[0])
        withStyle(
            style = SpanStyle(
                color = KarkdownColorTheme.colorScheme.accent,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(subParts[1].linkName())
        }
        append(subParts[2])

        addUrlAnnotation(
            UrlAnnotation(url = subParts[1].linkUrl()),
            start = startUrlIndex,
            end = endUrlIndex
        )
    }
}