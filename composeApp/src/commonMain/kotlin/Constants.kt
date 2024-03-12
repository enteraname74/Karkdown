import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.KarkdownColorTheme

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

    /**
     * Define the styles for texts.
     */
    object FontStyle {
        val h1 = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val h2 = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val h3 = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val h4 = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val h5 = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val h6 = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )

        val body = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = KarkdownColorTheme.colorScheme.onPrimary
        )
        val small = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.W300,
            color = KarkdownColorTheme.colorScheme.onPrimary
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