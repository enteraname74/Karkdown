package utils

import Constants
import androidx.compose.ui.text.TextStyle
import model.LineAnalyzer
import model.headerLevel

/**
 * Build a corresponding text style from a given line.
 */
fun buildCorrespondingTextStyle(
    line: String
): TextStyle {
    val lineAnalyzer = LineAnalyzer()

    return if (lineAnalyzer.isHeader(line))
        buildCorrespondingHeaderTextStyle(line.headerLevel())
    else
        Constants.FontStyle.body
}

/**
 * Build a corresponding header text style from a header level.
 */
fun buildCorrespondingHeaderTextStyle(
    headerLevel: Int
): TextStyle {
    return with(Constants.FontStyle) {
        when (headerLevel) {
            1 -> h1
            2 -> h2
            3 -> h3
            4 -> h4
            5 -> h5
            else -> h6
        }
    }
}