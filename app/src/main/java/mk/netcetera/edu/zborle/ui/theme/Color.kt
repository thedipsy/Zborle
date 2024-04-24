package mk.netcetera.edu.zborle.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val LightGreen = Color(0xFF5ec16a)
val DarkGreen = Color(0xFF438A4B)

val Orange = Color(0xFFe2b43f)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray = Color(0xFF888888)
val LightGray = Color(0xFFCCCCCC)
val DarkGray = Color(0xFF444444)

val PrimaryGreen = LightGreen
val SecondaryGreen = DarkGreen

@Immutable
data class ButtonColors(
  val background: Color = Color.Unspecified,
  val text: Color = Color.Unspecified
)

@Immutable
data class StatusColors(
  val correctBackground: Color = Color.Unspecified,
  val partiallyCorrectBackground: Color = Color.Unspecified,
  val incorrectBackground: Color = Color.Unspecified,
  val defaultBackground: Color = Color.Unspecified,
  val defaultKeyboardBackground: Color = Color.Unspecified
)