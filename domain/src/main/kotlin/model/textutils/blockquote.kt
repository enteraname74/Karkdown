package model.textutils

/**
 * Check if a line is a blockquote.
 */
fun String.isBlockquote(): Boolean {
    val regex = Regex("^>+ .*")
    return regex.matches(this)
}

/**
 * Retrieve the level of a blockquote.
 */
fun String.blockquoteLevel(): Int {
    val regex = Regex("^>+")
    val matchedElements = regex.find(this)
    return matchedElements?.value?.length ?: 0
}

/**
 * Retrieve the quotes of a blockquote.
 */
fun String.blockquoteQuotes(): String {
    val regex = Regex("^>+")
    val matchedElements = regex.find(this)
    return matchedElements?.value ?: ""
}

/**
 * Retrieve the content of a blockquote to show to a user.
 */
fun String.blockquoteContent(): String {
    return this.replaceFirst(">", "").trimStart()
}

/**
 * Retrieve the inner text of a blockquote.
 */
fun String.blockquoteInnerText(): String {
    return this.replaceFirst("^>+".toRegex(), "").trimStart()
}

/**
 * Build a blockquote line from a given start quotes line.
 */
fun String.toBlockQuote(): String {
    if (this.isEmpty()) return ">"

    val optionalSpace = if (this.first() == '>') "" else " "

    return ">$optionalSpace$this"
}