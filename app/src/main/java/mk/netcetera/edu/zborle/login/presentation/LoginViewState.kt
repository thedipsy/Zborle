package mk.netcetera.edu.zborle.login.presentation

import androidx.annotation.StringRes

/**
 * Represents the state of the login screen UI.
 *
 * @property username The current input in the username field.
 * @property password The current input in the password field.
 * @property isLoading Indicates whether the login process is in progress.
 * @property errorMessage Optional error message to be displayed in case of login failure.
 */
data class LoginViewState(
  val username: TextField,
  val password: TextField,
  val isLoading: Boolean,
  val errorMessage: String?
)

data class TextField(
  val text: String,
  @StringRes val errorMessageId: Int? = null
)