package mk.netcetera.edu.zborle.login.presentation

import mk.netcetera.edu.zborle.common.presentation.TextField

/**
 * Represents the state of the login screen UI.
 *
 * @property email The current input in the email field.
 * @property password The current input in the password field.
 * @property isLoading Indicates whether the login process is in progress.
 * @property errorMessageId Optional errorId message to be displayed in case of login failure.
 */
data class LoginViewState(
  val email: TextField,
  val password: TextField,
  val isLoading: Boolean,
  val errorMessageId: Int?
)