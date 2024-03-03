package mk.netcetera.edu.zborle.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mk.netcetera.edu.zborle.R

@Composable
fun HowToPlayDialog(
  onDismissRequest: () -> Unit
) {
  Dialog(onDismissRequest = { onDismissRequest() }) {
    Card(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxSize(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors().copy(containerColor = Color.White)
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {

        HowToPlayDialogHeader(onDismissRequest)

        HowToPlayDescription()

        HowToPlayExample()
      }
    }
  }
}

@Composable
fun HowToPlayExample() {
  Text(
    modifier = Modifier.fillMaxSize(),
    text = stringResource(id = R.string.examples),
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    textAlign = TextAlign.Center
  )
}

@Composable
fun HowToPlayDescription() {
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp),
    text = stringResource(id = R.string.descriptionTitle),
    fontSize = 16.sp,
    color = Color.Gray
  )
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp),
    text = stringResource(id = R.string.descriptionOne),
    fontSize = 16.sp,
    color = Color.Gray
  )
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp),
    text = stringResource(id = R.string.descriptionTwo),
    fontSize = 16.sp,
    color = Color.Gray
  )
}

@Composable
fun HowToPlayDialogHeader(onDismissRequest: () -> Unit) {
  Box(modifier = Modifier.fillMaxWidth()) {

    Text(
      modifier = Modifier
        .padding(16.dp)
        .align(Alignment.Center),
      text = stringResource(id = R.string.how_to_play),
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center
    )

    Icon(
      modifier = Modifier
        .padding(16.dp)
        .size(24.dp)
        .align(Alignment.CenterEnd)
        .clickable { onDismissRequest() },
      painter = painterResource(id = com.google.android.material.R.drawable.ic_m3_chip_close),
      contentDescription = null,
      tint = Color.Unspecified
    )
  }

}
