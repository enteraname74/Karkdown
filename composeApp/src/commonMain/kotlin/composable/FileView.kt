package composable

import Constants
import MarkdownManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import state.MarkdownState

/**
 * View for the content of a file.
 */
@Composable
fun FileView(
    markdownManager: MarkdownManager,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val state: MarkdownState by markdownManager.state.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(count = state.fileContent.size) { pos ->

            val currentElement = state.fileContent[pos]

//            MarkdownViewBuilder(
//                markdownElement = currentElement,
//                onClick = {
//                    markdownManager.setFocusedLine(pos = pos)
//                },
//                onLineChanged = { line ->
//                    markdownManager.updateLineAt(
//                        line = line,
//                        pos = pos,
//                    )
//                },
//                onDone = { newPos, initialText ->
//                    markdownManager.createNewLine(
//                        nextPos = newPos,
//                        initialText = initialText,
//                    )
//                },
//                userPosition = state.filePos,
//                markdownElementPosition = pos,
//                currentText = if (state.filePos == pos) markdownManager.currentText else currentElement.rowData,
//                onKeyUp = markdownManager::goUp,
//                onKeyDown = markdownManager::goDown,
//                onDeleteLine = {
//                    markdownManager.deleteLine(pos = pos)
//                },
//                filePath = markdownManager.getFilePath()
//            )
        }

        item {
            if (state.filePos < state.fileContent.size) {
                Spacer(
                    modifier = Modifier
                        .height(Constants.Spacing.large)
                        .clickable {
                            markdownManager.setFocusedLine(pos = -1)
                        }
                )
            }
        }
    }
}