package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBold(): Boolean {
    val regex = Regex("[^*_]*(\\*{2}[^*]+\\*{2}|_{2}[^_]+_{2})[^*_]*")
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
 * Retrieve the content of a bold section to show to a user.
 */
fun String.boldContent(): String {
    return this.replace("\\*{2}|_{2}".toRegex(), "")
}