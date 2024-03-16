package model.markdownelement

import model.textutils.withoutMarkdown

/**
 * Represent a simple text.
 */
class SimpleText(rowData: String) : MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData.withoutMarkdown()

    override fun toString(): String {
        return "SimpleText(rowData = $rowData)"
    }
}