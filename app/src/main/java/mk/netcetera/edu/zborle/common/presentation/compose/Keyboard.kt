package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.home.presentation.LetterStatus
import mk.netcetera.edu.zborle.ui.theme.DarkGray
import mk.netcetera.edu.zborle.ui.theme.LightGray
import mk.netcetera.edu.zborle.ui.theme.White
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

@Composable
fun MacedonianKeyboard(
  letterInputState: List<Pair<String, LetterStatus>>,
  onEnterClicked: () -> Unit,
  onLetterEntered: (String) -> Unit,
  onBackspaceClicked: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    if (letterInputState.size < 31) return

    // Split the list into three parts
    val firstRow = letterInputState.subList(0, 13)
    val secondRow = letterInputState.subList(13, 24)
    val thirdRow = letterInputState.subList(24, 31)


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

        for (letterState in row) {
          LetterButton(letterState = letterState, onLetterSelected = onLetterEntered)
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
      .background(LightGray, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable { onEnterClicked() },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.padding(4.dp),
      text = "ENTER",
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      color = DarkGray,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun RowScope.LetterButton(
  letterState: Pair<String, LetterStatus>,
  onLetterSelected: (String) -> Unit
) {

  val (letter, letterStatus) = letterState

  //TODO extract repeating  color logic
  val textColor = if (letterStatus == LetterStatus.DEFAULT) DarkGray else White
  val backgroundColor = when (letterStatus) {
    LetterStatus.CORRECT -> ZborleTheme.statusColors.correctBackground
    LetterStatus.PARTIALLY_CORRECT -> ZborleTheme.statusColors.partiallyCorrectBackground
    LetterStatus.INCORRECT -> ZborleTheme.statusColors.incorrectBackground
    LetterStatus.DEFAULT -> ZborleTheme.statusColors.defaultKeyboardBackground
  }

  Box(
    modifier = Modifier
      .weight(1f)
      .height(56.dp)
      .background(backgroundColor, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = true, radius = 6.dp, color = DarkGray),
        role = Role.Button
      ) { onLetterSelected(letter) },
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.padding(2.dp),
      text = letter,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      color = textColor,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun BackspaceButton(onBackspaceClicked: () -> Unit) {
  Box(
    modifier = Modifier
      .height(56.dp)
      .background(LightGray, RoundedCornerShape(6.dp))
      .clip(RoundedCornerShape(6.dp))
      .clickable { onBackspaceClicked() },
    contentAlignment = Alignment.Center
  ) {
    Icon(
      modifier = Modifier.padding(8.dp),
      painter = painterResource(id = R.drawable.remove_keyboard),
      contentDescription = null,
      tint = DarkGray
    )
  }
}