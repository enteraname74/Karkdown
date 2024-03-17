package visualtransformation

import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TransformedText
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
            else if (rowWords[realIndex].isLink()) handleLinkWord(word = rowWords[realIndex])
            else append(word)
            // We need to append the whitespaces between each word :
            append(whitespaces.getOrElse(index + 1) { "" })
        }
    }
}