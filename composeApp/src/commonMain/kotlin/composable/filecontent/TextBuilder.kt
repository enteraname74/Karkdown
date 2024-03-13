package composable.filecontent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.markdown.*

/**
 * Used to build the correct text view element from a line.
 */
@Composable
fun TextBuilder(
    markdownElement: MarkdownElement,
    onClick: () -> Unit,
    onEditableLineChanged: (String) -> Unit,
    onEditableLineDone: (Int) -> Unit,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
    userPosition: Int,
    currentText: String,
) {
    Row(
        modifier = Modifier.clickable {
            onClick()
        }.fillMaxWidth()
    ) {
        when (markdownElement) {
            is EditableText -> TextInput(
                text = currentText,
                onChange = {
                    onEditableLineChanged(it)
                },
                onDone = {
                    onEditableLineDone(userPosition+1)
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = {
                    onDeleteLine(userPosition)
                }
            )

            is Header -> HeaderText(
                text = markdownElement.viewData,
                headerLevel = markdownElement.headerLevel
            )

            is SimpleText -> StandardText(
                text = markdownElement.viewData
            )

            is Blockquote -> Blockquote(text = markdownElement.viewData)
        }
    }
}