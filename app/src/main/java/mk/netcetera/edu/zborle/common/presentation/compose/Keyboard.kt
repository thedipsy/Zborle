package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R

// Define Macedonian alphabet
val macedonianAlphabet = listOf(
  "Љ", "Њ", "Е", "Р", "Т", "S", "У", "И", "О", "П", "Ш", "Ѓ", "Ж",
  "А", "С", "Д", "Ф", "Г", "Х", "Ј", "К", "Л", "Ч", "Ќ",
  "З", "Џ", "Ц", "В", "Б", "Н", "М"
)

@Composable
fun MacedonianKeyboard(
  onEnterClicked: () -> Unit,
  onLetterSelected: (String) -> Unit,
  onBackspaceClicked: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    // Split the list into three parts
    val firstRow = macedonianAlphabet.subList(0, 13)
    val secondRow = macedonianAlphabet.subList(13, 24)
    val thirdRow = macedonianAlphabet.subList(24, 31)

    listOf(firstRow, secondRow, thirdRow).forEachIndexed { index, row ->
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
      ) {
        if (index == 2) {
          EnterButton(onEnterClicked)
        }

        for (letter in row) {
          LetterButton(letter = letter, onLetterSelected = onLetterSelected)
        }

        if (index == 2) {
          BackspaceButton(onBackspaceClicked)
        }
      }
    }
  }
}

@Composable
private fun EnterButton(onEnterClicked: () -> Unit) {
  Box(
    modifier = Modifier
      .height(56.dp)
      .background(Color.LightGray, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable { onEnterClicked() },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.padding(4.dp),
      text = "ENTER",
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      color = Color.DarkGray,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun RowScope.LetterButton(letter: String, onLetterSelected: (String) -> Unit) {
  Box(
    modifier = Modifier
      .weight(1f)
      .height(56.dp)
      .background(Color.LightGray, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable { onLetterSelected(letter) },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.padding(2.dp),
      text = letter,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      color = Color.DarkGray,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun BackspaceButton(onBackspaceClicked: () -> Unit) {
  Box(
    modifier = Modifier
      .height(56.dp)
      .background(Color.LightGray, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable { onBackspaceClicked() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.padding(8.dp),
      painter = painterResource(id = R.drawable.remove_keyboard),
      contentDescription = null,
      tint = Color.DarkGray
    )
  }
}