package composable.filecontent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.markdownelement.*
import model.textutils.*

/**
 * Used to build the correct markdown view element from a line.
 */
@Composable
fun MarkdownViewBuilder(
    modifier: Modifier = Modifier,
    markdownElement: MarkdownElement,
    onClick: () -> Unit,
    onLineChanged: (String) -> Unit,
    onDone: (nextPos: Int, initialText: String) -> Unit,
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
                    onDone(userPosition + 1, "")
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
                    val lineToSave = it.toBlockQuote()
                    onLineChanged(lineToSave)
                },
                onDone = { nextPos, initialLine ->
                    onDone(nextPos, "> $initialLine")
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = {
                    onLineChanged("")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.blockquoteInnerText()
            )

            is UnorderedList -> UnorderedListView(
                innerContent = markdownElement.viewData,
                onClick = onClick,
                onLineChanged = {
                    val lineToSave = it.toUnorderedList(
                        listIndicator = markdownElement.listIndicator
                    )
                    onLineChanged(lineToSave)
                },
                onDone = { nextPos, initialText ->
                    onDone(nextPos, "${markdownElement.listIndicator} $initialText")
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = {
                    onLineChanged("")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.unorderedListContent()
            )

            is OrderedList -> OrderedListView(
                innerContent = markdownElement.viewData,
                onClick = onClick,
                onLineChanged = {
                    val lineToSave = it.toOrderedList(
                        listIndicator = markdownElement.currentIndicator
                    )
                    onLineChanged(lineToSave)
                },
                onDone = { nextPos, initialText ->
                    if (nextPos == 0) onDone(nextPos, initialText)
                    onDone(nextPos, "${markdownElement.rowData.orderedListIndicator() + 1}. $initialText")
                },
                onKeyUp = onKeyUp,
                onKeyDown = onKeyDown,
                onDeleteLine = {
                    onLineChanged("")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.orderedListContent(),
                currentIndicator = markdownElement.currentIndicator
            )
        }
    }
}

