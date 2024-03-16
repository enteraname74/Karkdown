package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBold(): Boolean {
    val regex = Regex("[^\\*_]*(\\*{2}[^\\*]+\\*{2}|_{2}[^_]+_{2})[^\\*_]*")
    return regex.matches(this)
}

/**
 * Check if a string is italic.
 */
fun String.isItalic(): Boolean {
    val regex = Regex("[^\\*_]*(\\*[^\\*]+\\*|_[^_]+_)[^\\*_]*")
    return regex.matches(this)
}

/**
 * Check if a string is bold.
 */
fun String.isBoldAndItalic(): Boolean {
    val regex = Regex("[^\\*_]*(\\*{3}[^\\*]+\\*{3}|_{3}[^_]+_{3})[^\\*_]*")
    return regex.matches(this)
}


/**
 * Check if the bold text is with * characters.
 */
fun String.isStarBold(): Boolean {
    val regex = Regex(".*(\\*{2}.+\\*{2}).*")
    return regex.matches(this)
}

/**
 * Check if the bold text is with * characters.
 */
fun String.isStarItalic(): Boolean {
    val regex = Regex(".*(\\*.+\\*).*")
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