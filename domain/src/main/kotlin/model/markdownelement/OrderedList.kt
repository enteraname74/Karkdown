package model.markdownelement

import model.orderedListIndicator

/**
 * Represent an unordered list.
 */
class OrderedList(rowData: String, innerData: MarkdownElement): MarkdownElement(rowData = rowData) {
    override val viewData: MarkdownElement = innerData
    val currentIndicator: Int = rowData.orderedListIndicator()

    override fun toString(): String {
        return "OrderedList(\n\trowData = $rowData\n\tinnerData = $viewData\n)"
    }
}