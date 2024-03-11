package composable

import Constants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import composable.text.TextBuilder
import model.LineAnalyzer

/**
 * View for the content of a file.
 */
@Composable
fun FileView(
    modifier: Modifier = Modifier,
    fileContent: List<String>,
    lineAnalyzer: LineAnalyzer,
    currentText: String,
    currentLine: Int,
    onCurrentTextChange: (String) -> Unit,
    onDone: (String, Int) -> Unit,
    onLineClicked: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = fileContent.size) { pos ->
            lineAnalyzer.line = fileContent[pos]
            if (currentLine != pos) {
                TextBuilder(
                    lineAnalyzer = lineAnalyzer,
                    onClick = {
                        onLineClicked(pos)
                    }
                )
            } else {
                TextInput(
                    text = currentText,
                    onChange = {
                        onCurrentTextChange(it)
                    },
                    onDone = { text, linePos ->
                        onDone(text, linePos)
                    },
                    fileLine = pos
                )
            }
        }

        item {
            if (currentLine == -1) {
                TextInput(
                    text = currentText,
                    onChange = {
                        onCurrentTextChange(it)
                    },
                    onDone = { text, pos ->
                        onDone(text, pos)
                    },
                    fileLine = -1
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .height(Constants.Spacing.large)
                        .clickable {
                            onLineClicked(-1)
                        }
                )
            }
        }
    }
}