package me.arunsharma.devupdates.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = LightThemeColor.primary,
    primaryVariant = LightThemeColor.primary,
    onPrimary = Color.White,
    secondary = Color.White,
    secondaryVariant = Color.White,
    error = Color.White,
)

private val DarkThemeColors = darkColors(
    primary = DarkThemeColor.primary,
    primaryVariant = DarkThemeColor.primary,
    error = Color.White,
    onPrimary = DarkThemeColor.colorOnPrimary,
    surface = DarkThemeColor.colorSurface,
    background = DarkThemeColor.colorSurface
)

@Composable
fun DevUpdatesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = AppTypography,
        shapes = JetnewsShapes,
        content = content
    )
}
