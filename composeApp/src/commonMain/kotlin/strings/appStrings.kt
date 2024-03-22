package strings

import androidx.compose.ui.text.intl.Locale

val appStrings = when(Locale.current.language) {
    "fr" -> FrStrings
    else -> EnStrings
}

interface AppStrings {
    val appName: String get() =  "Karkdown"
    val appVersion: String get() = "1.0.0"

    val unsavedChangesDialogTitle: String
    val unsavedChangesDialogText: String

    val file: String
    val noFileOpen: String
    val newFilename: String

    val openFile: String
    val saveFile: String
    val saveAs: String
    val exportAsPdf: String

    val fileName: String
    val validate: String
    val cancel: String

    val fileSaved: String
    val fileCouldNotBeSaved: String

    /**
     * Indication to save a named file.
     */
    fun saveFileNameIn(filename: String): String

    /**
     * Indication that an image could is being loaded from a given path.
     */
    fun loadingImageAtPath(imagePath: String): String

    /**
     * Indication that an image could not be loaded from a given path.
     */
    fun couldNotLoadImageAtPath(imagePath: String): String
}