package composable

import Constants
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import strings.appStrings
import theme.KarkdownColorTheme

@Composable
fun FileNameDialog(
    currentFileName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var fileName by remember {
        mutableStateOf(TextFieldValue(text = currentFileName))
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
        fileName = fileName.copy(
            selection = TextRange(currentFileName.length, currentFileName.length)
        )
    }

    AlertDialog(
        containerColor = KarkdownColorTheme.colorScheme.primary,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(fileName.text)
                }
            ) {
                Text(
                    text = appStrings.validate,
                    color = KarkdownColorTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = appStrings.cancel,
                    color = KarkdownColorTheme.colorScheme.onPrimary
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = appStrings.saveFile,
                style = Constants.FontStyle.h2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = KarkdownColorTheme.colorScheme.onPrimary,
                        cursorColor = KarkdownColorTheme.colorScheme.onPrimary,
                        selectionColors = TextSelectionColors(
                            handleColor = KarkdownColorTheme.colorScheme.onPrimary,
                            backgroundColor = KarkdownColorTheme.colorScheme.secondary
                        ),
                        unfocusedTextColor = KarkdownColorTheme.colorScheme.onPrimary,
                        focusedContainerColor = Color.Transparent,
                        focusedLabelColor = KarkdownColorTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = KarkdownColorTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = KarkdownColorTheme.colorScheme.onPrimary,
                        focusedBorderColor = KarkdownColorTheme.colorScheme.onPrimary
                    ),
                    value = fileName,
                    onValueChange = {
                        fileName = it
                    },
                    label = {
                        Text(
                            text = appStrings.fileName
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onConfirm(fileName.text)
                        }
                    )
                )
            }
        }
    )
}