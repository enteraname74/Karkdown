package model.markdownelement

/**
 * Represent a simple text.
 */
class SimpleText(rowData: String) : MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData

    override fun toString(): String {
        return "SimpleText(rowData = $rowData)"
    }
}