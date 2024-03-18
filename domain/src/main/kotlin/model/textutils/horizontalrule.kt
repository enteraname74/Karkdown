package model.textutils

/**
 * Check if a string is a horizontal rule.
 */
fun String.isHorizontalRule(): Boolean {
    return this == "***" || this == "---"
}