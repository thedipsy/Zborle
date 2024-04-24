package mk.netcetera.edu.zborle.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.compose.MacedonianKeyboard
import mk.netcetera.edu.zborle.common.presentation.compose.ZborleWord
import mk.netcetera.edu.zborle.home.presentation.compose.HowToPlayDialog
import mk.netcetera.edu.zborle.home.presentation.compose.PlayerStatisticsDialog

@Composable
fun ZborleScreen(
  viewState: ZborleState,
  onLetterEntered: (String) -> Unit,
  onEnterClicked: () -> Unit,
  onBackspaceClicked: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    val showHowToPlayDialog = remember { mutableStateOf(false) }
    val showStatisticsDialog = remember { mutableStateOf(false) }

    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween,

      ) {
      ZborleHeader(
        onHowToIconClick = { showHowToPlayDialog.value = true },
        onStatisticsIconClick = { showStatisticsDialog.value = true }
      )

      ZborleInputs(viewState.wordAttempts)
      MacedonianKeyboard(
        letterInputState = viewState.letterInputState,
        onEnterClicked = onEnterClicked,
        onLetterEntered = onLetterEntered,
        onBackspaceClicked = onBackspaceClicked
      )
    }

    if (showHowToPlayDialog.value) {
      HowToPlayDialog(viewState.wordExamples) { showHowToPlayDialog.value = false }
    }
    if (showStatisticsDialog.value) {
      PlayerStatisticsDialog { showStatisticsDialog.value = false }
    }
  }

}

@Composable
private fun ZborleHeader(onHowToIconClick: () -> Unit, onStatisticsIconClick: () -> Unit) =
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 32.dp)
  ) {
    Icon(
      modifier = Modifier
        .padding(16.dp)
        .size(28.dp)
        .clip(RoundedCornerShape(2.dp))
        .clickable { onHowToIconClick() }
        .align(Alignment.CenterStart),
      painter = painterResource(id = R.drawable.question_mark),
      contentDescription = null,
      tint = Color.Unspecified
    )

    Text(
      modifier = Modifier
        .padding(8.dp)
        .align(Alignment.Center),
      text = stringResource(id = R.string.zborle_title).uppercase(),
      fontSize = 36.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center
    )

    Icon(
      modifier = Modifier
        .padding(16.dp)
        .size(28.dp)
        .clip(RoundedCornerShape(2.dp))
        .clickable { onStatisticsIconClick() }
        .align(Alignment.CenterEnd),
      painter = painterResource(id = R.drawable.statistics),
      contentDescription = null,
      tint = Color.Unspecified
    )
  }

@Composable
private fun ZborleInputs(wordAttempts: WordAttempts) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceAround
  ) {
    repeat(6) {
      ZborleWord(wordAttempts[it])
    }
  }
}





