package composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import strings.appStrings

@Composable
fun FileNameDialog(
    currentFileName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var fileName by remember {
        mutableStateOf(currentFileName)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(fileName)
                }
            ) {
                Text(
                    text = appStrings.validate
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = appStrings.cancel
                )
            }
        },
        title = {
            Text(
                text = appStrings.saveFile,
                style = Constants.FontStyle.h2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            Column {
                OutlinedTextField(
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
                            onConfirm(fileName)
                        }
                    )
                )
            }
        }
    )
}