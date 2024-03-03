package mk.netcetera.edu.zborle.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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

@Composable
fun ZborleWord() =
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    repeat(5) {
      ZborleTile(letter = "")
    }
  }


@Composable
private fun RowScope.ZborleTile(
  letter: String,
  textColor: Color = Color.White,
  textSize: TextUnit = 24.sp
) = Box(
  modifier = Modifier
    .weight(1f)
    .aspectRatio(1f)
    .padding(2.dp)
    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
    .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))
) {
  Text(
    modifier = Modifier.align(Alignment.Center),
    text = letter.uppercase(),
    fontSize = textSize,
    color = textColor,
    fontWeight = FontWeight.Bold
  )
}
