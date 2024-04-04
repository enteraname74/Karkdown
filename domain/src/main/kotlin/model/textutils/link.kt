package model.textutils

/**
 * Check if the text is a link.
 */
fun String.isLink(): Boolean {
    val regex = Regex("""\[.*?]\(.*?\)""")
    return regex.matches(this)
}

/**
 * Retrieve the name of a link.
 */
fun String.linkName(): String {
    val regex = Regex(".*\\[(.*)\\]\\((.*?)\\).*")
    return regex.find(this)?.destructured?.toList()?.get(0) ?: this
}

/**
 * Retrieve the url of a link.
 */
fun String.linkUrl(): String {
    val regex = Regex(".*\\[(.*)\\]\\((.*?)\\).*")
    return regex.find(this)?.destructured?.toList()?.get(1) ?: this
}