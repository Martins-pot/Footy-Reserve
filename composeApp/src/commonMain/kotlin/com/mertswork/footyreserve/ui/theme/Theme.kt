package com.mertswork.footyreserve.ui.theme



import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mertswork.footyreserve.core.presentation.BackgroundBlue
import com.mertswork.footyreserve.core.presentation.SecondaryBlue


private val LightColorScheme = lightColorScheme(
    primary = BackgroundBlue,         // Your new primary color
    onPrimary = Color.White,
    secondary = SecondaryBlue,
    onSecondary = Color.White,
    background = BackgroundBlue,
    onBackground = Color.White,
    surface = BackgroundBlue,
    onSurface = Color.White,
)

private val DarkColorScheme = darkColorScheme(
    primary = BackgroundBlue,         // Your new primary color
    onPrimary = Color.White,
    secondary = SecondaryBlue,
    onSecondary = Color.White,
    background = BackgroundBlue,
    onBackground = Color.White,
    surface = BackgroundBlue,
    onSurface = Color.White,
)


@Composable
fun FootyReserveTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
