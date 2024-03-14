package screen

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import composable.FileNameDialog
import composable.FileTabHeader
import composable.FileView
import composable.MainHeaderBar
import event.MainScreenEvent
import kotlinx.coroutines.launch
import state.MainScreenState
import strings.appStrings
import theme.KarkdownColorTheme
import viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state by mainScreenViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.shouldShowErrorFileSavedResult) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = appStrings.fileCouldNotBeSaved
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.ShouldShowFileSavingError(
                    show = false
                )
            )
        }
    }
    if (state.shouldShowCorrectFileSavedResult){
        scope.launch {
            snackbarHostState.showSnackbar(
                message = appStrings.fileSaved
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.ShouldShowCorrectFileSaving(
                    show = false
                )
            )
        }
    }
    Scaffold(
        backgroundColor = KarkdownColorTheme.colorScheme.primary,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .onKeyEvent { event ->
                if (event.type != KeyEventType.KeyUp) return@onKeyEvent false
                if (event.isCtrlPressed && event.key == Key.S) {
                    mainScreenViewModel.onEvent(
                        MainScreenEvent.QuickSaveCurrentFile
                    )
                }
                if (event.isCtrlPressed && event.isShiftPressed && event.key == Key.S) {
                    mainScreenViewModel.onEvent(
                        MainScreenEvent.ShouldEnterFileName(
                            shouldSetFileName = true
                        )
                    )
                }
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FileTabHeader(
                filename = state.filename,
                isDataUpdated = state.isDataUpdated,
                isFileSelected = true,
                onClick = {

                }
            )
            FileEditor(
                mainScreenViewModel = mainScreenViewModel,
                state = state,
                paddingValues = paddingValues
            )
        }
    }

    FilePicker(
        show = state.isSelectingFile,
        fileExtensions = listOf("md"),
        title = appStrings.openFile
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

    DirectoryPicker(
        show = state.isSelectingFolder,
        title = appStrings.saveFileNameIn(filename = state.filename)
    ) { parent ->
        if (parent != null) {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SaveAsCurrentFile(
                    path = parent,
                    filename = state.filename
                )
            )
        }
        mainScreenViewModel.onEvent(
            MainScreenEvent.ShouldSelectFolder(
                shouldSelectFolder = false
            )
        )
    }

    if (state.isSettingFileName) {
        FileNameDialog(
            currentFileName = state.filename,
            onDismiss = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldEnterFileName(
                        shouldSetFileName = false
                    )
                )
            },
            onConfirm = { filename ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldEnterFileName(
                        shouldSetFileName = false
                    )
                )
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetCurrentFileName(
                        name = filename
                    )
                )
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldSelectFolder(
                        shouldSelectFolder = true
                    )
                )
            }
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
                color = KarkdownColorTheme.colorScheme.secondary,
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        FileView(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(vertical = Constants.Spacing.medium),
            fileContent = state.fileContent,
            currentText = mainScreenViewModel.currentText,
            onLineChanged = { line, pos ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetCurrentText(
                        text = line,
                        pos = pos
                    )
                )
            },
            onDone = { nextPos, initialText ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.CreateNewLine(
                        nextPos = nextPos,
                        initialText = initialText
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