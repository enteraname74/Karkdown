package model.textutils

/**
 * Check if a string is italic.
 */
fun String.isItalic(): Boolean {
    val regex = Regex("(\\*.+\\*)")
    return regex.matches(this)
}

/**
 * Retrieve the content of an italic section to show to a user.
 */
fun String.italicContent(): String {
    val regex = Regex("""\*(.*)\*""")
    return regex.find(this)?.destructured?.toList()?.getOrNull(0) ?: this
}