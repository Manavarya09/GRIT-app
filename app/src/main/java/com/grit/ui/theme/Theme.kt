package com.grit.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GritBlack,
    onPrimary = GritWhite,
    secondary = GritYellow,
    onSecondary = GritBlack,
    tertiary = GritRed,
    onTertiary = GritWhite,
    background = GritBackground,
    onBackground = GritOnBackground,
    surface = GritSurface,
    onSurface = GritOnSurface,
    surfaceVariant = GritYellow,
    onSurfaceVariant = GritBlack,
    outline = GritBlack
)

@Composable
fun GritTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = GritWhite.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
