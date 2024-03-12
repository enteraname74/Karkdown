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
            is MainScreenEvent.ShouldSelectFile -> setFileSelectionState(shouldSelectFile = event.shouldSelectFile)
            MainScreenEvent.GoDown -> setFocusedLine(pos = abs(min(fileManager.userPosition + 1, fileManager.size - 1)))
            MainScreenEvent.GoUp -> setFocusedLine(pos = max(fileManager.userPosition - 1, 0))
            MainScreenEvent.QuickSaveCurrentFile -> TODO()
            MainScreenEvent.SaveAsCurrentFile -> TODO()
        }
    }

    /**
     * Manage the selection of a folder.
     */
    private fun setFileSelectionState(shouldSelectFile: Boolean) {
        println("THERE: $shouldSelectFile")
        _state.update {
            it.copy(
                isSelectingFile = shouldSelectFile
            )
        }
    }

    /**
     * Delete a line from the file.
     * If the given position is the beginning of the file, does nothing.
     */
    private fun deleteLine(pos: Int) {
        fileManager.deleteLine(pos = pos)

        _state.update {
            it.copy(
                fileContent = fileManager.content,
                userPosition = fileManager.userPosition
            )
        }
        currentText = fileManager.getLineAt(fileManager.userPosition)
    }

    /**
     * Define which line should be focused.
     */
    private fun setFocusedLine(pos: Int) {
        fileManager.setFocusedLine(pos)

        _state.update {
            it.copy(
                fileContent = fileManager.content,
                userPosition = fileManager.userPosition
            )
        }
        currentText = fileManager.getLineAt(pos)
    }

    /**
     * Define a line in the content at a given pos.=
     * @param nextPos the position where to put the text.
     *
     */
    private fun createNewLine(nextPos: Int) {
        fileManager.createNewLine(nextPos)

        _state.update {
            it.copy(
                fileContent = fileManager.content,
                userPosition = fileManager.userPosition
            )
        }
        currentText = ""
    }

    /**
     * Update the current edited text.
     */
    private fun updateEditedText(text: String) {
        fileManager.updateLineAt(text, fileManager.userPosition)
        currentText = text
    }

    private fun openFile(filepath: String) {
        fileManager.openFile(filepath)
        _state.update {
            it.copy(
                fileContent = fileManager.content,
                userPosition = fileManager.userPosition
            )
        }
        println("Content at line 0: ${fileManager.getLineAt(fileManager.userPosition)}")
        println(fileManager.content)
        currentText = fileManager.getLineAt(fileManager.userPosition)
    }

}