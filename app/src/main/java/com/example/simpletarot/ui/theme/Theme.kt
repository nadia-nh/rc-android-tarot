package com.example.simpletarot.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = RoyalViolet,
    secondary = GoldAccent,
    background = ParchmentWhite,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = DeepInk,
    onSurface = DeepInk,
)

private val DarkColorScheme = darkColorScheme(
    primary = MysticPurple,
    secondary = GoldAccent,
    background = DeepNavy,
    surface = CardBackGray,
    onPrimary = Color.White,
    onBackground = OffWhite,
    onSurface = OffWhite,
)

private val TarotTypography = Typography(
    // Large Titles (Menu Header)
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 2.sp
    ),
    // Card Names
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    // Descriptions / Meanings
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )
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