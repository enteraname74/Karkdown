package composable.filecontent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.Blockquote
import model.Header
import model.MarkdownElement
import model.SimpleText

/**
 * Used to build the correct markdown view element from a line.
 */
@Composable
fun MarkdownViewBuilder(
    modifier: Modifier = Modifier,
    markdownElement: MarkdownElement,
    onClick: () -> Unit = {},
    onEditableLineChanged: (String) -> Unit = {},
    onEditableLineDone: (Int) -> Unit = {},
    onKeyDown: () -> Unit = {},
    onKeyUp: () -> Unit = {},
    onDeleteLine: (Int) -> Unit = {},
    userPosition: Int = 0,
    markdownElementPosition: Int = 0,
    currentText: String = "",
) {
    when (markdownElement) {
        is Header, is SimpleText -> TextView(
            text = currentText,
            viewText = markdownElement.viewData.toString(),
            shouldFocus = markdownElementPosition == userPosition,
            onChange = {
                onEditableLineChanged(it)
            },
            onDone = {
                onEditableLineDone(userPosition + 1)
            },
            onKeyUp = onKeyUp,
            onKeyDown = onKeyDown,
            onDeleteLine = {
                onDeleteLine(userPosition)
            },
            onClick = onClick
        )
        is Blockquote -> BlockquoteView(innerContent = markdownElement.viewData)
    }
}