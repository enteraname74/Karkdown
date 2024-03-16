package visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Handles the style transformation of markdown elements.
 */
abstract class MarkdownTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = buildFinalString(text.toString()),
        offsetMapping = OffsetMapping.Identity
    )

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
     * Build the final string used by a text field.
     * @param text the initial text to transform
     *
     * @return the transformed text as an AnnotatedString.
     */
    protected abstract fun buildFinalString(text: String): AnnotatedString
}