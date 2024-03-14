package composable.filecontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.*

/**
 * Used to build the correct markdown view element from a line.
 */
@Composable
fun MarkdownViewBuilder(
    modifier: Modifier = Modifier,
    markdownElement: MarkdownElement,
    onClick: () -> Unit,
    onLineChanged: (String) -> Unit,
    onDone: (Int) -> Unit,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
    userPosition: Int,
    markdownElementPosition: Int,
    currentText: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                modifier
            )
    ) {
        when (markdownElement) {
            is Header, is SimpleText -> TextView(
                text = currentText,
                viewText = markdownElement.viewData.toString(),
                shouldFocus = markdownElementPosition == userPosition,
                onChange = {
                    onLineChanged(it)
                },
                onDone = {
                    onDone(userPosition + 1)
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = {
                    onDeleteLine(userPosition)
                },
                onClick = onClick
            )

            is Blockquote -> BlockquoteView(
                innerContent = markdownElement.viewData,
                onClick = onClick,
                onLineChanged = {
                    println("-------------------")
                    println("In blockquote with data: ${markdownElement.viewData}")
                    println("Original line to save: $it")
                    println("Blockquotes: ${markdownElement.rowData.quotes()}")
                    val lineToSave = it.toBlockQuote()
                    println("New line: $lineToSave")
                    println("-------------------")
                    onLineChanged(lineToSave)
                },
                onDone = onDone,
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = onDeleteLine,
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.blockquoteInnerText()
            )
        }
    }
}