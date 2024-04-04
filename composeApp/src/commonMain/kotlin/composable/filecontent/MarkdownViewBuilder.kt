package composable.filecontent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.markdownelement.*
import model.textutils.*
import java.nio.file.Path

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
    filePath: Path?
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
                    onLineChanged("${markdownElement.rowData.blockquoteQuotes()}${markdownElement.rowData.substring(startIndex = 2)}")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.blockquoteInnerText(),
                filePath = filePath
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
                    onLineChanged("${markdownElement.rowData.unorderedListIndicator()}${markdownElement.rowData.substring(startIndex = 2)}")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.unorderedListContent(),
                filePath = filePath
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
                    onLineChanged("${markdownElement.rowData.orderedListIndicator()}.${markdownElement.rowData.substring(startIndex = 3)}")
                },
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                currentText = currentText.orderedListContent(),
                currentIndicator = markdownElement.currentIndicator,
                filePath = filePath
            )
            is HorizontalRule -> HorizontalRuleView(
                currentText = markdownElement.rowData,
                onClick = onClick,
                onLineChanged = onLineChanged,
                onDone = {
                    onDone(userPosition+1, "")
                },
                onKeyDown = onKeyDown,
                onKeyUp = onKeyUp,
                onDeleteLine = onDeleteLine,
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition
            )
            is Image -> ImageView(
                imageName = markdownElement.imageName,
                imagePath = markdownElement.imagePath,
                currentText = markdownElement.rowData,
                onClick = onClick,
                onLineChanged = onLineChanged,
                onDone = {
                    onDone(userPosition+1, "")
                },
                onKeyDown = onKeyDown,
                onKeyUp = onKeyUp,
                onDeleteLine = onDeleteLine,
                userPosition = userPosition,
                markdownElementPosition = markdownElementPosition,
                filePath = filePath
            )
        }
    }
}

