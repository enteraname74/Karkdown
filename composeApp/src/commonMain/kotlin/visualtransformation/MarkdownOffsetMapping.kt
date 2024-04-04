package visualtransformation

import androidx.compose.ui.text.input.OffsetMapping

class MarkdownOffsetMapping(private val maxTextOffset: Int) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return if (offset >= maxTextOffset) maxTextOffset else offset
    }

    override fun transformedToOriginal(offset: Int): Int = offset

}