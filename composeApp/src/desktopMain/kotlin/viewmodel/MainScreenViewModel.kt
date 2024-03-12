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
import kotlin.math.min

class MainScreenViewModel(
    val fileManager: FileManager,
) {
    private val _state = MutableStateFlow(
        MainScreenState(
            fileContent = fileManager.content,
        )
    )
    var currentText by mutableStateOf("")
    val state = _state.asStateFlow()

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OpenFile -> openFile(event.file)
            is MainScreenEvent.SetCurrentText -> updateEditedText(event.text)
            is MainScreenEvent.CreateNewLine -> createNewLine(nextPos = event.nextPos)
            is MainScreenEvent.SetFocusedLine -> setFocusedLine(pos = event.pos)
            MainScreenEvent.GoDown -> setFocusedLine(pos = abs(min(fileManager.userPosition + 1, fileManager.size - 1)))
            MainScreenEvent.GoUp -> setFocusedLine(pos = abs(fileManager.userPosition - 1))
        }
    }

    /**
     * Define which line should be focused.
     */
    private fun setFocusedLine(pos: Int) {
        println("VM - We will focus the line at pos: $pos")

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
    }

}