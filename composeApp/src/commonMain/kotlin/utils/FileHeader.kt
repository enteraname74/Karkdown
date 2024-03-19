package utils

/**
 * Information for a file header.
 */
data class FileHeader(
    val isDataUpdated: Boolean,
    val isSelected: Boolean,
    val fileName: String
)