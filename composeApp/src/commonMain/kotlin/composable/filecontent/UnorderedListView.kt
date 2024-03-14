package composable.filecontent

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.markdownelement.MarkdownElement
import theme.KarkdownColorTheme

@Composable
fun UnorderedListView(
    innerContent: MarkdownElement,
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
    ) {
        Spacer(
            modifier = Modifier
                .size(Constants.Spacing.medium)
                .background(
                    color = KarkdownColorTheme.colorScheme.onSecondary,
                    shape = CircleShape
                )
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