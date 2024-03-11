package composable.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.LineAnalyzer

/**
 * Used to build the correct text view element from a line.
 */
@Composable
fun TextBuilder(
    lineAnalyzer: LineAnalyzer,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable{
            onClick()
        }
    ) {
        if (lineAnalyzer.isHeader()) Header(
            text = lineAnalyzer.getHeader(),
            headerLevel = lineAnalyzer.headerLevel()
        )
        else StandardText(
            text = lineAnalyzer.line
        )
    }
}