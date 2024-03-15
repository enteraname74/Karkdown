package model.textutils

/**
 * Check if a line is a header.
 */
fun String.isHeader(): Boolean {
    val regex = Regex("^#+ .+")
    return regex.matches(this)
}

/**
 * Retrieve the header level of a header line.
 */
fun String.headerLevel(): Int {
    val regex = Regex("^#+")
    val matchedElements = regex.find(this)
    return matchedElements?.value?.length ?: 0
}

/**
 * Retrieve the content of a header line to show to a user.
 */
fun String.headerContent(): String {
    val regex = Regex("\\w.*")
    return regex.find(this)?.value ?: ""
}