package model

import model.markdownelement.*

/**
 * Format a file content to respect the markdown properties and to keep only the interesting information for the user
 * when editing the file.
 */
class FileFormatter {

    /**
     * Check if we should add a dummy space between two markdown elements.
     * We only add a dummy space if there is two consecutive simple text.
     */
    private fun addDummySpaceIfNecessary(pos: Int, rowData: ArrayList<String>, elements: List<MarkdownElement>) {
        if ((isSimpleText(pos, elements) || isList(pos, elements)) && isSimpleText(pos+1, elements)) {
            rowData.add("")
        }
    }

    /**
     * Check if we should add a dummy blockquote between two markdown elements.
     * We only add a dummy space if there is two consecutive blockquotes with the same level.
     */
    private fun addDummyBlockquoteIsNecessary(pos: Int, rowData: ArrayList<String>, elements: List<MarkdownElement>) {
        if (isBlockquote(pos, elements) && isBlockquote(pos+1, elements)) {
            val b1 = elements[pos] as Blockquote
            val b2 = elements[pos+1] as Blockquote

            if (b1.level == b2.level) {
                rowData.add(b1.quotes)
            }
        }
    }

    /**
     * Check if a position in a list of markdown elements is a simple text.
     */
    private fun isList(pos: Int, elements: List<MarkdownElement>): Boolean {
        if (pos >= elements.size || pos < 0) return false

        val element = elements[pos]

        return element is UnorderedList || element is OrderedList
    }

    /**
     * Check if a position in a list of markdown elements is a simple text.
     */
    private fun isSimpleText(pos: Int, elements: List<MarkdownElement>): Boolean {
        if (pos >= elements.size || pos < 0) return false

        val element = elements[pos]

        return element is SimpleText
    }

    /**
     * Check if a position in a list of markdown elements is a simple text.
     */
    private fun isBlockquote(pos: Int, elements: List<MarkdownElement>): Boolean {
        if (pos >= elements.size || pos < 0) return false

        val element = elements[pos]

        return element is Blockquote
    }


    /**
     * Build a row data depending on a given list of markdown elements.
     * The final data is formatted to better suit the markdown conventions.
     */
    fun buildFinalRowData(elements: List<MarkdownElement>): List<String> {
        val rowData = ArrayList<String>()

        elements.forEachIndexed { index, markdownElement ->
            rowData.add(markdownElement.rowData)
            addDummySpaceIfNecessary(
                pos = index,
                rowData = rowData,
                elements = elements
            )
            addDummyBlockquoteIsNecessary(
                pos = index,
                rowData = rowData,
                elements = elements
            )
        }

        println(rowData)

        return rowData
    }
}