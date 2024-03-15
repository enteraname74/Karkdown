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

/**
 * Retrieve the content of a bold section to show to a user.
 */
fun String.boldContent(): String {
    val regex = Regex("\\w+")
    return regex.find(this)?.value ?: ""
}

/**
 * Retrieve the content of an italic section to show to a user.
 */
fun String.italicContent(): String {
    val regex = Regex("\\w+")
    return regex.find(this)?.value ?: ""
}