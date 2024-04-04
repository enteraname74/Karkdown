package model.textutils

/**
 * Check if a line is an ordered list.
 */
fun String.isOrderedList(): Boolean {
    val regex = Regex("\\d+\\. .*");
    return regex.matches(this)
}

/**
 * Build a blockquote line from a given start quotes line.
 */
fun String.toOrderedList(listIndicator: Int): String {
    return if (this.isEmpty()) "$listIndicator. "
    else "$listIndicator. $this"
}

/**
 * Retrieve the content of an ordered list to show to a user.
 */
fun String.orderedListContent(): String {
    return this.replaceFirst("\\d*.".toRegex(), "").trimStart()
}

/**
 * Retrieve the indicator used by the user for an ordered list.
 * If nothing is found, it returns the first possible indicator for an ordered list, 1.
 */
fun String.orderedListIndicator(): Int {
    val regex = Regex("^\\d*")
    return regex.find(this)?.value?.toIntOrNull() ?: 1
}