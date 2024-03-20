package composable

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import strings.appStrings
import theme.KarkdownColorTheme

@Composable
fun AboutDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
   if (show) {
       Dialog(
           onDismissRequest = onDismiss
       ) {
           Column(
               modifier = Modifier
                   .background(
                       color = KarkdownColorTheme.colorScheme.primary,
                       shape = RoundedCornerShape(Constants.Spacing.small)
                   )
                   .padding(Constants.Spacing.medium)
           ) {
               Row(
                   modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.End
               ) {
                   Icon(
                       modifier = Modifier
                           .size(24.dp)
                           .background(
                               color = KarkdownColorTheme.colorScheme.primaryContainer,
                               shape = CircleShape
                           )
                           .clickable {
                               onDismiss()
                           }
                           .padding(Constants.Spacing.small),
                       imageVector = Icons.Rounded.Close,
                       tint = KarkdownColorTheme.colorScheme.onPrimary,
                       contentDescription = null
                   )
               }
               InnerContent()
           }
       }
   }
}

@Composable
private fun InnerContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Constants.Spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = appStrings.appName,
            style = Constants.FontStyle.h1
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Version: ${appStrings.appVersion}",
            style = Constants.FontStyle.body
        )
    }
}