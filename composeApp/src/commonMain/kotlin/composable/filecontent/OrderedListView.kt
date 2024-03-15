package composable.filecontent

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.markdownelement.MarkdownElement
import model.markdownelement.OrderedList
import theme.KarkdownColorTheme

@Composable
fun OrderedListView(
    innerContent: MarkdownElement,
    onClick: () -> Unit,
    onLineChanged: (String) -> Unit,
    onDone: (nextPos: Int, initialText: String) -> Unit,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
    userPosition: Int,
    markdownElementPosition: Int,
    currentText: String,
    currentIndicator: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Constants.Spacing.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
    ) {
        Text(
            text = "$currentIndicator. ",
            style = Constants.FontStyle.body
        )

        MarkdownViewBuilder(
            markdownElement = innerContent,
            onClick = onClick,
            onLineChanged = onLineChanged,
            onDone = onDone,
            onKeyUp = onKeyUp,
            onKeyDown = onKeyDown,
            onDeleteLine = onDeleteLine,
            userPosition = userPosition,
            markdownElementPosition = markdownElementPosition,
            currentText = currentText
        )
    }
}