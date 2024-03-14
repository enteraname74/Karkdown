package model.markdownelement

import model.listIndicator

/**
 * Represent an unordered list.
 */
class UnorderedList(rowData: String, innerData: MarkdownElement): MarkdownElement(rowData = rowData) {
    override val viewData: MarkdownElement = innerData
    val listIndicator: String = rowData.listIndicator()

    override fun toString(): String {
        return "UnorderedList(\n\trowData = $rowData\n\tinnerData = $viewData\n)"
    }
}