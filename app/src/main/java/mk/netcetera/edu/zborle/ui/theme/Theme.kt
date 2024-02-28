package mk.netcetera.edu.zborle.ui.theme

import android.app.Activity

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
  primary = PrimaryGreen,
  secondary = DarkGreen
)

private val LightColorScheme = lightColorScheme(
  primary = PrimaryGreen,
  secondary = Black

  /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ZborleTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val buttonColors = if(darkTheme) ButtonDarkColorScheme else ButtonLightColorScheme

  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
      ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
    }
  }
  CompositionLocalProvider(
    LocalButtonColors provides buttonColors
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}

val ButtonLightColorScheme = ButtonColors(
  background = Color.Black,
  text = Color.White
)

val ButtonDarkColorScheme = ButtonColors(
  background = DarkGreen,
  text = Color.White
)

val LocalButtonColors = staticCompositionLocalOf { ButtonColors() }

object ZborleTheme {
  val buttonColors: ButtonColors
  @Composable
  get() = LocalButtonColors.current
}