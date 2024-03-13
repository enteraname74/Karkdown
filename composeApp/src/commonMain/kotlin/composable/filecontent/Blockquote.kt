package composable.filecontent

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.markdown.MarkdownElement
import theme.KarkdownColorTheme

@Composable
fun Blockquote(
    innerContent: MarkdownElement
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
                .width(Constants.Spacing.medium)
        )

        TextBuilder(
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = Constants.Spacing.medium,
                    horizontal = Constants.Spacing.medium
                ),
            markdownElement = innerContent
        )
    }
}