package composable

import Constants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import composable.filecontent.MarkdownViewBuilder
import model.MarkdownElement

/**
 * View for the content of a file.
 */
@Composable
fun FileView(
    modifier: Modifier = Modifier,
    fileContent: List<MarkdownElement>,
    userLine: Int,
    onEditableLineChanged: (String, Int) -> Unit,
    onEditableLineDone: (Int) -> Unit,
    onLineClicked: (Int) -> Unit,
    currentText: String,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = fileContent.size) { pos ->
            MarkdownViewBuilder(
                markdownElement = fileContent[pos],
                onClick = {
                    onLineClicked(pos)
                },
                onLineChanged = { line ->
                    onEditableLineChanged(line, pos)
                },
                onDone = { newPos ->
                    onEditableLineDone(newPos)
                },
                userPosition = userLine,
                markdownElementPosition = pos,
                currentText = if (userLine == pos) currentText else fileContent[pos].rowData,
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = onDeleteLine
            )
        }

        item {
            if (userLine < fileContent.size) {
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