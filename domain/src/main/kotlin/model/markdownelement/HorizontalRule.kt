package model.markdownelement

/**
 * Represent a horizontal bar to use to separate elements.
 */
class HorizontalRule(rowData: String): MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData
}