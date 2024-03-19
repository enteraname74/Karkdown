package composable

import Constants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import theme.KarkdownColorTheme
import utils.FileHeader

@Composable
fun FileHeaders(
    headers: List<FileHeader>,
    onHeaderClicked: (headerPos: Int) -> Unit,
    onCreateFile: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            modifier = Modifier.weight(1f)
        ) {
            items(count = headers.size) { headerPos ->
                FileHeaderView(
                    header = headers[headerPos],
                    onClick = {
                        onHeaderClicked(headerPos)
                    }
                )
            }
        }
        Icon(
            modifier = Modifier
                .padding(horizontal = Constants.Spacing.medium)
                .size(Constants.ImageSize.medium)
                .clickable {
                    onCreateFile()
                },
            imageVector = Icons.Rounded.Add,
            contentDescription = "",
            tint = KarkdownColorTheme.colorScheme.onPrimary
        )
    }
}