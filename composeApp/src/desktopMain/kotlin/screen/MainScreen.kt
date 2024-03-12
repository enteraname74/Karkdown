package screen

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalWindowInfo
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import composable.FileView
import composable.MainHeaderBar
import event.MainScreenEvent
import state.MainScreenState
import viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state by mainScreenViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyUp) return@onKeyEvent false
                if (event.isCtrlPressed && event.key == Key.S) println("Ctrl S pressed")
                false
            },
        topBar = {
            MainHeaderBar {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldSelectFile(
                        shouldSelectFile = true
                    )
                )
            }
        }
    ) { paddingValues ->
        FileEditor(
            mainScreenViewModel = mainScreenViewModel,
            state = state,
            paddingValues = paddingValues
        )
    }

    FilePicker(
        show = state.isSelectingFile,
        fileExtensions = listOf("md"),
        title = "Open file"
    ) { file ->
        if (file != null) {
            mainScreenViewModel.onEvent(
                MainScreenEvent.OpenFile(
                    filepath = file.path
                )
            )
        }
        mainScreenViewModel.onEvent(
            MainScreenEvent.ShouldSelectFile(
                shouldSelectFile = false
            )
        )
    }
}

@Composable
fun FileEditor(
    mainScreenViewModel: MainScreenViewModel,
    state: MainScreenState,
    paddingValues: PaddingValues
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(
                color = CardDefaults.cardColors().containerColor,
                shape = RoundedCornerShape(
                    topStart = Constants.Spacing.medium,
                    topEnd = Constants.Spacing.medium
                )
            ),
        horizontalArrangement = Arrangement.Center
    ) {
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
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetFocusedLine(
                        pos = linePos
                    )
                )
            },
            userLine = state.userPosition,
            onKeyUp = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.GoUp
                )
            },
            onKeyDown = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.GoDown
                )
            },
            onDeleteLine = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.DeleteLine(it)
                )
            }
        )
    }
}