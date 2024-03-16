package model.textutils

/**
 * Check if a string is bold.
 */
fun String.isBold(): Boolean {
    val regex = Regex("[^\\*_]*(\\*{2}[^\\*]+\\*{2}|_{2}[^_]+_{2})[^\\*_]*")
    return regex.matches(this)
}

/**
 * Check if a string is italic.
 */
fun String.isItalic(): Boolean {
    val regex = Regex("[^\\*_]*(\\*[^\\*]+\\*|_[^_]+_)[^\\*_]*")
    return regex.matches(this)
}

/**
 * Check if a string is bold.
 */
fun String.isBoldAndItalic(): Boolean {
    val regex = Regex("[^\\*_]*(\\*{3}[^\\*]+\\*{3}|_{3}[^_]+_{3})[^\\*_]*")
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
 * Check if the bold text is with * characters.
 */
fun String.isStarItalic(): Boolean {
    val regex = Regex(".*(\\*.+\\*).*")
    return regex.matches(this)
}

/**
 * Check if the bold text is with * characters.
 */
fun String.isStarBoldAndItalic(): Boolean {
    val regex = Regex(".*(\\*{3}.+\\*{3}).*")
    return regex.matches(this)
}

/**
 * Remove all markdown elements on a String.
 */
fun String.withoutMarkdown(): String {
    val withoutHeaders = this.headerContent()

    val whitespaces = withoutHeaders.split("\\S+".toRegex())
    val words = withoutHeaders.split("\\s+".toRegex())

    var markdownLessString = ""

    words.forEachIndexed { index, word ->
        markdownLessString += if (word.isBold()) word.boldContent()
        else if (word.isItalic()) word.italicContent()
        else if (word.isBoldAndItalic()) word.boldAndItalicContent()
        else word

        // We need to append the whitespaces between each word :
        markdownLessString += whitespaces.getOrElse(index + 1) { "" }
    }

    return markdownLessString
}

/**
 * Retrieve the content of a bold section to show to a user.
 */
private fun String.boldContent(): String {
    return this.replace("\\*{2}|_{2}".toRegex(), "")
}

/**
 * Retrieve the content of an italic section to show to a user.
 */
private fun String.italicContent(): String {
    return this.replace("[*_]".toRegex(), "")
}

/**
 * Retrieve the content of a bold and italic section to show to a user.
 */
private fun String.boldAndItalicContent(): String {
    return this.replace("\\*{3}|_{3}".toRegex(), "")
}