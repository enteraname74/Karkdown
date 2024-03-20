package composable

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import theme.KarkdownColorTheme

@Composable
fun MainHeaderBar(
    shouldShowDropdown: Boolean,
    setDropdownVisibility: (Boolean) -> Unit,
    onNewFile: () -> Unit,
    onOpenFile: () -> Unit,
    onQuickSave: () -> Unit,
    onSaveAs: () -> Unit,
    onExportAsPdf: () -> Unit,
    onMore: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KarkdownColorTheme.colorScheme.primary,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FileDropdownMenu(
            shouldShowDropdown = shouldShowDropdown,
            setDropdownVisibility = setDropdownVisibility,
            onOpenFile = onOpenFile,
            onQuickSave = onQuickSave,
            onSaveAs = onSaveAs,
            onExportAsPdf = onExportAsPdf,
            onNewFile = onNewFile
        )
        Icon(
            modifier = Modifier
                .padding(end = Constants.Spacing.medium)
                .clickable {
                    onMore()
                },
            imageVector = Icons.Rounded.MoreVert,
            tint = KarkdownColorTheme.colorScheme.onPrimary,
            contentDescription = null
        )
    }
}