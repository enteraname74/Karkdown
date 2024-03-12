package event

/**
 * Events for the main screen.
 */
sealed interface MainScreenEvent {
    /**
     * Indicate to open a file.
     */
    data class OpenFile(val filepath: String): MainScreenEvent

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

    /**
     * Move the user position up by one line.
     */
    data object GoUp: MainScreenEvent

    /**
     * Move the user position down by one line.
     */
    data object GoDown: MainScreenEvent

    /**
     * Remove a line from the file at a given pos.
     */
    data class DeleteLine(val pos: Int): MainScreenEvent

    /**
     * Indicate whether to select or not a file.
     */
    data class ShouldSelectFile(val shouldSelectFile: Boolean): MainScreenEvent

    /**
     * Indicate whether to select or not a folder.
     */
    data class ShouldSelectFolder(val shouldSelectFolder: Boolean): MainScreenEvent

    /**
     * Indicate whether to indicate or not the name of the current file.
     */
    data class ShouldEnterFileName(val shouldSetFileName: Boolean): MainScreenEvent

    /**
     * Indicate to quick save the current file.
     * If the quick save isn't possible (when the file has never been saved before), it will do the SaveAs event.
     */
    data object QuickSaveCurrentFile: MainScreenEvent

    /**
     * Save a file in a specific path with a specific filename.
     */
    data class SaveAsCurrentFile(val path: String, val filename: String): MainScreenEvent

    /**
     * Define the current filename.
     */
    data class SetCurrentFileName(val name: String): MainScreenEvent

    /**
     * Indicate if the indication that the saving of a file has failed should be shown
     */
    data class ShouldShowFileSavingError(val show: Boolean): MainScreenEvent

    /**
     * Indicate if the indication that the saving of a file succeed should be shown.
     */
    data class ShouldShowCorrectFileSaving(val show: Boolean): MainScreenEvent
}

