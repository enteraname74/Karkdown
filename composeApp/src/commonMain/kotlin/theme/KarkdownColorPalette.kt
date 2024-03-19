package theme

import androidx.compose.ui.graphics.Color

/**
 * Color palettte for a Karkdown application.
 */
data class KarkdownColorPalette(
    val primary: Color = primaryColorDark,
    val secondary: Color = secondaryColorDark,
    val primaryContainer: Color = primaryContainerColor,
    val secondaryContainer: Color = secondaryContainerColor,
    val onPrimary: Color = textColorDark,
    val onSecondary: Color = textColorDark,
    val subText: Color = subTextColorDark,
    val accent: Color = accentColor
)
