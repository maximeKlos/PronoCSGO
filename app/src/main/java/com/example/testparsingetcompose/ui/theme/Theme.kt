package com.example.testparsingetcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = CSBlue,
    primaryVariant = CSLightBlue,
    secondary = CSOrange,
    background = Color.White,
    onPrimary = CSBlue,
    onBackground = Color.Black
)

private val LightColorPalette = lightColors(
    primary = CSBlue,
    primaryVariant = CSLightBlue,
    secondary = CSOrange,
    background = Color.White,
    onPrimary = CSBlue,
    onBackground = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TestParsingEtComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}