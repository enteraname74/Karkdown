package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import event.MainScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.FileManager
import model.LineAnalyzer
import state.MainScreenState

class MainScreenViewModel(
    val fileManager: FileManager,
    val lineAnalyzer: LineAnalyzer
) {
    private val _state = MutableStateFlow(
        MainScreenState()
    )
    var currentText by mutableStateOf("")
    val state = _state.asStateFlow()

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OpenFile -> openFile(event.file)
            is MainScreenEvent.SetCurrentText -> defineCurrentText(event.text)
            is MainScreenEvent.AddNewLine -> {
                addLine(
                    text = event.text,
                    pos = event.pos
                )
            }
            is MainScreenEvent.SetFocusedLine -> setFocusedLine(pos = event.pos)
        }
    }

    private fun setFocusedLine(pos: Int) {
        _state.update {
            it.copy(
                currentLine = pos
            )
        }
        currentText = fileManager.getLineAt(pos)
    }

    private fun addLine(text: String, pos: Int) {
        fileManager.addLine(text, pos)
        _state.update {
            it.copy(
                fileContent = fileManager.fileData
            )
        }
        currentText = ""
    }

    private fun defineCurrentText(text: String) {
        currentText = text
    }

    private fun openFile(filepath: String) {
        fileManager.openFile(filepath)
    }
}