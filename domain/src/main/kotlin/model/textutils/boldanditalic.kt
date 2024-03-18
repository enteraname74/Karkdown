package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBoldAndItalic(): Boolean {
    val regex = Regex("[^*_]*(\\*{3}[^*]+\\*{3}|_{3}[^_]+_{3})[^*_]*")
    return regex.matches(this)
}

/**
 * Check if the bold text is with * characters.
 */
fun String.isStarBoldAndItalic(): Boolean {
    val regex = Regex(".*(\\*{3}.+\\*{3}).*")
    return regex.matches(this)
}

/**
 * Retrieve the content of a bold and italic section to show to a user.
 */
fun String.boldAndItalicContent(): String {
    return this.replace("\\*{3}|_{3}".toRegex(), "")
}
