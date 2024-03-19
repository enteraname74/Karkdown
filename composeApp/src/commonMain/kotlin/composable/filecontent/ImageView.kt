package composable.filecontent

import Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import strings.appStrings
import theme.KarkdownColorTheme
import java.io.File

@Composable
fun ImageView(
    currentText: String,
    imagePath: String,
    imageName: String,
    onClick: () -> Unit,
    onLineChanged: (String) -> Unit,
    onDone: () -> Unit,
    onKeyDown: () -> Unit,
    onKeyUp: () -> Unit,
    onDeleteLine: (Int) -> Unit,
    userPosition: Int,
    markdownElementPosition: Int,
) {
    var imageData by remember {
        mutableStateOf(fetchImageData(imagePath = imagePath))
    }

    LaunchedEffect(key1 = imagePath) {
        imageData = fetchImageData(imagePath = imagePath)
    }

    if (userPosition != markdownElementPosition) {

        val imageSubText =
            if (imageData != null) imageName else appStrings.couldNotLoadImageAtPath(imagePath = imagePath)

        Column(
            modifier = Modifier
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (imageData != null) {
                Image(
                    modifier = Modifier
                        .size(size = Constants.ImageSize.huge),
                    bitmap = imageData!!,
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(size = Constants.ImageSize.medium),
                    imageVector = Icons.Rounded.Error,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = KarkdownColorTheme.colorScheme.onSecondary)
                )
            }
            Text(
                text = imageSubText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Constants.FontStyle.small
            )
        }


    } else {
        TextView(
            text = currentText,
            viewText = currentText,
            shouldFocus = true,
            onClick = onClick,
            onChange = onLineChanged,
            onDone = onDone,
            onKeyUp = onKeyUp,
            onKeyDown = onKeyDown,
            onDeleteLine = {
                onDeleteLine(userPosition)
            }
        )
    }
}

/**
 * Fetch image data from its path, return null if the data couldn't be retrieved.
 */
private fun fetchImageData(imagePath: String): ImageBitmap? {
    return try {
        val file = File(imagePath)
        loadImageBitmap(file.inputStream())
    } catch (_: Exception) {
        null
    }
}