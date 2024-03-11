package model

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name

class FileManager(
    private val fileContent: FileContent
) {
    var filepath: Path = Path("")
    val filename: String
        get() = filepath.name

    val fileData = fileContent.data

    /**
     * Open a file.
     */
    fun openFile(path: String) {
        try {
            filepath = Path(path)
            fileContent.getContent(path)
        } catch (_: Exception) {}
    }

    /**
     * Add a line at a given position.
     * If the position is -1, adds at the end of the file.
     */
    fun addLine(text: String, pos: Int) {
        if (pos == -1) fileContent.addLine(text)
        else fileContent.insertLineAt(text, pos)
    }

    /**
     * Retrieve the line at a given position.
     * Returns an empty string if the position is incorrect.
     */
    fun getLineAt(pos: Int): String = fileContent.getLineAt(pos)
}