package composable

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import strings.appStrings
import theme.KarkdownColorTheme

@Composable
fun FileDropdownMenu(
    shouldShowDropdown: Boolean,
    setDropdownVisibility: (Boolean) -> Unit,
    onOpenFile: () -> Unit,
    onQuickSave: () -> Unit,
    onSaveAs: () -> Unit,
    onExportAsPdf: () -> Unit
) {
    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
    ) {
        TextButton(
            onClick = {
                setDropdownVisibility(!shouldShowDropdown)
            }
        ) {
            Text(
                text = appStrings.file,
                color = KarkdownColorTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(
                    color = KarkdownColorTheme.colorScheme.primary
                ),
            expanded = shouldShowDropdown,
            onDismissRequest = {
                setDropdownVisibility(false)
            }
        ) {
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = appStrings.openFile,
                            color = KarkdownColorTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Ctrl+O",
                            color = KarkdownColorTheme.colorScheme.subText
                        )
                    }
                },
                onClick = onOpenFile
            )
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = appStrings.saveFile,
                            color = KarkdownColorTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Ctrl+S",
                            color = KarkdownColorTheme.colorScheme.subText
                        )
                    }
                },
                onClick = onQuickSave
            )
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = appStrings.saveAs,
                            color = KarkdownColorTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Ctrl+Maj+S",
                            color = KarkdownColorTheme.colorScheme.subText
                        )
                    }
                },
                onClick = onSaveAs
            )
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = appStrings.exportAsPdf,
                            color = KarkdownColorTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Ctrl+Maj+P",
                            color = KarkdownColorTheme.colorScheme.subText
                        )
                    }
                },
                onClick = onExportAsPdf
            )
        }
    }
}