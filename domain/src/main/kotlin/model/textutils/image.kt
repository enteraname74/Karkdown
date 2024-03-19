package model.textutils

/**
 * Check if a string is an image link.
 */
fun String.isImage(): Boolean {
    val regex = Regex("""!\[.*?]\(.*?\)""")
    return regex.matches(this)
}

/**
 * Retrieve the name of an image.
 */
fun String.imageName(): String {
    val regex = Regex("""!\[(.*)]\((.*?)\)""")
    return regex.find(this)?.destructured?.toList()?.get(0) ?: this
}

/**
 * Retrieve the path of an image.
 */
fun String.imagePath(): String {
    val regex = Regex("""!\[.*]\((.*?)\)""")
    return regex.find(this)?.destructured?.toList()?.get(0) ?: this
}