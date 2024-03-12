package composable

import Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.rounded.FolderOpen
import androidx.compose.material3.Card
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
            Image(
                imageVector = Icons.Default.FolderOpen,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        openFile()
                    }
            )
        }
    }
}