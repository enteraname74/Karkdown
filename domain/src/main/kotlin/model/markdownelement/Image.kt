package model.markdownelement

import model.textutils.imageName
import model.textutils.imagePath

/**
 * Represent an image information (name and path).
 */
class Image(rowData: String): MarkdownElement(rowData = rowData) {
    override val viewData: String = rowData
    val imageName: String = rowData.imageName()
    val imagePath: String = rowData.imagePath()
}