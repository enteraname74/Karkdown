package composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Text input for modifying file content
 */
@Composable
fun TextInput(
    text: String,
    onChange: (String) -> Unit,
    onDone: () -> Unit,
    onKeyUp: () -> Unit,
    onKeyDown: () -> Unit,
    shouldInitCursorPosition: Boolean,
    removeInitCursorPosition: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = text,
        onValueChange = {
            onChange(it)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        modifier = Modifier
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyUp) return@onKeyEvent false
                if (event.key == Key.DirectionUp) {
                    onKeyUp()
                    return@onKeyEvent true
                } else if (event.key == Key.DirectionDown) {
                    onKeyDown()
                    return@onKeyEvent true
                }
                false
            }
    )
}