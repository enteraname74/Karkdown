package composable

import Constants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.filecontent.MarkdownViewBuilder
import model.markdownelement.MarkdownElement
import java.nio.file.Path

/**
 * View for the content of a file.
 */
@Composable
fun FileView(
    modifier: Modifier = Modifier,
    fileContent: List<MarkdownElement>,
    filePath: Path?,
    userLine: Int,
    onLineChanged: (String, Int) -> Unit,
    onDone: (nextPos: Int, initialText: String) -> Unit,
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
                    onLineChanged(line, pos)
                },
                onDone = { newPos, initialText ->
                    onDone(newPos, initialText)
                },
                userPosition = userLine,
                markdownElementPosition = pos,
                currentText = if (userLine == pos) currentText else fileContent[pos].rowData,
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = onDeleteLine,
                filePath = filePath
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

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}