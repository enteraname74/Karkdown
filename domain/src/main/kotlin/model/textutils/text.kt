package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBold(): Boolean {
    val regex = Regex("\\*\\*.*\\*\\*")
    return regex.matches(this)
}

/**
 * Check if a string is italic.
 */
fun String.isItalic(): Boolean {
    val regex = Regex("_.*_")
    return regex.matches(this)
}