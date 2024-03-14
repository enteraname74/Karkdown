package composable.filecontent

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import theme.KarkdownColorTheme
import utils.buildCorrespondingTextStyle

/**
 * Text input for modifying file content
 */
@Composable
fun TextView(
    text: String,
    viewText: String,
    shouldFocus: Boolean,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    onDone: () -> Unit,
    onKeyUp: () -> Unit,
    onKeyDown: () -> Unit,
    onDeleteLine: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isClicked by interactionSource.collectIsPressedAsState()

    var textValue by remember {
        mutableStateOf(TextFieldValue(text))
    }

    var backSpaceCountWhenEmptyString by remember {
        mutableStateOf(if (text.isEmpty()) 2 else 0)
    }

    if (isClicked) {
        onClick()
    }

    if (shouldFocus) {
        SideEffect {
            focusRequester.requestFocus()
        }
    }

    textValue = textValue.copy(
        text = if (isFocused) text else viewText
    )

    BasicTextField(
        interactionSource = interactionSource,
        cursorBrush = SolidColor(KarkdownColorTheme.colorScheme.onPrimary),
        textStyle = buildCorrespondingTextStyle(line = text),
        value = textValue,
        onValueChange = {
            if (!isFocused) return@BasicTextField
            val shouldNavigateToNextLine = it.text.lastOrNull() == '\n'
            if (shouldNavigateToNextLine) return@BasicTextField onDone()

            textValue = it
            if (it.text.isEmpty()) backSpaceCountWhenEmptyString++ else backSpaceCountWhenEmptyString = 0
            onChange(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyUp) return@onKeyEvent false
                when(event.key) {
                    Key.DirectionUp -> onKeyUp()
                    Key.DirectionDown -> onKeyDown()
                    Key.Backspace -> {
                        val isAtStartOfLine = textValue.selection.start == 0
                        if (isAtStartOfLine) {
                            // if the text is empty, we don't want to remove the line directly
                            if (text.isEmpty()) {
                                if (backSpaceCountWhenEmptyString == 2) onDeleteLine()
                                else backSpaceCountWhenEmptyString++
                            } else {
                                onDeleteLine()
                            }
                        }

                    }
                }
                false
            }
    )
}