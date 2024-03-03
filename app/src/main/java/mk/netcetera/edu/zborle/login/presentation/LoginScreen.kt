package mk.netcetera.edu.zborle.login.presentation

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

/**
 * Composable function representing the Login screen.
 *
 * @param onUsernameTextChanged Lambda function to be invoked when the username text changes.
 * @param onPasswordTextChanged Lambda function to be invoked when the password text changes.
 * @param onLoginClick Lambda function to be invoked when the user clicks the login button.
 * @param onRegisterClick Lambda function to be invoked when the user clicks the register button.
 * @param onBack Lambda function to be invoked when the user presses the back button.
 */
@Composable
fun LoginScreen(
  onUsernameTextChanged: (String) -> Unit,
  onPasswordTextChanged: (String) -> Unit,
  onLoginClick: () -> Unit,
  onRegisterClick: () -> Unit,
  onBack: () -> Unit
) {
  BackHandler { onBack() }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(all = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    LoginHeader()

    UsernameTextField(onUsernameTextChanged = onUsernameTextChanged)
    PasswordTextField(onPasswordTextChanged = onPasswordTextChanged)
    LoginButton(onLoginClick = onLoginClick)

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
private fun LoginHeader() {
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
private fun UsernameTextField(onUsernameTextChanged: (String) -> Unit) {
  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = "",
    onValueChange = onUsernameTextChanged,
    label = { Text(stringResource(id = R.string.username)) },
    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
    shape = RoundedCornerShape(12.dp),
    singleLine = true
  )
}


@Composable
private fun PasswordTextField(onPasswordTextChanged: (String) -> Unit) {
  var passwordVisibilityIcon by remember { mutableStateOf(R.drawable.visibility_on) }
  var passwordVisualTransformation: VisualTransformation by remember {
    mutableStateOf(PasswordVisualTransformation())
  }

  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = "",
    onValueChange = onPasswordTextChanged,
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


@Composable
private fun LoginButton(onLoginClick: () -> Unit) =
  Button(
    modifier = Modifier
      .size(height = 60.dp, width = 200.dp)
      .padding(top = 12.dp, bottom = 6.dp),
    colors = ButtonDefaults.buttonColors(containerColor = ZborleTheme.buttonColors.background),
    onClick = onLoginClick
  ) {
    Text(
      text = stringResource(id = R.string.login),
      color = ZborleTheme.buttonColors.text,
      fontSize = 18.sp
    )
  }