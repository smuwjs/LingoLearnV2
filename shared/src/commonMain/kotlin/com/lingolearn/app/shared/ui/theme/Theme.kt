package com.lingolearn.app.shared.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = Gray900,
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    secondaryContainer = SecondaryTealLight,
    onSecondaryContainer = Gray900
)

@Composable
fun LingoLearnTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
