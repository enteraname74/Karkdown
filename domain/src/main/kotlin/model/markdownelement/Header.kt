package model.markdownelement

import model.textutils.headerContent
import model.textutils.headerLevel
import model.textutils.withoutMarkdown


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