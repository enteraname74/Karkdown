package composable.filecontent

import Constants
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.github.enteraname74.karkdowncore.textutils.headerLevel
import com.github.enteraname74.karkdowncore.textutils.isHeader
import theme.KarkdownColorTheme
import utils.buildCorrespondingTextStyle
import visualtransformation.TextFieldMarkdownTransformation
import visualtransformation.TextFieldViewMarkdownTransformation

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

    var textValue by remember {
        mutableStateOf(TextFieldValue(text))
    }

    var backSpaceCountWhenEmptyString by remember {
        mutableStateOf(if (text.isEmpty()) 2 else 0)
    }
//    println("IS CLICKED? $isClicked")
//    LaunchedEffect(isClicked) {
//        if (isClicked) {
//            println("TEXT - onClick called")
//            onClick()
//        }
//    }

    var cursorPosSet by remember {
        mutableStateOf(false)
    }

    if (shouldFocus && !cursorPosSet) {
//        textValue = textValue.copy(
//            text = text,
//            selection = TextRange(text.length, text.length)
//        )
        cursorPosSet = true
    } else if (!shouldFocus) {
        cursorPosSet = false
    }

    if (shouldFocus) {
        SideEffect {
            try {
                focusRequester.requestFocus()
            } catch (_: Exception) {}
        }
    }

//    textValue = textValue.copy(
//        text = if (shouldFocus) text else viewText
//    )

    println("TEXT: $text, VIEW TEXT: $viewText, textValue: ${textValue.text}")

    fun getText(): String {
        println("TEXT FROM CALLBACK: $text")
        return text
    }

    BasicTextField(
        visualTransformation = if (shouldFocus) TextFieldMarkdownTransformation() else TextFieldViewMarkdownTransformation(rowData = text),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            println("onClick called")
                            onClick()
                        }
                        if (it is FocusInteraction.Focus) {
                            println("FOCUS, will set text: ${getText()}")
                            textValue = textValue.copy(
                                text = getText(),
                            )
                        } else if (it is FocusInteraction.Unfocus) {
                            println("UNFOCUSED, will set text: $viewText")
                            textValue = textValue.copy(
                                text = viewText,
                            )
                        }
                    }
                }
            },
        cursorBrush = SolidColor(KarkdownColorTheme.colorScheme.onPrimary),
        textStyle = buildCorrespondingTextStyle(line = text),
        value = textValue,
        onValueChange = {
            val shouldNavigateToNextLine = it.text.lastOrNull() == '\n'
            if (shouldNavigateToNextLine) return@BasicTextField onDone()

            textValue = it
            if (it.text.isEmpty()) backSpaceCountWhenEmptyString++ else backSpaceCountWhenEmptyString = 0
            onChange(it.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = if (text.isHeader()) Constants.Spacing.textPadding(text.headerLevel()) else Constants.Spacing.body
            )
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                when (event.key) {
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