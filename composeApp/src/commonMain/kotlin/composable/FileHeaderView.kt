package composable

import Constants
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import strings.appStrings
import theme.KarkdownColorTheme
import utils.FileHeader

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun FileHeaderView(
    header: FileHeader,
    onClick: () -> Unit,
    onClose: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    var isDeletingDialogOpened by remember {
        mutableStateOf(false)
    }

    var isHovered by remember {
        mutableStateOf(false)
    }

    ConfirmDialog(
        show = isDeletingDialogOpened,
        onCloseFile = onClose,
        setVisibility = {
            isDeletingDialogOpened = it
        }
    )

    Column(
        modifier = Modifier
            .width(150.dp)
            .hoverable(interactionSource = interactionSource)
            .onClick(
                matcher = PointerMatcher.mouse(PointerButton.Tertiary),
                onClick = {
                    if (header.isDataUpdated) {
                        onClose()
                    } else {
                        isDeletingDialogOpened = true
                    }
                }
            )
            .onPointerEvent(eventType = PointerEventType.Enter) {
                isHovered = true
            }.onPointerEvent(eventType = PointerEventType.Exit) {
                isHovered = false
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = KarkdownColorTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Constants.Spacing.small)
                )
                .clickable {
                    onClick()
                }
                .padding(
                    top = Constants.Spacing.medium,
                    start = Constants.Spacing.medium,
                    end = Constants.Spacing.medium,
                    bottom = Constants.Spacing.small
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.small)
            ) {
                if (!header.isDataUpdated) {
                    Icon(
                        imageVector = Icons.Rounded.FiberManualRecord,
                        contentDescription = null,
                        modifier = Modifier.size(Constants.ImageSize.small),
                        tint = KarkdownColorTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = header.fileName,
                    style = Constants.FontStyle.small,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (isHovered) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    modifier = Modifier
                        .size(Constants.ImageSize.small)
                        .clickable {
                            if (header.isDataUpdated) {
                                onClose()
                            } else {
                                isDeletingDialogOpened = true
                            }
                        },
                    contentDescription = null,
                    tint = KarkdownColorTheme.colorScheme.onPrimary
                )
            }
        }
        if (header.isSelected) {
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth(),
                color = KarkdownColorTheme.colorScheme.accent
            )
        }
    }
}

@Composable
private fun ConfirmDialog(
    show: Boolean,
    setVisibility: (Boolean) -> Unit,
    onCloseFile: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = {
                setVisibility(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        setVisibility(false)
                        onCloseFile()
                    }
                ) {
                    Text(
                        text = appStrings.validate,
                        style = Constants.FontStyle.small
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        setVisibility(false)
                    }
                ) {
                    Text(
                        text = appStrings.cancel,
                        style = Constants.FontStyle.small
                    )
                }
            },
            containerColor = KarkdownColorTheme.colorScheme.primary,
            textContentColor = KarkdownColorTheme.colorScheme.onPrimary,
            titleContentColor = KarkdownColorTheme.colorScheme.onPrimary,
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = appStrings.unsavedChangesDialogTitle,
                    style = Constants.FontStyle.h3
                )
            },
            text = {
                Text(
                    textAlign = TextAlign.Center,
                    text = appStrings.unsavedChangesDialogText,
                    style = Constants.FontStyle.body
                )
            }
        )
    }
}