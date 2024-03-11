package model

import java.io.File

/**
 * Implementation of the FileData interface using a list of strings (list of sentences) to represent a file content.
 */
class FileContentImpl: FileContent{
    override var data: ArrayList<String> = ArrayList()

    override fun getContent(path: String) {
        data = try {
            File(path).readLines() as ArrayList<String>
        } catch (_: Exception) {
            ArrayList()
        }
    }

    override fun addLine(line: String) { data.add(line) }

    override fun insertLineAt(line: String, pos: Int) {
        if (pos < 0 || pos >= data.size) return

        data.add(pos, line)
    }

    override fun setLineAt(line: String, pos: Int) {
        if (pos < 0 || pos >= data.size) return

        data[pos] = line
    }

    override fun getList(): List<String> = data

    override fun getLineAt(pos: Int): String = if (pos < 0 || pos >= data.size) "" else data[pos]
}