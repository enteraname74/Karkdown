package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBoldAndItalic(): Boolean {
    val regex = Regex("(\\*{3}.+\\*{3})")
    return regex.matches(this)
}

/**
 * Retrieve the content of a bold and italic section to show to a user.
 */
fun String.boldAndItalicContent(): String {
    val regex = Regex("""\*{3}(.*)\*{3}""")
    return regex.find(this)?.destructured?.toList()?.getOrNull(0) ?: this
}
