package state

import model.MarkdownElement
import strings.appStrings
import java.nio.file.Path
import kotlin.io.path.name

/**
 * UI State of the main screen.
 */
data class MainScreenState(
    val fileContent: List<MarkdownElement> = emptyList(),
    val filepath : Path? = null,
    var filename: String = filepath?.name ?: appStrings.newFilename,
    val userPosition: Int = 0,
    val isSelectingFile: Boolean = false,
    val isSelectingFolder: Boolean = false,
    val isSettingFileName: Boolean = false,
    val shouldShowCorrectFileSavedResult: Boolean = false,
    val shouldShowErrorFileSavedResult: Boolean = false,
    val isDataUpdated: Boolean = true
)
