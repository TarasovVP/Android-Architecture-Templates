package com.vnteam.architecturetemplates.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = Primary200,
    primaryContainer = Primary700,
    secondary = Neutral200
)

private val LightColorPalette = lightColorScheme(
    primary = Primary500,
    primaryContainer = Primary700,
    secondary = Neutral200
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val appColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = appColors,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}