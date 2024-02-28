package mk.netcetera.edu.zborle.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF5ec16a)
val SecondaryGreen = Color(0xFFF0FCEE)
val Black = Color(0xFF000000)

val DarkGreen = Color(0xFF438A4B)


@Immutable
data class ButtonColors(
  val background: Color = Color.Unspecified,
  val text: Color = Color.Unspecified
)