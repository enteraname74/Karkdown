package composable

import Constants
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainHeaderBar(
    openFile: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(all = Constants.Spacing.small)
            .fillMaxWidth()
    ) {
        Row {
            Button(
                onClick = {
                    openFile()
                }
            ) {
                Text("open file")
            }
        }
    }
}