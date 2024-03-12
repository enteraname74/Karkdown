package composable

import Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainHeaderBar(
    openFile: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(Constants.Spacing.small)
            .fillMaxWidth()
            .background(
                color = CardDefaults.cardColors().containerColor,
                shape = RoundedCornerShape(Constants.Spacing.medium)
            )
            .padding(Constants.Spacing.medium)
    ) {
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