package mk.netcetera.edu.zborle.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.TextField
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.register.domain.RegisterUserUseCase

/**
 * ViewModel responsible for handling register-related logic.
 */
class RegisterViewModel : ViewModel() {

    private val registerUser by lazy { RegisterUserUseCase() }

    /** Exposes view state. */
    private val _viewState = MutableStateFlow(initialViewState())
    val viewState: StateFlow<RegisterViewState>
        get() = _viewState

    /** Exposes navigation events. **/
    private val _navigationEvent: MutableSharedFlow<RegisterEvent> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val navigationEvent: Flow<RegisterEvent>
        get() = _navigationEvent

    /**
     * Invoked when the user makes changes in the name.
     */
    fun onNameTextChanged(text: String) =
        _viewState.update { it.copy(name = it.name.copy(text = text, errorMessageId = null)) }

    /**
     * Invoked when the user makes changes in the surname.
     */
    fun onSurnameTextChanged(text: String) =
        _viewState.update { it.copy(surname = it.surname.copy(text = text, errorMessageId = null)) }

    /**
     * Invoked when the user makes changes in the email.
     */
    fun onEmailTextChanged(text: String) =
        _viewState.update { it.copy(email = it.email.copy(text = text, errorMessageId = null)) }

    /**
     * Invoked when the user makes changes in the password.
     */
    fun onPasswordTextChanged(text: String) =
        _viewState.update {
            it.copy(
                password = it.password.copy(
                    text = text,
                    errorMessageId = null
                )
            )
        }.also { checkMatchingPasswords() }

    /**
     * Invoked when the user makes changes in the confirm password.
     */
    fun onConfirmPasswordTextChanged(text: String) =
        _viewState.update {
            it.copy(confirmPassword = it.confirmPassword.copy(text = text, errorMessageId = null))
        }.also { checkMatchingPasswords() }

    /**
     * Invoked when the user clicks on Login button.
     */
    fun onLoginClicked() =
        _navigationEvent.tryEmit(RegisterEvent.OpenLogin)

    /**
     * Invoked when the user clicks on Register button.
     */
    fun onRegisterClicked() =
        viewModelScope.launch {
            if (validateData()) {
                register()
            }
        }

    private suspend fun register() {
        _viewState.update { it.copy(isLoading = true, errorMessage = null) }
        withContext(Dispatchers.IO) {
            with(_viewState.value) {
                val outcome = registerUser(
                    firstName = name.text,
                    lastName = surname.text,
                    email = email.text,
                    password = password.text
                )

                when (outcome) {
                    is ApiResponse.Complete -> {
                        _navigationEvent.tryEmit(RegisterEvent.OpenZborle(outcome.value.accessToken))
                    }

                    ApiResponse.Error -> {
                        _viewState.update { it.copy(errorMessage = "Error", isLoading = false) }
                    }
                }
            }
        }
    }

    private fun checkMatchingPasswords() {
        val password = _viewState.value.password.text
        val confirmPassword = _viewState.value.confirmPassword.text

        val errorMessageId = R.string.password_do_not_match
            .takeIf { password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword }

        _viewState.update {
            it.copy(confirmPassword = it.confirmPassword.copy(errorMessageId = errorMessageId))
        }
    }

    private fun validateData(): Boolean {
        if (_viewState.value.name.text.isEmpty()) {
            _viewState.update { it.copy(name = it.name.copy(errorMessageId = R.string.mandatory_field)) }
        }
        if (_viewState.value.surname.text.isEmpty()) {
            _viewState.update { it.copy(surname = it.surname.copy(errorMessageId = R.string.mandatory_field)) }
        }
        if (_viewState.value.email.text.isEmpty()) {
            _viewState.update { it.copy(email = it.email.copy(errorMessageId = R.string.mandatory_field)) }
        }
        if (_viewState.value.password.text.isEmpty()) {
            _viewState.update { it.copy(password = it.password.copy(errorMessageId = R.string.mandatory_field)) }
        }
        if (_viewState.value.confirmPassword.text.isEmpty()) {
            _viewState.update { it.copy(confirmPassword = it.confirmPassword.copy(errorMessageId = R.string.mandatory_field)) }
        }

        return _viewState.value.run {
            name.isValid() && surname.isValid() && email.isValid() && password.isValid() && confirmPassword.isValid()
        }
    }

    private fun initialViewState() = RegisterViewState(
        name = TextField(""),
        surname = TextField(""),
        email = TextField(""),
        password = TextField(""),
        confirmPassword = TextField(""),
        isLoading = false,
        errorMessage = null
    )
}

/**
 * Defines events originating from the Register flow.
 */
sealed interface RegisterEvent {

    /**
     * Represents the event to open the Login screen.
     */
    data object OpenLogin : RegisterEvent

    /**
     * Represents the event to open the Zborle screen.
     */
    data class OpenZborle(val token: String) : RegisterEvent
}