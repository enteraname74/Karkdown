package composable

import Constants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import composable.text.TextBuilder
import model.markdown.EditableText
import model.markdown.MarkdownElement
import kotlin.math.max

/**
 * View for the content of a file.
 */
@Composable
fun FileView(
    modifier: Modifier = Modifier,
    fileContent: List<MarkdownElement>,
    userLine: Int,
    onEditableLineChanged: (String) -> Unit,
    onEditableLineDone: (Int) -> Unit,
    onLineClicked: (Int) -> Unit,
    currentText: String,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = fileContent.size) { pos ->
            TextBuilder(
                markdownElement = fileContent[pos],
                onClick = {
                    onLineClicked(pos)
                },
                onEditableLineChanged = {
                    onEditableLineChanged(it)
                },
                onEditableLineDone = { newPos ->
                    onEditableLineDone(newPos)
                },
                userLine = userLine,
                currentText = currentText,
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown
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