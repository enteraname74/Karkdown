package model

import model.markdownelement.*
import model.textutils.*

/**
 * Utils building a list of markdown elements from a row data.
 */
class FileBuilder {
    /**
     * Build a MarkdownElement from a given line.
     */
    private fun buildMarkdownElementFromLine(line: String): MarkdownElement {
        return if (line.isHeader()) Header(rowData = line)
        else if (line.isUnorderedList()) UnorderedList(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.unorderedListContent())
        )
        else if (line.isOrderedList()) OrderedList(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.orderedListContent())
        )
        else if (line.isBlockquote()) Blockquote(
            rowData = line,
            innerData = buildMarkdownElementFromLine(line.blockquoteContent())
        )
        else if (line.isHorizontalRule()) HorizontalRule(
            rowData = line
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