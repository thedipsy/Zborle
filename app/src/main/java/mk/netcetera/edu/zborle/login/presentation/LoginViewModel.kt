package mk.netcetera.edu.zborle.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.TextField

/**
 * ViewModel responsible for handling login-related logic.
 */
class LoginViewModel : ViewModel() {

  /** Exposes view state. */
  private val _viewState = MutableStateFlow(initialViewState())
  val viewState: StateFlow<LoginViewState>
    get() = _viewState

  /** Exposes navigation events. **/
  private val _navigationEvent: MutableSharedFlow<LoginEvent> =
    MutableSharedFlow(extraBufferCapacity = 1)
  val navigationEvent: Flow<LoginEvent>
    get() = _navigationEvent

  /**
   * Invoked when the user makes changes in the email.
   */
  fun onEmailTextChanged(text: String) =
    _viewState.update { it.copy(email = it.email.copy(text = text, errorMessageId = null)) }

  /**
   * Invoked when the user makes changes in the password.
   */
  fun onPasswordTextChanged(text: String) =
    _viewState.update { it.copy(password = it.password.copy(text = text, errorMessageId = null)) }

  /**
   * Invoked when the user clicks on Register button.
   */
  fun onRegisterClicked() =
    _navigationEvent.tryEmit(LoginEvent.OpenRegister)

  /**
   * Invoked when the user clicks on Login button.
   */
  fun onLoginClicked() =
    viewModelScope.launch {
      frontendValidation()
      if (!isDataValid()) return@launch

      login()
    }

  private suspend fun login() {
    _viewState.update { it.copy(isLoading = true) }
    withContext(Dispatchers.IO) {
      delay(3000)
      // TODO: login call
      _navigationEvent.tryEmit(LoginEvent.OpenZborle)
    }
  }

  private fun isDataValid() =
    _viewState.value.email.errorMessageId == null && _viewState.value.password.errorMessageId == null

  private fun frontendValidation() {
    if (_viewState.value.email.text.isEmpty()) {
      _viewState.update { it.copy(email = it.email.copy(errorMessageId = R.string.mandatory_field)) }
    }
    if (_viewState.value.password.text.isEmpty()) {
      _viewState.update { it.copy(password = it.password.copy(errorMessageId = R.string.mandatory_field)) }
    }
  }

  private fun initialViewState() = LoginViewState(
    email = TextField(""),
    password = TextField(""),
    isLoading = false,
    errorMessage = null
  )
}

/**
 * Defines events originating from the Login flow.
 */
sealed interface LoginEvent {

  /**
   * Represents the event to open the Registration screen.
   */
  object OpenRegister : LoginEvent

  /**
   * Represents the event to open the Zborle screen.
   */
  object OpenZborle : LoginEvent
}