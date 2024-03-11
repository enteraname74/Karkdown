package composable.text

import Constants
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun Header(
    text: String,
    headerLevel: Int
) {
    Text(
        text = text,
        style = buildCorrespondingTextStyle(headerLevel)
    )
}

private fun buildCorrespondingTextStyle(
    headerLevel: Int
): TextStyle {
    return with(Constants.FontSize) {
        when(headerLevel) {
            1 -> h1
            2 -> h2
            3 -> h3
            4 -> h4
            5 -> h5
            else -> h6
        }
    }

}