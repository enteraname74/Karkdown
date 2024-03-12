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
     * Create a new line.
     */
    data class CreateNewLine(val nextPos: Int): MainScreenEvent

    /**
     * Define which line is focused and can be edited.
     * When focusing a line, the user goes into editing mode
     */
    data class SetFocusedLine(val pos: Int): MainScreenEvent
}

