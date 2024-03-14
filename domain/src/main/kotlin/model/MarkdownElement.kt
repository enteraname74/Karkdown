package model

/**
 * Represent a markdown element.
 */
sealed class MarkdownElement(val rowData: String) {
    /**
     * The data to use when showing it to a user.
     */
    abstract val viewData: Any
}

/**
 * Represent a header with its level
 */
class Header(rowData: String) : MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData.headerContent()
    val headerLevel = rowData.headerLevel()

    override fun toString(): String {
        return "Header(rowData = $rowData)"
    }
}

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

/**
 * Represent a simple text.
 */
class SimpleText(rowData: String) : MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData

    override fun toString(): String {
        return "SimpleText(rowData = $rowData)"
    }
}