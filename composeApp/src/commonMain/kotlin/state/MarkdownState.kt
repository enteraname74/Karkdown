package state

import com.github.enteraname74.karkdowncore.markdownelement.MarkdownElement

data class MarkdownState(
    val filePos: Int = -1,
    val fileContent: List<MarkdownElement> = emptyList(),
)