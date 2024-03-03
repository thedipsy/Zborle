package mk.netcetera.edu.zborle.register.presentation

import mk.netcetera.edu.zborle.common.presentation.TextField

/**
 * Represents the state of the register screen UI.
 *
 * @property name The current input in the name field.
 * @property surname The current input in the surname field.
 * @property email The current input in the email field.
 * @property password The current input in the password field.
 * @property isLoading Indicates whether the login process is in progress.
 * @property errorMessage Optional error message to be displayed in case of login failure.
 */
data class RegisterViewState(
  val name: TextField,
  val surname: TextField,
  val email: TextField,
  val password: TextField,
  val confirmPassword: TextField,
  val isLoading: Boolean,
  val errorMessage: String?
)

