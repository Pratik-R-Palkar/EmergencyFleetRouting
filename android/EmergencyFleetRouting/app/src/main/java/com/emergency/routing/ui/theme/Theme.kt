package com.emergency.routing.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    secondary = AmberAlert,
    tertiary = BlueVehicle,
)

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    secondary = AmberAlert,
    tertiary = BlueVehicle,
)

@Composable
fun EmergencyFleetRoutingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
