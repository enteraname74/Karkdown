package state

import model.markdown.EditableText
import model.markdown.MarkdownElement
import kotlin.math.max

/**
 * UI State of the main screen.
 */
data class MainScreenState(
    val fileContent: List<MarkdownElement> = emptyList(),
    val fileName: String = "",
    val userPosition: Int = 0
)
