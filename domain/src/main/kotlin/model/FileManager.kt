package model

import model.markdown.EditableText
import model.markdown.MarkdownElement
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name
import kotlin.math.max

class FileManager {
    private var filepath: Path = Path("")

    private var rowData: ArrayList<String> = ArrayList()
    private var lineAnalyzer = LineAnalyzer()
    var content: ArrayList<MarkdownElement> = ArrayList()
    var userPosition: Int = 0

    init {
        // We initialize the initial file content with a text input:
        println("FileContentManager - INIT")
        updateMarkdownContent()
        rowData.add("")
        setEditableLine(0)
    }

    val filename: String
        get() = filepath.name

    /**
     * Size of the file content.
     */
    val size: Int
        get() = content.size


    /**
     * Open a file.
     */
    fun openFile(path: String) {
        try {
            filepath = Path(path)
            getContent(path)
        } catch (_: Exception) {}
    }

    /**
     * Tries to set the user position on the current editable text.
     *
     * If no editable text is found, it will set the user position at 0.
     */
    private fun setUserPositionToCurrentEditableText() {
        userPosition = max(content.indexOfFirst { it is EditableText }, 0)
    }

    fun getContent(path: String) {
        rowData = try {
            File(path).readLines() as ArrayList<String>
        } catch (_: Exception) {
            ArrayList()
        }
        updateMarkdownContent()
    }

    /**
     * Update the markdown content of a file.
     */
    private fun updateMarkdownContent() {
        content = lineAnalyzer.buildMarkdownFile(rowData)
        setEditableLine(userPosition)
        println("FileContentManager - content: $content")
    }

    /**
     * Set the focused line to work on.
     */
    fun setFocusedLine(pos: Int) {
        updateMarkdownContent()
        setEditableLine(pos)
    }

    /**
     * Set which line is the editable one by the user.
     * If the pos is not in the bound of the content, it will add an editable line at the end of the file.
     *
     * If we want to set an editable line at the end of the file but there is already one, does nothing.
     */
    private fun setEditableLine(pos: Int) {
        println("FileContentManager - Will set editable line at pos: $pos")
        val shouldAddLineAtEnd = pos < 0 || pos >= size

        if (shouldAddLineAtEnd && content.isEmpty()) content.add(EditableText(currentText = ""))
        else if (shouldAddLineAtEnd && content.last() is EditableText) return
        else if (shouldAddLineAtEnd) content.add(EditableText(currentText = ""))
        else content.set(
            index = pos,
            element = EditableText(
                currentText = content[pos].rowData
            )
        )
        setUserPositionToCurrentEditableText()
    }

    /**
     * Create a new line and place the user to it.
     */
    fun createNewLine(nextPos: Int) {
        if (nextPos >= size) rowData.add("") else rowData.add(nextPos, "")
        userPosition = nextPos
        updateMarkdownContent()
    }

    /**
     * Replace the previous line at a given pos by a new line.
     * @param line the line to set.
     * @param pos the position where the line should replace the previous one.
     *
     * Does nothing if the position is not correct
     */
    fun updateLineAt(line: String, pos: Int) {
        println("FileContentManager - A line will be updated at the pos: $pos, ${rowData.size}")
        if (pos < 0 || pos >= rowData.size) {
            return
        }
        else rowData[pos] = line

        updateMarkdownContent()
    }

    /**
     * Retrieve the line at a given position.
     * @param pos the position where to retrieve the line.
     *
     * Returns the line or an empty string if the pos is incorrect.
     */
    fun getLineAt(pos: Int): String = if (pos < 0 || pos >= rowData.size) "" else rowData[pos]
}