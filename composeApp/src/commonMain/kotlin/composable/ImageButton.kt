package composable

import Constants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import theme.KarkdownColorTheme

@Composable
fun ImageButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    name: String,
    containerColor: Color = KarkdownColorTheme.colorScheme.primaryContainer,
    contentColor: Color = KarkdownColorTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
        ) {
            Icon(
                imageVector = imageVector,
                tint = contentColor,
                contentDescription = null
            )
            Text(
                text = name,
                fontSize = 14.sp,
                color = contentColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}