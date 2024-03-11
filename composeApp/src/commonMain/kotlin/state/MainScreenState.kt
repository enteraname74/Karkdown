package state

/**
 * UI State of the main screen.
 */
data class MainScreenState(
    val fileContent: List<String> = emptyList(),
    val fileName: String = "",
    val currentLine: Int = -1
)
