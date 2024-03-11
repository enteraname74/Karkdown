package model

/**
 * Methods for analyzing a line.
 */
abstract class LineAnalyzer(
    var line: String
) {
    /**
     * Check if a line is a header.
     */
    abstract fun isHeader(): Boolean

    /**
     * Retrieve the header level of a line.
     */
    abstract fun headerLevel(): Int

    abstract fun getHeader(): String
}