package model.textutils

/**
 * Check if a string is strikethrough
 */
fun String.isStrikethrough(): Boolean {
    val regex = Regex("~{2}[^~]+~{2}")
    return regex.matches(this)
}

/**
 * Retrieve the content of an italic section to show to a user.
 */
fun String.strikethroughContent(): String {
    val regex = Regex("""~{2}([^~]+)~{2}""")
    return regex.find(this)?.destructured?.toList()?.getOrNull(0) ?: this
}