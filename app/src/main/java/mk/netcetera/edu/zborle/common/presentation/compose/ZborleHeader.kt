package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R

@Composable
fun ZborleHeader() {
  Icon(
    modifier = Modifier
      .size(128.dp)
      .padding(8.dp),
    painter = painterResource(id = R.drawable.zborle_logo),
    contentDescription = null,
    tint = Color.Unspecified
  )
  Text(
    modifier = Modifier.padding(8.dp),
    text = stringResource(id = R.string.zborle_title).uppercase(),
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold
  )
}