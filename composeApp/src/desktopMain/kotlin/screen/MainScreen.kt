package screen

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FolderOpen
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import composable.*
import event.MainScreenEvent
import kotlinx.coroutines.launch
import state.MainScreenState
import strings.appStrings
import theme.KarkdownColorTheme
import viewmodel.MainScreenViewModel
import java.awt.FileDialog
import java.io.File
import java.io.FilenameFilter
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileSystemView
import kotlin.io.path.Path

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    window: ComposeWindow
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
                    } else if (event.key == Key.O) {
                        val filePath = fileChooser(window = window)
                        filePath?.let { path ->
                            mainScreenViewModel.onEvent(
                                MainScreenEvent.OpenFile(
                                    filepath = path
                                )
                            )
                        }
                    } else if (event.isShiftPressed && event.key == Key.S) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.ShouldEnterFileName(
                                shouldSetFileName = true
                            )
                        )

                    } else if (event.key == Key.S) {
                        mainScreenViewModel.onEvent(
                            MainScreenEvent.QuickSaveCurrentFile
                        )
                    } else if (event.isShiftPressed && event.key == Key.P) {
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
                state = state,
                window = window
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
                            name = appStrings.newFilename
                        )
                        ImageButton(
                            onClick = {
                                val filePath = fileChooser(window = window)
                                filePath?.let { path ->
                                    mainScreenViewModel.onEvent(
                                        MainScreenEvent.OpenFile(
                                            filepath = path
                                        )
                                    )
                                }
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
                val folder = directoryChooser(
                    title = appStrings.saveFileNameIn(filename = state.filename),
                    initialDirectory = state.filepath?.toString() ?: System.getProperty("user.home")
                )
                folder?.let {
                    mainScreenViewModel.onEvent(
                        MainScreenEvent.SaveAsCurrentFile(
                            path = folder,
                            filename = filename
                        )
                    )
                }
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
                val folder = directoryChooser(
                    title = appStrings.saveFileNameIn(filename = state.filename),
                    initialDirectory = state.filepath?.toString() ?: System.getProperty("user.home")
                )
                folder?.let {
                    mainScreenViewModel.onEvent(
                        MainScreenEvent.ExportAsPdf(path = it)
                    )
                }
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

/**
 * Open a file.
 */
fun fileChooser(
    window: ComposeWindow
): String? {
    val fileDialog = FileDialog(window, appStrings.openFile, FileDialog.LOAD).apply {
        directory = System.getProperty("user.home")
        setFilenameFilter { _, name -> name.endsWith(".md") }
        isVisible = true
    }

    val file: String? = fileDialog.file
    return if (file != null) Path(base = fileDialog.directory, file).toString() else null
}

/**
 * Open a folder.
 */
fun directoryChooser(
    title: String?,
    initialDirectory: String
): String? {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory).apply {
        dialogTitle = title
        currentDirectory = File(initialDirectory)
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        isAcceptAllFileFilterUsed = true
        selectedFile = null
        currentDirectory = null
    }

    return if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        fileChooser.selectedFile.toString()
    } else {
        null
    }
}

@Composable
fun MainScreenHeaderBar(
    mainScreenViewModel: MainScreenViewModel,
    state: MainScreenState,
    window: ComposeWindow
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
            val filePath = fileChooser(window = window)
            filePath?.let { path ->
                mainScreenViewModel.onEvent(
                    MainScreenEvent.OpenFile(
                        filepath = path
                    )
                )
            }
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
            },
            filePath = state.filepath
        )
    }
}