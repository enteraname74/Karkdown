import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.github.enteraname74.karkdowncore.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import state.MarkdownState
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MarkdownManager {
    private var fileManager: FileManager = FileManager()

    private val _state: MutableStateFlow<MarkdownState> = MutableStateFlow(
        MarkdownState()
    )
    val state: StateFlow<MarkdownState> = _state.asStateFlow()
    var currentText by mutableStateOf("")
        private set

    private fun updateState() {
        _state.value = MarkdownState(
            filePos = fileManager.userPosition,
            fileContent = fileManager.content,
        )
        currentText = fileManager.getLineAt(pos = fileManager.userPosition)
    }

    init {
        fileManager.createNewLine(
            pos = 0,
            initialText = "",
        )
        updateState()
    }

    fun init(fileManager: FileManager) {
        this.fileManager = fileManager
        updateState()
    }

    fun init(filePath: String) {
        fileManager = FileManager().apply {
            fromFile(path = filePath)
            updateState()
        }
    }

    fun init(lines: List<String>) {
        fileManager = FileManager().apply {
            fromLines(lines = lines)
            updateState()
        }
    }

    fun deleteLine(pos: Int) {
        fileManager.deleteLine(pos = pos)
        updateState()
    }

    /**
     * Set the user to the previous line in the text.
     */
    fun goUp() {
        setFocusedLine(
            pos = max(
                fileManager.userPosition - 1,
                0,
            )
        )
    }

    /**
     * Set the user to the next line in the text.
     */
    fun goDown() {
        setFocusedLine(
            pos = abs(
                min(
                    fileManager.userPosition + 1,
                    fileManager.size - 1
                )
            )
        )
    }

    fun getFilePath(): Path? =
        fileManager.filepath

    fun setFocusedLine(pos: Int) {
        fileManager.setFocusedLine(pos = pos)
        updateState()
    }

    /**
     * Define a line in the content at a given pos.=
     * @param nextPos the position where to put the text.
     * @param initialText the initial text to add in the new line.
     */
    fun createNewLine(nextPos: Int, initialText: String = "") {
        fileManager.createNewLine(
            pos = nextPos,
            initialText = initialText,
        )
        updateState()
    }

    /**
     * Set a line at the given pos.
     */
    fun updateLineAt(pos: Int, line: String) {
        fileManager.updateLineAt(
            pos = pos,
            line = line,
        )
        updateState()
    }

    fun getRowData(): List<String> =
        fileManager.getRowData()
}

@Composable
fun rememberMarkdownManager(): MarkdownManager {
    val manager = rememberSaveable(saver = MarkdownSaver) {
        mutableStateOf(MarkdownManager()).value
    }

    return manager
}