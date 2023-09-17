package com.example.pokemon_detail.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFa5c8ff),
    secondary = Color(0xFFbcc7dc),
    background = Color(0xFF1a1c1e),
    onBackground = Color(0xFFe3e2e6),
    surface = Color(0xFF4A4B57),
    onSurface = Color.White,
    error = Color(0xFFffb4ab),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9DB7E7),
    secondary = Color(0xFF8691a4),
    background = Color(0xFF545f71),
    onBackground = Color(0xFF1a1c1e),
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFA55B52),
)

@Composable
fun PokemonAppCustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}