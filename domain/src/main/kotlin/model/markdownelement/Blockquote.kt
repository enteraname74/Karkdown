package model.markdownelement

/**
 * Represent a blockquote.
 * The inner data is the markdown content inside the blockquote.
 */
class Blockquote(rowData: String, innerData: MarkdownElement): MarkdownElement(rowData = rowData) {
    override val viewData: MarkdownElement = innerData
    override fun toString(): String {
        return "Blockquote(\n\trowData = $rowData\n\tinnerData = $viewData\n)"
    }
}