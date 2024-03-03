package mk.netcetera.edu.zborle.login.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * ViewModel responsible for handling login-related logic.
 */
class LoginViewModel : ViewModel() {

  /** Exposes navigation events **/
  private val _navigationEvent: MutableSharedFlow<LoginEvent> =
    MutableSharedFlow(extraBufferCapacity = 1)
  val navigationEvent: Flow<LoginEvent>
    get() = _navigationEvent

  /**
   * Invoked when the user clicks on Register button.
   */
  fun onRegisterClicked() = _navigationEvent.tryEmit(LoginEvent.OpenRegister)

  /**
   * Invoked when the user clicks on Login button.
   */
  fun onLoginClicked() {
    // TODO: login call

    _navigationEvent.tryEmit(LoginEvent.OpenZborle)
  }
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