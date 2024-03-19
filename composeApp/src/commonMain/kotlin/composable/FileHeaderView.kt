package composable

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import theme.KarkdownColorTheme
import utils.FileHeader

@Composable
fun FileHeaderView(
    header: FileHeader,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = KarkdownColorTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Constants.Spacing.small)
                )
                .clickable {
                    onClick()
                }
                .padding(
                    top = Constants.Spacing.medium,
                    start = Constants.Spacing.medium,
                    end = Constants.Spacing.medium,
                    bottom = Constants.Spacing.small
                ),
            horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!header.isDataUpdated) {
                Icon(
                    imageVector = Icons.Rounded.FiberManualRecord,
                    contentDescription = null,
                    modifier = Modifier.size(Constants.ImageSize.small),
                    tint = KarkdownColorTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = header.fileName,
                style = Constants.FontStyle.small,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (header.isSelected) {
            Divider(
                modifier = Modifier
                    .height(Constants.Spacing.small)
                    .fillMaxWidth(),
                color = KarkdownColorTheme.colorScheme.accent
            )
        }
    }
}