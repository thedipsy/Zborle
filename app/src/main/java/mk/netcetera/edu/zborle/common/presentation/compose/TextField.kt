package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.TextField

@Composable
fun OutlinedTextField(
  textField: TextField,
  enabled: Boolean,
  @StringRes labelId: Int,
  leadingIcon: (@Composable () -> Unit)? = null,
  onTextChanged: (String) -> Unit,
) = OutlinedTextField(
  modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
  value = textField.text,
  onValueChange = onTextChanged,
  label = { Text(stringResource(id = labelId)) },
  shape = RoundedCornerShape(12.dp),
  singleLine = true,
  leadingIcon = leadingIcon,
  enabled = enabled,
  supportingText = getErrorText(textField.errorMessageId)
)

@Composable
fun OutlinedPasswordTextField(
  passwordTextField: TextField,
  @StringRes labelId: Int,
  enabled: Boolean,
  showLeadingIcon: Boolean = false,
  onPasswordTextChanged: (String) -> Unit,
) {
  var passwordVisibilityIcon by remember { mutableStateOf(R.drawable.visibility_on) }
  var passwordVisualTransformation: VisualTransformation by remember {
    mutableStateOf(PasswordVisualTransformation())
  }

  val leadingIcon: (@Composable () -> Unit)? = if (showLeadingIcon) {
    { Icon(Icons.Filled.Lock, contentDescription = null) }
  } else {
    null
  }

  OutlinedTextField(
    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
    value = passwordTextField.text,
    onValueChange = onPasswordTextChanged,
    label = { Text(stringResource(id = labelId)) },
    leadingIcon = leadingIcon,
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
    supportingText = getErrorText(passwordTextField.errorMessageId)
  )
}

private fun getErrorText(errorMessageId: Int?): (@Composable () -> Unit)? =
  if (errorMessageId != null) {
    {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = errorMessageId),
        color = MaterialTheme.colorScheme.error
      )
    }
  } else {
    null
  }