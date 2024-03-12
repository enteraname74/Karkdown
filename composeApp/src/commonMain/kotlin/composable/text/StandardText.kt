package composable.text

import Constants
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StandardText(
    text: String
) {
    Text(
        text = text,
        style = Constants.FontStyle.body
    )
}