package composable.filecontent

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.markdownelement.MarkdownElement
import theme.KarkdownColorTheme

@Composable
fun BlockquoteView(
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
) {
    Row(
        modifier = Modifier
            .background(color = KarkdownColorTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Divider(
            color = KarkdownColorTheme.colorScheme.accent,
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
        )

        MarkdownViewBuilder(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = Constants.Spacing.medium,
                    horizontal = Constants.Spacing.medium
                ),
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