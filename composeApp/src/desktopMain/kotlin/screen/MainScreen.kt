package screen

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FolderOff
import androidx.compose.material.icons.rounded.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.window.DialogWindow
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import composable.*
import event.MainScreenEvent
import kotlinx.coroutines.launch
import state.MainScreenState
import strings.appStrings
import theme.KarkdownColorTheme
import viewmodel.MainScreenViewModel
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

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
    if (state.shouldShowCorrectFileSavedResult) {
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
                if (event.isCtrlPressed) {
                    if (event.key == Key.N && event.type == KeyEventType.KeyUp) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.CreateNewFile
                        )
                    }
                    else if (event.key == Key.O) {
//                        mainScreenViewModel.onEvent(
//                            MainScreenEvent.ShouldSelectFile(
//                                shouldSelectFile = true
//                            )
//                        )
                        val filePath = fileChooserDialog("AMOGUS BAKA")
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.OpenFile(
                                filepath = filePath
                            )
                        )
                    }
                    else if (event.isShiftPressed && event.key == Key.S) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.ShouldEnterFileName(
                                shouldSetFileName = true
                            )
                        )
                    }
                    else if (event.key == Key.S) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.QuickSaveCurrentFile
                        )
                    }
                    else if (event.isShiftPressed && event.key == Key.P) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.ShouldEnterFileNameForPdf(
                                shouldSetFileName = true
                            )
                        )
                    }
                }
                true
            },
        topBar = {
            MainScreenHeaderBar(
                mainScreenViewModel = mainScreenViewModel,
                state = state
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.filesHeaders.isEmpty()) {

                val focusRequester = remember { FocusRequester() }

                SideEffect {
                    try {
                        focusRequester.requestFocus()
                    } catch (_: Exception) {
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = Constants.Spacing.medium),
                        text = appStrings.noFileOpen,
                        style = Constants.FontStyle.h1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
                    ) {
                        ImageButton(
                            onClick = {
                                mainScreenViewModel.onEvent(
                                    MainScreenEvent.CreateNewFile
                                )
                            },
                            imageVector = Icons.Rounded.Add,
                            name = appStrings.newFile
                        )
                        ImageButton(
                            onClick = {
                                mainScreenViewModel.onEvent(
                                    MainScreenEvent.ShouldSelectFile(
                                        shouldSelectFile = true
                                    )
                                )
                            },
                            imageVector = Icons.Rounded.FolderOpen,
                            name = appStrings.openFile
                        )
                    }
                }
            } else {
                FileHeaders(
                    headers = state.filesHeaders,
                    onHeaderClicked = {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.SwitchCurrentFile(
                                filePos = it
                            )
                        )
                    },
                    onCreateFile = {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.CreateNewFile
                        )
                    },
                    onCloseFile = {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.CloseFile(
                                filePos = it
                            )
                        )
                    }
                )
                FileEditor(
                    mainScreenViewModel = mainScreenViewModel,
                    state = state,
                    paddingValues = paddingValues
                )
            }
        }
    }

    FilePicker(
        initialDirectory = System.getProperty("user.home"),
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
        initialDirectory = state.filepath?.toString() ?: System.getProperty("user.home"),
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

    DirectoryPicker(
        initialDirectory = state.filepath?.toString() ?: System.getProperty("user.home"),
        show = state.isSelectingFolderForPdf,
        title = appStrings.saveFileNameIn(filename = state.pdfName)
    ) { parent ->
        if (parent != null) {
            mainScreenViewModel.onEvent(
                MainScreenEvent.ExportAsPdf(path = parent)
            )
        }
        mainScreenViewModel.onEvent(
            MainScreenEvent.ShouldSelectFolderForPdf(
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

    if (state.isSettingFileNameForPdf) {
        FileNameDialog(
            currentFileName = state.filename.replace(".md", ""),
            onDismiss = {
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldEnterFileNameForPdf(
                        shouldSetFileName = false
                    )
                )
            },
            onConfirm = { filename ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldEnterFileNameForPdf(
                        shouldSetFileName = false
                    )
                )
                mainScreenViewModel.onEvent(
                    MainScreenEvent.SetPdfName(
                        name = filename
                    )
                )
                mainScreenViewModel.onEvent(
                    MainScreenEvent.ShouldSelectFolderForPdf(
                        shouldSelectFolder = true
                    )
                )
            }
        )
    }

    AboutDialog(
        show = state.shouldShowAboutDialog,
        onDismiss = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetAboutDialogVisibility(
                    show = false
                )
            )
        }
    )
}

fun fileChooserDialog(
    title: String?
): String {
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView())
    fileChooser.currentDirectory = File(System.getProperty("user.dir"))
    fileChooser.dialogTitle = title
    fileChooser.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
    fileChooser.isAcceptAllFileFilterUsed = true
    fileChooser.selectedFile = null
    fileChooser.currentDirectory = null
    val file = if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        fileChooser.selectedFile.toString()
    } else {

        ""

    }
    println(file)
    return file

}

@Composable
fun MainScreenHeaderBar(
    mainScreenViewModel: MainScreenViewModel,
    state: MainScreenState,
) {
    MainHeaderBar(
        shouldShowDropdown = state.shouldShowFileDropdownMenu,
        setDropdownVisibility = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = it
                )
            )
        },
        onOpenFile = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = false
                )
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.ShouldSelectFile(
                    shouldSelectFile = true
                )
            )
        },
        onQuickSave = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = false
                )
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.QuickSaveCurrentFile
            )
        },
        onSaveAs = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = false
                )
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.ShouldEnterFileName(
                    shouldSetFileName = true
                )
            )
        },
        onExportAsPdf = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = false
                )
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.ShouldEnterFileNameForPdf(
                    shouldSetFileName = true
                )
            )
        },
        onNewFile = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetFileDropdownMenuVisibility(
                    show = false
                )
            )
            mainScreenViewModel.onEvent(
                MainScreenEvent.CreateNewFile
            )
        },
        onMore = {
            mainScreenViewModel.onEvent(
                MainScreenEvent.SetAboutDialogVisibility(
                    show = true
                )
            )
        }
    )
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