package composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
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
    onDeleteLine: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var textValue by remember {
        mutableStateOf(TextFieldValue(text))
    }

    var backSpaceCountWhenEmptyString by remember {
        mutableStateOf(if (text.isEmpty()) 2 else 0)
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = "cursor pos") {
        if (text.isNotEmpty()) {
            textValue = TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            )
        }
    }

    BasicTextField(
        value = textValue,
        onValueChange = {
            textValue = it
            println("_")
            if (it.text.isEmpty()) backSpaceCountWhenEmptyString++ else backSpaceCountWhenEmptyString = 0
            onChange(it.text)
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
                when(event.key) {
                    Key.DirectionUp -> onKeyUp()
                    Key.DirectionDown -> onKeyDown()
                    Key.Backspace -> {
                        if (text.isEmpty()) {
                            if (backSpaceCountWhenEmptyString == 2) onDeleteLine()
                            else backSpaceCountWhenEmptyString++
                        }
                    }
                }
                false
            }
    )
}
