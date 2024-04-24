package mk.netcetera.edu.zborle.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
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
  val buttonColors = if (darkTheme) ButtonDarkColorScheme else ButtonLightColorScheme
  val statusColors =
    if (darkTheme) StatusColorsDarkColorScheme else StatusColorsLightColorScheme

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
    LocalButtonColors provides buttonColors,
    LocalStatusColors provides statusColors
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}

val ButtonLightColorScheme = ButtonColors(
  background = Black,
  text = White
)

val ButtonDarkColorScheme = ButtonColors(
  background = DarkGreen,
  text = White
)

val StatusColorsLightColorScheme = StatusColors(
  correctBackground = LightGreen,
  partiallyCorrectBackground = Orange,
  incorrectBackground = Gray,
  defaultBackground = White,
  defaultKeyboardBackground = LightGray
)

val StatusColorsDarkColorScheme = StatusColors(
  correctBackground = LightGreen,
  partiallyCorrectBackground = Orange,
  incorrectBackground = Gray,
  defaultBackground = White,
  defaultKeyboardBackground = LightGray
)

val LocalButtonColors = staticCompositionLocalOf { ButtonColors() }
val LocalStatusColors = staticCompositionLocalOf { StatusColors() }

object ZborleTheme {
  val buttonColors: ButtonColors
    @Composable
    get() = LocalButtonColors.current

  val statusColors: StatusColors
    @Composable
    get() = LocalStatusColors.current
}