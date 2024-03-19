package utils

import androidx.compose.ui.graphics.painter.Painter

data class SuccessImageLoading(val data: Painter): LoadingState
data class ImageLoading(val message: String = ""): LoadingState
data class ErrorImageLoading(val message: String = ""): LoadingState


/**
 * Different states of a loading event.
 */
sealed interface LoadingState
