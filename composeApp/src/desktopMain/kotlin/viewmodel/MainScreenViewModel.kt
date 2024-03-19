package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.woojiahao.MarkdownDocument
import com.github.woojiahao.markdownConverter
import event.MainScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.FileManager
import state.MainScreenState
import strings.appStrings
import utils.FileHeader
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.io.path.pathString
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MainScreenViewModel {
    private val allFilesManager: ArrayList<FileManager> = arrayListOf(FileManager())
    private var filePos: Int = 0

    private val _state = MutableStateFlow(
        MainScreenState()
    )

    private val currentFileManager: FileManager
        get() = allFilesManager.getOrElse(filePos) { FileManager() }

    var currentText by mutableStateOf("")
    val state = _state.asStateFlow()

    init {
        setFocusedLine(pos = 0)
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.CreateNewFile -> createNewFile()
            is MainScreenEvent.SwitchCurrentFile -> switchShownFile(newFilePos = event.filePos)
            is MainScreenEvent.CloseFile -> closeFile(pos = event.filePos)

            is MainScreenEvent.OpenFile -> openFile(filepath = event.filepath)
            is MainScreenEvent.SetCurrentText -> updateEditedText(text = event.text, pos = event.pos)
            is MainScreenEvent.CreateNewLine -> createNewLine(nextPos = event.nextPos, initialText = event.initialText)
            is MainScreenEvent.SetFocusedLine -> setFocusedLine(pos = event.pos)
            is MainScreenEvent.DeleteLine -> deleteLine(pos = event.pos)

            is MainScreenEvent.SetFileDropdownMenuVisibility -> setFileDropdownMenuVisibility(show = event.show)

            MainScreenEvent.QuickSaveCurrentFile -> quickSave()
            is MainScreenEvent.SaveAsCurrentFile -> saveAs(
                filepath = event.path,
                filename = event.filename
            )

            is MainScreenEvent.ShouldShowCorrectFileSaving -> showCorrectSaving(show = event.show)
            is MainScreenEvent.ShouldShowFileSavingError -> showSavingError(show = event.show)

            is MainScreenEvent.ShouldSelectFolder -> setFolderSelectionState(shouldSelectFolder = event.shouldSelectFolder)
            is MainScreenEvent.ShouldEnterFileName -> shouldSetFileName(shouldSetFileName = event.shouldSetFileName)
            is MainScreenEvent.ShouldSelectFile -> setFileSelectionState(shouldSelectFile = event.shouldSelectFile)
            is MainScreenEvent.SetCurrentFileName -> setCurrentFileName(name = event.name)
            is MainScreenEvent.SetPdfName -> setPdfName(name = event.name)

            is MainScreenEvent.ExportAsPdf -> exportAsPdf(filepath = event.path, filename = _state.value.pdfName)
            is MainScreenEvent.ShouldEnterFileNameForPdf -> shouldSetFileNameForPdf(shouldSetFileName = event.shouldSetFileName)
            is MainScreenEvent.ShouldSelectFolderForPdf -> shouldSelectFolderForPdf(shouldSelectFolder = event.shouldSelectFolder)

            MainScreenEvent.GoDown -> setFocusedLine(
                pos = abs(
                    min(
                        currentFileManager.userPosition + 1,
                        currentFileManager.size - 1
                    )
                )
            )

            MainScreenEvent.GoUp -> setFocusedLine(pos = max(currentFileManager.userPosition - 1, 0))
        }
    }

    /**
     * Close a file at a given index.
     * If the closed file is the one where the user is on,
     * it will bring the user to the front
     */
    private fun closeFile(pos: Int) {
        if (pos < 0 || pos > allFilesManager.lastIndex) return
        allFilesManager.removeAt(pos)

        if (pos == filePos) filePos = 0
        else if (pos < filePos) filePos -= 1
        updateCurrentFileInformation()
    }

    /**
     * Create a new file and switch view to it.
     */
    private fun createNewFile() {
        allFilesManager.add(FileManager())
        switchShownFile(allFilesManager.lastIndex)
    }

    /**
     * Change the visible file to show to the user.
     */
    private fun switchShownFile(newFilePos: Int) {
        if (newFilePos < 0 || newFilePos > allFilesManager.lastIndex) return

        filePos = newFilePos
        updateCurrentFileInformation()
    }

    /**
     * Show or hide the file dropdown menu.
     */
    private fun setFileDropdownMenuVisibility(show: Boolean) {
        _state.update {
            it.copy(
                shouldShowFileDropdownMenu = show
            )
        }
    }

    /**
     * Define the pdf name to use when exporting the file as pdf.
     */
    private fun setPdfName(name: String) {
        _state.update {
            it.copy(
                pdfName = name
            )
        }
    }

    /**
     * Define if we should select a folder for the pdf
     */
    private fun shouldSelectFolderForPdf(shouldSelectFolder: Boolean) {
        _state.update {
            it.copy(
                isSelectingFolderForPdf = shouldSelectFolder
            )
        }
    }

    /**
     * Define if we should set a filename for the pdf.
     */
    private fun shouldSetFileNameForPdf(shouldSetFileName: Boolean) {
        _state.update {
            it.copy(
                isSettingFileNameForPdf = shouldSetFileName
            )
        }
    }

    /**
     * Save the current state of the file to a pdf with the given information.
     * If nothing was saved before, a md file will be saved.
     */
    private fun exportAsPdf(filepath: String, filename: String) {
        saveAs(filepath, filename.replace(".pdf", ""))

        currentFileManager.filepath?.let { path ->
            val converter = markdownConverter {
                document(MarkdownDocument(path.pathString))

                val finalFilename = if (filename.split(".").lastOrNull() != "pdf") "$filename.pdf" else filename
                val finalPath = Path(
                    base = path.parent.pathString,
                    finalFilename
                ).pathString

                targetLocation(finalPath)
            }
            converter.convert()
        }
    }

    /**
     * Define if we should show a saving error.
     */
    private fun showSavingError(show: Boolean) {
        _state.update {
            it.copy(
                shouldShowErrorFileSavedResult = show
            )
        }
    }

    /**
     * Define if we should show if a saving was correct.
     */
    private fun showCorrectSaving(show: Boolean) {
        _state.update {
            it.copy(
                shouldShowCorrectFileSavedResult = show
            )
        }
    }

    /**
     * Set the current filename used by the view and later by the file manager.
     */
    private fun setCurrentFileName(name: String) {
        _state.update {
            it.copy(
                filename = name
            )
        }
    }

    /**
     * Save the current file in a given path, with a given filename.
     */
    private fun saveAs(filepath: String, filename: String) {
        // We need to check if the filename has the correct format:
        val finalFilename = if (filename.split(".").lastOrNull() != "md") "$filename.md" else filename

        currentFileManager.filepath = Path(
            base = filepath,
            finalFilename
        )
        quickSave()
    }

    /**
     * Tries to quick save a file.
     * If there is missing information (filepath),
     * then it will use the save as method instead
     */
    private fun quickSave() {

        // In this case, we need to do the steps of a save as operation (filename -> destination folder -> save as)
        if (currentFileManager.filepath == null) {
            shouldSetFileName(shouldSetFileName = true)
        } else {
            val hasBeenSaved = currentFileManager.saveFile()
            if (hasBeenSaved) showCorrectSaving(show = true) else showSavingError(show = false)
            _state.update {
                it.copy(
                    filename = currentFileManager.filename,
                    filepath = currentFileManager.filepath,
                    isDataUpdated = currentFileManager.isDataUpdated
                )
            }
        }
    }

    /**
     * Manage the selection of a folder.
     */
    private fun setFolderSelectionState(shouldSelectFolder: Boolean) {
        _state.update {
            it.copy(
                isSelectingFolder = shouldSelectFolder
            )
        }
    }

    /**
     * Manage the selection of a file.
     */
    private fun setFileSelectionState(shouldSelectFile: Boolean) {
        _state.update {
            it.copy(
                isSelectingFile = shouldSelectFile
            )
        }
    }

    /**
     * Define if we should enter a filename for the markdown file.
     */
    private fun shouldSetFileName(shouldSetFileName: Boolean) {
        _state.update {
            it.copy(
                isSettingFileName = shouldSetFileName
            )
        }
    }

    /**
     * Delete a line from the file.
     * If there is still elements on the line, it will be brought to the previous line.
     * If the given position is the beginning of the file, does nothing.
     */
    private fun deleteLine(pos: Int) {
        currentFileManager.deleteLine(pos = pos)
        updateCurrentFileInformation()
    }

    /**
     * Define which line should be focused.
     */
    private fun setFocusedLine(pos: Int) {
        currentFileManager.setFocusedLine(pos)
        updateCurrentFileInformation(currentTextToShow = currentFileManager.getLineAt(pos))
    }

    /**
     * Define a line in the content at a given pos.=
     * @param nextPos the position where to put the text.
     * @param initialText the initial text to add in the new line.
     */
    private fun createNewLine(nextPos: Int, initialText: String) {
        currentFileManager.createNewLine(nextPos, initialText)
        updateCurrentFileInformation(currentTextToShow = "")
    }

    /**
     * Update the current edited text.
     */
    private fun updateEditedText(text: String, pos: Int) {
        currentFileManager.updateLineAt(text, pos)
        currentText = text
        updateCurrentFileInformation()
    }


    /**
     * Open a file.
     * It will add a new FileManager and place the user on it.
     */
    private fun openFile(filepath: String) {
        allFilesManager.add(FileManager())
        filePos = allFilesManager.lastIndex

        currentFileManager.openFile(filepath)
        currentFileManager.userPosition = 0
        updateCurrentFileInformation()
    }

    /**
     * Update information about the current file and the user position on it.
     */
    private fun updateCurrentFileInformation(currentTextToShow: String = currentFileManager.getLineAt(currentFileManager.userPosition)) {
        _state.update {
            it.copy(
                filesHeaders = allFilesManager.mapIndexed { pos, manager ->
                    manager.toFileHeader(
                        fileManagerPos = pos
                    )
                },
                fileContent = currentFileManager.content,
                filePos = filePos,
                userPosition = currentFileManager.userPosition,
                filepath = currentFileManager.filepath,
                filename = currentFileManager.filepath?.name ?: appStrings.newFilename,
                isDataUpdated = currentFileManager.isDataUpdated
            )
        }
        currentText = currentTextToShow
    }

    /**
     * Retrieve useful information from a file manager to be shown in a header.
     */
    private fun FileManager.toFileHeader(fileManagerPos: Int): FileHeader = FileHeader(
        isDataUpdated = this.isDataUpdated,
        isSelected = fileManagerPos == filePos,
        fileName = this.filename
    )
}