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
 * Check if a given string is an URL.
 */
fun String.isURL(): Boolean {
    val regex = Regex("""(http|https)://.+""")
    return regex.matches(this)
}

/**
 * Retrieve the path of an image.
 */
fun String.imagePath(): String {
    val regex = Regex("""!\[.*]\((.*?)\)""")
    return regex.find(this)?.destructured?.toList()?.get(0) ?: this
}