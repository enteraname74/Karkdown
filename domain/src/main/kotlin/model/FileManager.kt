package model

import model.markdownelement.MarkdownElement
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name

class FileManager {

    private val fileFormatter = FileFormatter()

    /**
     * The full filepath (path + filename) of a file.
     */
    var filepath: Path? = null

    private var lastSavedRowData: ArrayList<String> = ArrayList()
    private var rowData: ArrayList<String> = ArrayList()

    private var fileBuilder = FileBuilder()
    var content: ArrayList<MarkdownElement> = ArrayList()
    var userPosition: Int = 0
    val isDataUpdated: Boolean
        get() = rowData == lastSavedRowData

    init {
        // We initialize the initial file content with a text input:
        rowData.add("")
        updateMarkdownContent()
        lastSavedRowData = ArrayList(rowData)
    }

    val filename: String?
        get() = filepath?.name

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
        } catch (_: Exception) {
        }
    }

    /**
     * Attempt to do a quick save with the known file path.
     *
     * @return true if the file was saved, false if not.
     */
    fun saveFile(): Boolean {
        return try {
            filepath?.let { filepath ->
                val file = File(filepath.toString())

                file.printWriter().use { writer ->
                    val finalData = fileFormatter.buildFinalRowData(content)
                    finalData.forEach { line ->
                        writer.println(line)
                    }
                }
                lastSavedRowData = ArrayList(rowData)
                return true
            }
            false
        } catch (e: Exception) {
            println("ERROR WHEN QUICK SAVING: $e")
            false
        }
    }

    private fun getContent(path: String) {
        rowData = try {
            File(path).readLines() as ArrayList<String>
        } catch (_: Exception) {
            ArrayList()
        }
        content = fileFormatter.formatMarkdownElements(
            elements = fileBuilder.buildMarkdownFile(rowData)
        )

        // If the content is empty, we add a simple text for starting the editing of the file.
        if (content.isEmpty()) {
            createNewLine(
                pos = 0,
                initialText = ""
            )
        }

        rowData = content.toRowData()
        lastSavedRowData = ArrayList(rowData)
    }

    /**
     * Update the markdown content of a file.
     */
    private fun updateMarkdownContent() {
        content = fileBuilder.buildMarkdownFile(rowData)
    }

    /**
     * Set the focused line to work on.
     */
    fun setFocusedLine(pos: Int) {
        userPosition = pos
        updateMarkdownContent()
    }

    /**
     * Create a new line and place the user to it.
     * @param pos the position where to put the text.
     * @param initialText the initial text to add in the new line.
     */
    fun createNewLine(pos: Int, initialText: String) {
        if (pos >= size) rowData.add(initialText) else rowData.add(pos, initialText)
        userPosition = pos
        updateMarkdownContent()
    }

    /**
     * Delete a line at a given position.
     * If the line is the start of the file, does nothing.
     * if the user is on the given line, it will move him to the previous one.
     * If there is still elements on the line, it will be brought to the previous line.
     *
     * @param pos the position of the line to remove.
     */
    fun deleteLine(pos: Int) {
        if (pos == 0 || pos >= rowData.size) return

        // If there is still elements on the line, it will be brought to the previous line:
        val lineToDelete = rowData[pos]
        if (lineToDelete.isNotEmpty()) rowData[pos-1] = rowData[pos-1]+lineToDelete

        rowData.removeAt(pos)

        // We move the user
        if (userPosition == pos) {
            userPosition -= 1
            updateMarkdownContent()
        }
    }

    /**
     * Replace the previous line at a given pos by a new line.
     * @param line the line to set.
     * @param pos the position where the line should replace the previous one.
     *
     * Does nothing if the position is not correct
     */
    fun updateLineAt(line: String, pos: Int) {
        if (pos < 0 || pos >= rowData.size) {
            return
        } else rowData[pos] = line

        userPosition = pos
        updateMarkdownContent()
    }

    /**
     * Retrieve the line at a given position.
     * @param pos the position where to retrieve the line.
     *
     * Returns the line or an empty string if the pos is incorrect.
     */
    fun getLineAt(pos: Int): String = if (pos < 0 || pos >= rowData.size) "" else rowData[pos]

    /**
     * Retrieve the row data of a file from its markdown elements.
     */
    private fun List<MarkdownElement>.toRowData(): ArrayList<String> {
        return ArrayList(this.map { it.rowData })
    }
}