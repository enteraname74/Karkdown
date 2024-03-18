package model.textutils

/**
 * Check if a string is a code.
 */
fun String.isCode(): Boolean {
    val regex = Regex("""(`{1,3})[^`]+\1""")
    return regex.matches(this)
}

/**
 * Retrieve the content of a code section to show to a user.
 */
fun String.codeContent(): String {
    val regex = Regex("""(`{1,3})([^`]+)\1""")
    return regex.find(this)?.destructured?.toList()?.getOrNull(1) ?: this
}