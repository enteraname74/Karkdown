package state

import model.markdownelement.MarkdownElement
import strings.appStrings
import utils.FileHeader
import java.nio.file.Path
import kotlin.io.path.name

/**
 * UI State of the main screen.
 */
data class MainScreenState(
    val filesHeaders: List<FileHeader> = emptyList(),
    val filePos: Int = 0,
    val fileContent: List<MarkdownElement> = emptyList(),
    val filepath : Path? = null,
    var filename: String = filepath?.name ?: appStrings.newFilename,
    var pdfName: String = filepath?.name?.replace(".md", ".pdf") ?: appStrings.newFilename,
    val userPosition: Int = 0,

    val shouldShowFileDropdownMenu: Boolean = false,
    val shouldShowAboutDialog: Boolean = false,

    val isSelectingFile: Boolean = false,
    val isSelectingFolder: Boolean = false,
    val isSettingFileName: Boolean = false,

    val isSettingFileNameForPdf: Boolean = false,
    val isSelectingFolderForPdf: Boolean = false,

    val shouldShowCorrectFileSavedResult: Boolean = false,
    val shouldShowErrorFileSavedResult: Boolean = false,

    val isDataUpdated: Boolean = true
)
