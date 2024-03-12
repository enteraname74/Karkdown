package strings

import androidx.compose.ui.text.intl.Locale

val appStrings = when(Locale.current.language) {
    "fr" -> FrStrings
    else -> EnStrings
}

interface AppStrings {
    val appName: String get() =  "Karkdown"

    val openFile: String
    val saveFile: String
    val saveAs: String
    val fileName: String
    val validate: String
    val cancel: String

    val newFile: String
    val fileSaved: String
    val fileCouldNotBeSaved: String

    fun saveFileNameIn(filename: String): String
}