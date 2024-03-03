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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import mk.netcetera.edu.zborle.compose.Button

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
  viewState: LoginViewState,
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

    UsernameTextField(
      usernameTextField = viewState.username,
      enabled = !viewState.isLoading,
      onUsernameTextChanged = onUsernameTextChanged
    )
    PasswordTextField(
      passwordTextField = viewState.password,
      enabled = !viewState.isLoading,
      onPasswordTextChanged = onPasswordTextChanged
    )
    Button(
      textId = R.string.login,
      isLoading = viewState.isLoading,
      onClick = onLoginClick
    )

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
private fun UsernameTextField(
  usernameTextField: TextField,
  enabled: Boolean,
  onUsernameTextChanged: (String) -> Unit
) {
  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = usernameTextField.text,
    onValueChange = onUsernameTextChanged,
    label = { Text(stringResource(id = R.string.username)) },
    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
    shape = RoundedCornerShape(12.dp),
    singleLine = true,
    enabled = enabled,
    supportingText = {
      usernameTextField.errorMessageId?.let { errorMessageId ->
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(id = errorMessageId),
          color = MaterialTheme.colorScheme.error
        )
      }
    }
  )
}


@Composable
private fun PasswordTextField(
  passwordTextField: TextField,
  enabled: Boolean,
  onPasswordTextChanged: (String) -> Unit
) {
  var passwordVisibilityIcon by remember { mutableStateOf(R.drawable.visibility_on) }
  var passwordVisualTransformation: VisualTransformation by remember {
    mutableStateOf(PasswordVisualTransformation())
  }

  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    value = passwordTextField.text,
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
    visualTransformation = passwordVisualTransformation,
    enabled = enabled,
    supportingText = {
      passwordTextField.errorMessageId?.let { errorMessageId ->
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(id = errorMessageId),
          color = MaterialTheme.colorScheme.error
        )
      }
    }
  )
}