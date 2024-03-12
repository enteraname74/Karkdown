package screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import composable.FileView
import event.MainScreenEvent
import viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state by mainScreenViewModel.state.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ){
        FileView(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(vertical = Constants.Spacing.medium),
            fileContent = state.fileContent,
            currentText = mainScreenViewModel.currentText,
            onEditableLineChanged = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetCurrentText(
                        text = it
                    )
                )
            },
            onEditableLineDone = { nextPos ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.CreateNewLine(
                        nextPos = nextPos
                    )
                )
            },
            onLineClicked = { linePos ->
                println("Line clicked at pos: $linePos")
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetFocusedLine(
                        pos = linePos
                    )
                )
            },
            userLine = state.userPosition
        )
    }
}