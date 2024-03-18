package model.textutils

/**
 * Check if a string is italic.
 */
fun String.isItalic(): Boolean {
    val regex = Regex("[^*_]*(\\*[^*]+\\*|_[^_]+_)[^*_]*")
    return regex.matches(this)
}

/**
 * Check if the italic text is with * characters.
 */
fun String.isStarItalic(): Boolean {
    val regex = Regex(".*(\\*.+\\*).*")
    return regex.matches(this)
}

/**
 * Retrieve the content of an italic section to show to a user.
 */
fun String.italicContent(): String {
    return this.replace("[*_]".toRegex(), "")
}