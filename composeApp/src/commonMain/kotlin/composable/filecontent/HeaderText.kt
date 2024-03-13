package composable.filecontent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import utils.buildCorrespondingHeaderTextStyle

@Composable
fun HeaderText(
    text: String,
    headerLevel: Int
) {
    Text(
        text = text,
        style = buildCorrespondingHeaderTextStyle(headerLevel),
    )
}