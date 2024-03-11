package screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
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
            lineAnalyzer = mainScreenViewModel.lineAnalyzer,
            currentText = mainScreenViewModel.currentText,
            onCurrentTextChange = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetCurrentText(
                        text = it
                    )
                )
            },
            onDone = { text, pos ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.AddNewLine(
                        text = text,
                        pos = pos
                    )
                )
            },
            onLineClicked = { linePos ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetFocusedLine(
                        pos = linePos
                    )
                )
            },
            currentLine = state.currentLine
        )
    }
}