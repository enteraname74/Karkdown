package model.textutils


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
        else if (word.isStrikethrough()) word.strikethroughContent()
        else if (word.isLink()) word.linkName()
        else word

        // We need to append the whitespaces between each word :
        markdownLessString += whitespaces.getOrElse(index + 1) { "" }
    }

    return markdownLessString
}