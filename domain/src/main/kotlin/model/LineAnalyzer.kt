package model

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
        val regex = Regex("^>+ .+")
        return regex.matches(line)
    }

    /**
     * Build a MarkdownElement from a given line.
     */
    private fun buildMarkdownElementFromLine(line: String): MarkdownElement {
        return if (isHeader(line)) Header(line)
        else if (isBlockquote(line)) Blockquote(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.blockquoteContent())
        )
        else SimpleText(line)
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
    return this.replaceFirst("^>".toRegex(), "").trimStart()
}