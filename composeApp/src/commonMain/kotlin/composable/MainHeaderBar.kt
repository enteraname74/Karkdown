package composable

import Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import theme.KarkdownColorTheme

@Composable
fun MainHeaderBar(
    openFile: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KarkdownColorTheme.colorScheme.primary,
            )
            .padding(Constants.Spacing.medium)
    ) {
        Image(
            imageVector = Icons.Default.FolderOpen,
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    openFile()
                },
            colorFilter = ColorFilter.tint(color = KarkdownColorTheme.colorScheme.onPrimary)
        )
    }
}