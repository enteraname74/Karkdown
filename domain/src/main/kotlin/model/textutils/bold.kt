package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBold(): Boolean {
    val regex = Regex("(\\*{2}.+\\*{2})")
    return regex.matches(this)
}

/**
 * Retrieve the content of a bold section to show to a user.
 */
fun String.boldContent(): String {
    val regex = Regex("""\*{2}(.*)\*{2}""")
    return regex.find(this)?.destructured?.toList()?.getOrNull(0) ?: this
}