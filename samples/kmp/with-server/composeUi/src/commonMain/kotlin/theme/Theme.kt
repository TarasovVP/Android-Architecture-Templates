package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

private val DarkColorPalette = darkColorScheme(
    primary = Primary200,
    primaryContainer = Primary800,
    secondary = Neutral500,
    onBackground = White
)

private val LightColorPalette = lightColorScheme(
    primary = Primary500,
    primaryContainer = Primary400,
    secondary = Neutral200,
    onBackground = Neutral800
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
        typography = Typography(),
        content = {
            ProvideTextStyle(
                value = TextStyle(color = White),
                content = content
            )
        }
    )
}