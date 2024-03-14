package model

import model.markdownelement.*

/**
 * Methods for analyzing a line.
 */
class LineAnalyzer {
    /**
     * Check if a line is a header.
     */
    fun isHeader(line: String): Boolean {
        val regex = Regex("^#+ .+")
        return regex.matches(line)
    }

    /**
     * Check if a line is a blockquote.
     */
    private fun isBlockquote(line: String): Boolean {
        val regex = Regex("^>+.*")
        return regex.matches(line)
    }

    /**
     * Check if a line is a unordered list.
     */
    private fun isUnorderedList(line: String): Boolean {
        val regex = Regex("^(-|\\*|\\+).*")
        return regex.matches(line)
    }

    /**
     * Build a MarkdownElement from a given line.
     */
    private fun buildMarkdownElementFromLine(line: String): MarkdownElement {
        return if (isHeader(line)) Header(rowData = line)
        else if (isUnorderedList(line)) UnorderedList(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.unorderedListContent())
        )
        else if (isBlockquote(line)) Blockquote(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.blockquoteContent())
        )
        else SimpleText(rowData = line)
    }

    /**
     * Build a markdown file from a list of lines.
     */
    fun buildMarkdownFile(lines: List<String>): ArrayList<MarkdownElement> {
        val markdownFile: ArrayList<MarkdownElement> = ArrayList()

        lines.forEach { line ->
            markdownFile.add(
                buildMarkdownElementFromLine(line = line)
            )
        }

//        markdownFile.forEach {
//            println(it)
//        }

        return markdownFile
    }
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

/**
 * Build a blockquote line from a given start quotes line.
 */
fun String.toUnorderedList(listIndicator: String): String {
    if (this.isEmpty()) return listIndicator
    return "$listIndicator $this"
}

/**
 * Retrieve the content of an unordered list to show to a user.
 */
fun String.unorderedListContent(): String {
    return this.replaceFirst("[+*-]".toRegex(), "").trimStart()
}

/**
 * Retrieve the content of an unordered list to show to a user.
 */
fun String.listIndicator(): String {
    return this.firstNotNullOf { "-" }
}