package com.newsapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── Brand Colors ─────────────────────────────────────────────────────────────

val NewsRed = Color(0xFFE53935)
val NewsRedDark = Color(0xFFB71C1C)
val NewsSurface = Color(0xFFF8F9FA)
val NewsSurfaceDark = Color(0xFF121212)
val NewsCard = Color(0xFFFFFFFF)
val NewsCardDark = Color(0xFF1E1E1E)
val NewsOnSurface = Color(0xFF1A1A2E)
val NewsOnSurfaceDark = Color(0xFFECECEC)

// ─── Color Schemes ────────────────────────────────────────────────────────────

private val LightColorScheme = lightColorScheme(
    primary = NewsRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6),
    onPrimaryContainer = Color(0xFF410002),
    secondary = Color(0xFF775652),
    onSecondary = Color.White,
    background = NewsSurface,
    onBackground = NewsOnSurface,
    surface = NewsCard,
    onSurface = NewsOnSurface,
    surfaceVariant = Color(0xFFF3DDDB),
    outline = Color(0xFFD0C5C4),
    error = Color(0xFFBA1A1A)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB4AB),
    onPrimary = Color(0xFF690005),
    primaryContainer = NewsRedDark,
    onPrimaryContainer = Color(0xFFFFDAD6),
    secondary = Color(0xFFE7BDBA),
    onSecondary = Color(0xFF442927),
    background = NewsSurfaceDark,
    onBackground = NewsOnSurfaceDark,
    surface = NewsCardDark,
    onSurface = NewsOnSurfaceDark,
    surfaceVariant = Color(0xFF534341),
    outline = Color(0xFF9C8C8A),
    error = Color(0xFFFFB4AB)
)

// ─── Typography ───────────────────────────────────────────────────────────────

val NewsTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 57.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    )
)

// ─── Theme Entry Point ────────────────────────────────────────────────────────

@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NewsTypography,
        content = content
    )
}
