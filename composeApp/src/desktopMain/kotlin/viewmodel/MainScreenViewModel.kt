package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import event.MainScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.FileManager
import state.MainScreenState
import strings.appStrings
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MainScreenViewModel {
    private val fileManager: FileManager = FileManager()
    private val _state = MutableStateFlow(
        MainScreenState(
            fileContent = fileManager.content,
        )
    )

    var currentText by mutableStateOf("")
    val state = _state.asStateFlow()

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OpenFile -> openFile(filepath = event.filepath)
            is MainScreenEvent.SetCurrentText -> updateEditedText(event.text)
            is MainScreenEvent.CreateNewLine -> createNewLine(nextPos = event.nextPos)
            is MainScreenEvent.SetFocusedLine -> setFocusedLine(pos = event.pos)
            is MainScreenEvent.DeleteLine -> deleteLine(pos = event.pos)

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

            MainScreenEvent.GoDown -> setFocusedLine(pos = abs(min(fileManager.userPosition + 1, fileManager.size - 1)))
            MainScreenEvent.GoUp -> setFocusedLine(pos = max(fileManager.userPosition - 1, 0))

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
        val finalFilename = if (filename.split(".").last() != "md") "$filename.md" else filename

        fileManager.filepath = Path(
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
        if (fileManager.filepath == null) {
            shouldSetFileName(shouldSetFileName = true)
        } else {
            val hasBeenSaved = fileManager.saveFile()
            if (hasBeenSaved) showCorrectSaving(show = true) else showSavingError(show = false)
            _state.update {
                it.copy(
                    filename = fileManager.filename,
                    filepath = fileManager.filepath,
                    isDataUpdated = fileManager.isDataUpdated
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
     * Manage the selection of a file.
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
     * If the given position is the beginning of the file, does nothing.
     */
    private fun deleteLine(pos: Int) {
        fileManager.deleteLine(pos = pos)
        updateCurrentFileInformation()
    }

    /**
     * Define which line should be focused.
     */
    private fun setFocusedLine(pos: Int) {
        fileManager.setFocusedLine(pos)
        updateCurrentFileInformation(currentTextToShow = fileManager.getLineAt(pos))
    }

    /**
     * Define a line in the content at a given pos.=
     * @param nextPos the position where to put the text.
     *
     */
    private fun createNewLine(nextPos: Int) {
        fileManager.createNewLine(nextPos)
        updateCurrentFileInformation(currentTextToShow = "")
    }

    /**
     * Update the current edited text.
     */
    private fun updateEditedText(text: String) {
        fileManager.updateLineAt(text, fileManager.userPosition)
        currentText = text
        _state.update {
            it.copy(
                isDataUpdated = fileManager.isDataUpdated
            )
        }
    }

    private fun openFile(filepath: String) {
        fileManager.openFile(filepath)
        updateCurrentFileInformation()
    }

    /**
     * Update information about the current file and the user position on it.
     */
    private fun updateCurrentFileInformation(currentTextToShow: String = fileManager.getLineAt(fileManager.userPosition)) {
        _state.update {
            it.copy(
                fileContent = fileManager.content,
                userPosition = fileManager.userPosition,
                filepath = fileManager.filepath,
                filename = fileManager.filepath?.name ?: appStrings.newFilename,
                isDataUpdated = fileManager.isDataUpdated
            )
        }
        currentText = currentTextToShow
    }
}