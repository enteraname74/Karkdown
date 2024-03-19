package composable.filecontent

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.KarkdownColorTheme

@Composable
fun HorizontalRuleView(
    currentText: String,
    onClick: () -> Unit,
    onLineChanged: (String) -> Unit,
    onDone: () -> Unit,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
    userPosition: Int,
    markdownElementPosition: Int,
) {
    // If we are not on the line, we should show the styled view of the horizontal rule :
    if (userPosition != markdownElementPosition) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.Spacing.veryLarge)
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = KarkdownColorTheme.colorScheme.onSecondary
            )
        }
    } else {
        TextView(
            text = currentText,
            viewText = currentText,
            shouldFocus = true,
            onClick = onClick,
            onChange = onLineChanged,
            onDone = onDone,
            onKeyUp = onKeyUp,
            onKeyDown = onKeyDown,
            onDeleteLine = {
                onDeleteLine(userPosition)
            }
        )
    }
}