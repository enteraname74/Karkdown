package composable

import Constants
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import karkdown.composeapp.generated.resources.Res
import karkdown.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import strings.appStrings
import theme.KarkdownColorTheme
import java.awt.Desktop
import java.net.URI

@Composable
fun AboutDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    var showContributors by remember { mutableStateOf(false) }

    SideEffect {
        showContributors = false
    }

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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showContributors) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    showContributors = false
                                },
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = KarkdownColorTheme.colorScheme.onPrimary
                        )
                    } else {
                        Spacer(modifier = Modifier)
                    }
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
                if (showContributors) {
                    Contributors()
                } else {
                    InnerContent(
                        onContributors = {
                            showContributors = true
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun InnerContent(
    onContributors: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(Constants.Spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
    ) {
        Image(
            modifier = Modifier.size(Constants.ImageSize.veryLarge),
            painter = painterResource(Res.drawable.icon),
            contentDescription = "",
        )
        Column(
            modifier = Modifier
                .padding(
                    bottom = Constants.Spacing.medium
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = appStrings.appName,
                style = Constants.FontStyle.h1
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Version: ${appStrings.appVersion}",
                style = Constants.FontStyle.body,
                color = KarkdownColorTheme.colorScheme.subText
            )
        }

        AboutCard(
            text = appStrings.projectWebSite,
            icon = Icons.Rounded.OpenInBrowser,
            onClick = {
                openUrl("https://github.com/enteraname74/Karkdown")
            }
        )
        AboutCard(
            text = appStrings.contributors,
            icon = Icons.Rounded.Groups,
            onClick = {
                onContributors()
            }
        )
    }
}

/**
 * Open the project website in the default web browser.
 */
private fun openUrl(url: String) {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
}

@Composable
private fun AboutCard(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KarkdownColorTheme.colorScheme.secondary,
                shape = RoundedCornerShape(Constants.Spacing.medium)
            )
            .clickable {
                onClick()
            }
            .padding(Constants.Spacing.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Constants.FontStyle.body
        )
        Icon(
            imageVector = icon,
            tint = KarkdownColorTheme.colorScheme.onPrimary,
            contentDescription = null
        )
    }
}

@Composable
private fun Contributors() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(Constants.Spacing.medium),
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(
                    bottom = Constants.Spacing.medium
                ),
            text = appStrings.contributors,
            style = Constants.FontStyle.h2
        )
        ContributorRow(
            name = "Noah Penin",
            url = "https://github.com/enteraname74",
            function = "${appStrings.leadDev}, ${appStrings.designer}"
        )
        ContributorRow(
            name = "MaxBuster",
            url = "https://github.com/MaxBuster380",
            function = appStrings.designer
        )
    }
}

@Composable()
private fun ContributorRow(
    name: String,
    function: String,
    url: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                openUrl(url = url)
            }
            .padding(Constants.Spacing.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small)
        ) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = name,
                style = Constants.FontStyle.body
            )
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = function,
                style = Constants.FontStyle.small,
                color = KarkdownColorTheme.colorScheme.subText
            )
        }
        Icon(
            imageVector = Icons.Rounded.OpenInBrowser,
            tint = KarkdownColorTheme.colorScheme.onPrimary,
            contentDescription = null
        )
    }
}