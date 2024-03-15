package model.markdownelement

import model.unorderedListIndicator

/**
 * Represent an unordered list.
 */
class OrderedList(rowData: String, innerData: MarkdownElement): MarkdownElement(rowData = rowData) {
    override val viewData: MarkdownElement = innerData
    val currentIndicator: String = rowData.unorderedListIndicator()

    override fun toString(): String {
        return "OrderedList(\n\trowData = $rowData\n\tinnerData = $viewData\n)"
    }
}