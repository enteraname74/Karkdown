package model

/**
 * Gives methods to access and modify file content
 */
interface FileContent {
    var data: ArrayList<String>

    /**
     * Retrieve file content from its path.
     * @param path the path of the file to open.
     */
    fun getContent(path: String)

    /**
     * Add a line at the end of the file.
     * @param line the line to add.
     *
     */
    fun addLine(line: String)

    /**
     * Insert a line at a given pos in the file.
     * @param line the line to insert.
     * @param pos the position where the line should be inserted.
     *
     * The function does nothing if the position is not in the bound of the content.
     */
    fun insertLineAt(line: String, pos: Int)

    /**
     * Replace the previous line at a given pos by a new line.
     * @param line the line to set.
     * @param pos the position where the line should replace the previous one.
     *
     * The function does nothing if the position is not in the bound of the content.
     */
    fun setLineAt(line: String, pos: Int)

    /**
     * Retrieve the line at a given position.
     * @param pos the position where to retrieve the line.
     *
     * Returns the line or an empty string if the pos is incorrect.
     */
    fun getLineAt(pos: Int): String

    /**
     * Retrieve a list of each line.
     */
    fun getList(): List<String>
}