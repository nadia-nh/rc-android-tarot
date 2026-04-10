package com.example.simpletarot.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = MysticPurple,
    onPrimary = PureWhite,
    primaryContainer = MysticPurple,
    onPrimaryContainer = PureWhite,

    secondary = MossGreen,
    onSecondary = PureWhite,
    secondaryContainer = LightSurface,
    onSecondaryContainer = LightOnSurface,

    background = LightBackground,
    onBackground = LightOnSurface,

    surface = PureWhite,
    onSurface = LightOnSurface,

    surfaceVariant = LightSurface,
    onSurfaceVariant = MysticPurple,

    outlineVariant = Color(0xFFE0D9C8)
)

private val DarkColorScheme = darkColorScheme(
    primary = LavenderText,
    onPrimary = DarkBackground,
    secondary = MossGreen,
    onSecondary = PureWhite,

    background = DarkBackground,
    onBackground = LavenderText,

    surface = MidnightBlack,
    onSurface = LavenderText,

    surfaceVariant = DarkSurface,
    onSurfaceVariant = PureWhite,

    outline = Color.White.copy(alpha = 0.3f),
    outlineVariant = Color.White.copy(alpha = 0.1f)
)

@Composable
fun SimpleTarotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalSpacing provides TarotSpacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = TarotTypography,
            shapes = TarotShapes,
            content = content
        )
    }
}