package composable.filecontent

import Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.textutils.isURL
import strings.appStrings
import theme.KarkdownColorTheme
import utils.ErrorImageLoading
import utils.ImageLoading
import utils.LoadingState
import utils.SuccessImageLoading
import java.io.File
import java.net.URL

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
    var imageLoadingState by remember {
        mutableStateOf<LoadingState>(ImageLoading())
    }

    val density = LocalDensity.current

    LaunchedEffect(key1 = imagePath) {
        CoroutineScope(Dispatchers.IO).launch {
            imageLoadingState = ImageLoading(message = appStrings.loadingImageAtPath(imagePath = imagePath))
            val result = getImageDataFromGenericPath(imagePath = imagePath, density = density)

            imageLoadingState = if (result == null) ErrorImageLoading(message = appStrings.couldNotLoadImageAtPath(imagePath = imagePath))
            else SuccessImageLoading(data = result)
        }
    }

    if (userPosition != markdownElementPosition) {
        when(imageLoadingState) {
            is ErrorImageLoading -> ErrorView(
                message = (imageLoadingState as ErrorImageLoading).message,
                onClick = onClick
            )
            is ImageLoading -> LoadingView(
                message = (imageLoadingState as ImageLoading).message,
                onClick = onClick
            )
            is SuccessImageLoading -> LoadedImageView(
                painter = (imageLoadingState as SuccessImageLoading).data,
                imageName = imageName,
                onClick = onClick
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

@Composable
fun LoadedImageView(
    painter: Painter,
    imageName: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            modifier = Modifier
                .size(size = Constants.ImageSize.huge),
            painter = painter,
            contentDescription = null
        )
        Text(
            text = imageName,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = Constants.FontStyle.small
        )
    }
}

@Composable
fun LoadingView(
    message: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        CircularProgressIndicator(
            color = KarkdownColorTheme.colorScheme.onSecondary
        )
        Text(
            text = message,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = Constants.FontStyle.small
        )
    }
}

@Composable
fun ErrorView(
    message: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            modifier = Modifier
                .size(size = Constants.ImageSize.medium),
            imageVector = Icons.Rounded.Error,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = KarkdownColorTheme.colorScheme.onSecondary)
        )
        Text(
            text = message,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = Constants.FontStyle.small
        )
    }
}

/**
 * Retrieve an image data depending on the type of the given image path.
 * Returns null if the data couldn't be retrieved.
 */
private fun getImageDataFromGenericPath(imagePath: String, density: Density): Painter? {
    return if (imagePath.isURL()) imageFromUrl(url = imagePath, density = density)
    else imageFromFile(filePath = imagePath, density = density)
}

/**
 * Fetch image data from its file path, return null if the data couldn't be retrieved.
 */
private fun imageFromFile(filePath: String, density: Density): Painter? {
    return try {
        val file = File(filePath)
        file.inputStream().buffered().use { loadSvgPainter(it, density) }
    } catch (_: Exception) {
        null
    }
}

/**
 * Fetch image data from a URL, return null if the data couldn't be retrieved.
 */
private fun imageFromUrl(url: String, density: Density): Painter? {
    return try {
        URL(url).openStream().buffered().use { loadSvgPainter(it, density) }
    } catch (e: Exception) {
        null
    }
}