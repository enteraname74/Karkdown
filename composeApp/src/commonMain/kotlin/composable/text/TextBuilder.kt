package composable.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import composable.TextInput
import model.markdown.EditableText
import model.markdown.Header
import model.markdown.MarkdownElement
import model.markdown.SimpleText

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
    userLine: Int,
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
                    onEditableLineDone(userLine+1)
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown
            )

            is Header -> HeaderText(
                text = markdownElement.viewData,
                headerLevel = markdownElement.headerLevel
            )

            is SimpleText -> StandardText(
                text = markdownElement.viewData
            )
        }
    }
}