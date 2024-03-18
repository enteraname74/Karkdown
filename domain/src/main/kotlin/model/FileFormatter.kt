package model

import model.markdownelement.*
import model.textutils.blockquoteContent
import model.textutils.blockquoteInnerText

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

        val isTwoSpecificMarkdownElements = (isSimpleText(pos, elements) || isList(pos, elements)) && isSimpleText(pos + 1, elements)

        if (isTwoSpecificMarkdownElements) {
            val isTwoNotBlankMarkdownElements = elements[pos].rowData.isNotBlank() && elements[pos+1].rowData.isNotBlank()
            if (isTwoNotBlankMarkdownElements) rowData.add("")
        }
    }

    /**
     * Check if we should add a dummy blockquote between two markdown elements.
     * We only add a dummy space if there is two consecutive blockquotes with the same level.
     */
    private fun addDummyBlockquoteIsNecessary(pos: Int, rowData: ArrayList<String>, elements: List<MarkdownElement>) {
        if (isBlockquote(pos, elements) && isBlockquote(pos + 1, elements)) {
            val b1 = elements[pos] as Blockquote
            val b2 = elements[pos + 1] as Blockquote

            if (b1.level >= b2.level) {
                rowData.add(b2.quotes)
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

        return rowData
    }

    /**
     * Check if an element at a given position is a dummy space.
     */
    private fun isDummySpace(pos: Int, elements: List<MarkdownElement>): Boolean {
        if (!isSimpleText(pos, elements)) return false

        val isPreviousElementInNeedOfSeparation = (isSimpleText(pos - 1, elements) || isList(pos - 1, elements)) && elements.getOrNull(pos-1)?.rowData?.isNotBlank() == true
        val isTextBlank = elements[pos].rowData.isBlank()
        val isNextElementInNeedOfSeparation = isSimpleText(pos + 1, elements) && elements.getOrNull(pos+1)?.rowData?.isNotBlank() == true

        println("text blank ? $isTextBlank")
        println("Prev ? $isPreviousElementInNeedOfSeparation")
        println("Next ? $isNextElementInNeedOfSeparation")

        return isPreviousElementInNeedOfSeparation && isNextElementInNeedOfSeparation && isTextBlank
    }

    /**
     * Check if an element at a given position is a dummy blockquote.
     */
    private fun isDummyBlockquote(pos: Int, elements: List<MarkdownElement>): Boolean {
        if (!isBlockquote(pos, elements)) return false

        if (isBlockquote(pos - 1, elements) && isBlockquote(pos + 1, elements)) {
            val b0 = elements[pos] as Blockquote

            println("Content of blockquote: ${b0.rowData.blockquoteInnerText()}")
            println("Is blank?: ${b0.rowData.blockquoteInnerText().isBlank()}")
            println("Is empty?: ${b0.rowData.blockquoteInnerText().isEmpty()}")

            return b0.rowData.blockquoteInnerText().isBlank()
        }
        return false
    }

    /**
     * Format a given list of markdown element for a user.
     */
    fun formatMarkdownElements(elements: List<MarkdownElement>): ArrayList<MarkdownElement> {
        val formattedElements = ArrayList<MarkdownElement>()

        elements.forEachIndexed { index, markdownElement ->
            println("----")
            println("Analyze for : $markdownElement")
            println(!isDummySpace(index, elements))
            println(!isDummyBlockquote(index, elements))
            println("----")
            if (!isDummySpace(index, elements) && !isDummyBlockquote(index, elements)) {
                formattedElements.add(markdownElement)
            }
        }

        return formattedElements
    }
}