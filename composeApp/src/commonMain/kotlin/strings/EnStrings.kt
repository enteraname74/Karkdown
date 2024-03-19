package strings

/**
 * Implementation of the AppStrings for the English (default) translation.
 */
data object EnStrings: AppStrings {
    override val file: String = "File"

    override val openFile: String = "Open file"
    override val saveFile: String = "Save"
    override val saveAs: String = "Save as..."
    override val exportAsPdf: String = "Export as PDF"

    override val fileName: String = "File's name"
    override val validate: String = "Validate"
    override val cancel: String = "Cancel"

    override val newFilename: String = "New file"
    override val fileSaved: String = "File saved"
    override val fileCouldNotBeSaved: String = "File couldn't be saved"

    override fun saveFileNameIn(filename: String): String = "Save $filename in..."

    override fun couldNotLoadImageAtPath(imagePath: String): String = "Could not load image from path: $imagePath"
}