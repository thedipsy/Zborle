package mk.netcetera.edu.zborle.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

@Composable
fun LoginScreen(onRegisterClick: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(all = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Header()

    UsernameTextField()

    PasswordTextField()

    Button(
      modifier = Modifier
        .size(height = 60.dp, width = 200.dp)
        .padding(top = 12.dp, bottom = 6.dp),
      colors = ButtonDefaults.buttonColors(containerColor = ZborleTheme.buttonColors.background),
      onClick = { /*TODO*/ }) {
      Text(
        text = stringResource(id = R.string.login),
        color = ZborleTheme.buttonColors.text,
        fontSize = 18.sp
      )
    }

    Text(
      modifier = Modifier
        .padding(vertical = 6.dp)
        .clickable { onRegisterClick() },
      text = stringResource(id = R.string.register),
      fontSize = 14.sp,
      textDecoration = TextDecoration.Underline
    )
  }
}

@Composable
fun Header() {
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

@Composable
fun UsernameTextField() {
  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = "",
    onValueChange = { /*TODO*/ },
    label = { Text(stringResource(id = R.string.username)) },
    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
    shape = RoundedCornerShape(12.dp),
    singleLine = true
  )
}


@Composable
fun PasswordTextField() {
  var passwordVisibilityIcon by remember { mutableStateOf(R.drawable.visibility_on) }
  var passwordVisualTransformation: VisualTransformation by remember {
    mutableStateOf(PasswordVisualTransformation())
  }

  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = "",
    onValueChange = { /*TODO*/ },
    label = { Text(stringResource(id = R.string.lozinka)) },
    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
    trailingIcon = {
      Icon(
        modifier = Modifier.clickable {
          if (passwordVisualTransformation is PasswordVisualTransformation) {
            passwordVisualTransformation = VisualTransformation.None
            passwordVisibilityIcon = R.drawable.visibility_off
          } else {
            passwordVisualTransformation = PasswordVisualTransformation()
            passwordVisibilityIcon = R.drawable.visibility_on
          }
        },
        painter = painterResource(id = passwordVisibilityIcon),
        contentDescription = null
      )
    },
    shape = RoundedCornerShape(12.dp),
    singleLine = true,
    visualTransformation = passwordVisualTransformation
  )
}

@Preview
@Composable
fun LoginPreview() {
  ZborleTheme {
    LoginScreen { }
  }
}