import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Constants used throughout the application.
 */
object Constants {

    /**
     * Spacing values for spacing or simple size.
     */
    object Spacing {
        val small: Dp = 4.dp
        val medium: Dp = 8.dp
        val large: Dp = 16.dp
        val veryLarge: Dp = 24.dp
    }

    /**
     * Define the sizes of images in the application.
     */
    object ImageSize {
        val small: Dp = 16.dp
        val medium: Dp = 32.dp
        val large: Dp = 64.dp
        val veryLarge: Dp = 128.dp
        val huge: Dp = 160.dp
    }

    object FontSize {
        val h1: TextStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight(1000)
        )
        val h2: TextStyle = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight(900)
        )
        val h3: TextStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(800)
        )
        val h4: TextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(700)
        )
        val h5: TextStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
        val h6: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(500)
        )
    }

    /**
     * Define the durations of animations
     */
    object AnimationDuration {
        const val short: Int = 100
        const val normal: Int = 300
    }
}