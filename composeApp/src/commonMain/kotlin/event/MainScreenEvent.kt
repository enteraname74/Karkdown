package event

/**
 * Events for the main screen.
 */
sealed interface MainScreenEvent {
    /**
     * Indicate to open a file.
     */
    data class OpenFile(val file: String): MainScreenEvent

    /**
     * Set the current text the user is currently typing.
     */
    data class SetCurrentText(val text: String): MainScreenEvent

    /**
     * Add a new line at a given position.
     * If the position is -1, then the line should be added at the end of the file.
     */
    data class AddNewLine(val text: String, val pos: Int): MainScreenEvent

    /**
     * Define which line is focused and can be edited.
     */
    data class SetFocusedLine(val pos: Int): MainScreenEvent
}

