package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mk.netcetera.edu.zborle.ui.theme.Black
import mk.netcetera.edu.zborle.ui.theme.White

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ZborleDialog(
  @StringRes titleId: Int,
  onDismissRequest: () -> Unit,
  content: @Composable ColumnScope.() -> Unit
) = Dialog(
    onDismissRequest = { onDismissRequest() },
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      modifier = Modifier
        .padding(24.dp)
        .wrapContentSize(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = White)
    ) {
      Column(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.size(20.dp))

        DialogHeader(titleId = titleId, onDismissRequest)
        Spacer(modifier = Modifier.size(16.dp))

        content()

        Spacer(modifier = Modifier.size(24.dp))
      }
    }
  }

@Composable
private fun DialogHeader(@StringRes titleId: Int, onDismissRequest: () -> Unit) =
  Box(modifier = Modifier.fillMaxWidth()) {
    Text(
      modifier = Modifier.align(Alignment.Center),
      text = stringResource(id = titleId),
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      color = Black
    )

    Icon(
      modifier = Modifier
        .size(24.dp)
        .align(Alignment.CenterEnd)
        .clickable { onDismissRequest() },
      painter = painterResource(id = com.google.android.material.R.drawable.ic_m3_chip_close),
      contentDescription = null,
      tint = Color.Unspecified
    )
  }