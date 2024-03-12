package composable

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import theme.KarkdownColorTheme

@Composable
fun FileTabHeader(
    filename: String,
    isDataUpdated: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .background(
                color = KarkdownColorTheme.colorScheme.primary,
                shape = RoundedCornerShape(Constants.Spacing.small)
            )
            .clickable {
                onClick()
            }
            .padding(Constants.Spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isDataUpdated) {
            Icon(
                imageVector = Icons.Rounded.FiberManualRecord,
                contentDescription = null,
                modifier = Modifier.size(Constants.ImageSize.small),
                tint = KarkdownColorTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = filename,
            style = Constants.FontStyle.small,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}