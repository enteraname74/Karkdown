package model

import model.markdown.Header
import model.markdown.MarkdownElement
import model.markdown.SimpleText

/**
 * Methods for analyzing a line.
 */
class LineAnalyzer {
    /**
     * Check if a line is a header.
     */
    fun isHeader(line: String): Boolean {
        val regex = Regex("#+ .+")
        return regex.matches(line)
    }

    /**
     * Build a markdown file from a list of lines.
     */
    fun buildMarkdownFile(lines: List<String>): ArrayList<MarkdownElement> {
        val markdownFile: ArrayList<MarkdownElement> = ArrayList()

        lines.forEach { line ->
            if (isHeader(line)) markdownFile.add(Header(line))
            else markdownFile.add(SimpleText(line))
        }

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
fun String.toHeader(): String {
    val regex = Regex("\\w.*")
    return regex.find(this)?.value ?: ""
}