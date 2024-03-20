package composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import theme.KarkdownColorTheme

@Composable
fun MainHeaderBar(
    shouldShowDropdown: Boolean,
    setDropdownVisibility: (Boolean) -> Unit,
    onNewFile: () -> Unit,
    onOpenFile: () -> Unit,
    onQuickSave: () -> Unit,
    onSaveAs: () -> Unit,
    onExportAsPdf: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KarkdownColorTheme.colorScheme.primary,
            )
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
    }
}