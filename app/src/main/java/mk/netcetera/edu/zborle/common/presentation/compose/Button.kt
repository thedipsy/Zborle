package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

@Composable
fun PrimaryButton(
  @StringRes textId: Int,
  isLoading: Boolean,
  onClick: () -> Unit
) = Button(
  modifier = Modifier
    .size(height = 60.dp, width = 200.dp)
    .padding(top = 12.dp, bottom = 6.dp),
  colors = ButtonDefaults.buttonColors(containerColor = ZborleTheme.buttonColors.background),
  onClick = onClick
) {
  if (isLoading) {
    CircularProgressIndicator(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f),
      strokeWidth = 2.dp,
      strokeCap = StrokeCap.Round
    )
  } else {
    Text(
      text = stringResource(id = textId),
      color = ZborleTheme.buttonColors.text,
      fontSize = 18.sp
    )
  }
}


@Composable
fun SecondaryButton(@StringRes textId: Int, onClick: () -> Unit) =
  Text(
    modifier = Modifier
      .padding(vertical = 6.dp)
      .clickable { onClick() },
    text = stringResource(id = textId),
    fontSize = 14.sp,
    textDecoration = TextDecoration.Underline
  )