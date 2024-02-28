package mk.netcetera.edu.zborle.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.ui.theme.DarkGreen
import mk.netcetera.edu.zborle.ui.theme.PrimaryGreen

@Composable
fun ZborleTile(
  modifier: Modifier? = null,
  letter: String,
  textColor: Color = Color.White,
  textSize: TextUnit = 24.sp
) {
    Box(
      modifier = modifier ?: Modifier
        .size(45.dp)
        .padding(2.dp)
        .background(color = PrimaryGreen, shape = RoundedCornerShape(4.dp))
    ) {
      Text(
        modifier = Modifier.align(Alignment.Center),
        text = letter.uppercase(),
        fontSize = textSize,
        color = textColor,
        fontWeight = FontWeight.Bold
      )
    }
}