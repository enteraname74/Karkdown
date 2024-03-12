package model.markdown

import model.headerLevel
import model.toHeader

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
    override val viewData: String = rowData.toHeader()
    val headerLevel = rowData.headerLevel()

    override fun toString(): String {
        return "Header(rowData = $rowData)"
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

/**
 * Represent the text the user is currently editing.
 */
class EditableText(currentText: String) : MarkdownElement(rowData = currentText) {
    override val viewData: String = rowData

    override fun toString(): String {
        return "EditableText(currentText = $rowData)"
    }
}