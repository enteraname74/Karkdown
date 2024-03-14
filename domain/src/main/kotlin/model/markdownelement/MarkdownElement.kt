package model.markdownelement

/**
 * Represent a markdown element.
 */
sealed class MarkdownElement(val rowData: String) {
    /**
     * The data to use when showing it to a user.
     */
    abstract val viewData: Any
}