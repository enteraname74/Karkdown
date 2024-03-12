package state

import model.markdown.MarkdownElement

/**
 * UI State of the main screen.
 */
data class MainScreenState(
    val fileContent: List<MarkdownElement> = emptyList(),
    val fileName: String = "",
    val userPosition: Int = 0,
    val isSelectingFile: Boolean = false
)
