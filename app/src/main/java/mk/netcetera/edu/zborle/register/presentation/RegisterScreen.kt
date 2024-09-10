package mk.netcetera.edu.zborle.register.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.compose.*

@Composable
fun RegisterScreen(
    viewState: RegisterViewState,
    onNameTextChanged: (String) -> Unit,
    onSurnameTextChanged: (String) -> Unit,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onConfirmPasswordTextChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit,
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
        ZborleHeader()

        // Name
        OutlinedTextField(
            textField = viewState.name,
            labelId = R.string.name,
            enabled = !viewState.isLoading,
            onTextChanged = onNameTextChanged
        )
        // Surname
        OutlinedTextField(
            textField = viewState.surname,
            labelId = R.string.surname,
            enabled = !viewState.isLoading,
            onTextChanged = onSurnameTextChanged
        )
        //Email
        OutlinedTextField(
            textField = viewState.email,
            labelId = R.string.email,
            enabled = !viewState.isLoading,
            onTextChanged = onEmailTextChanged
        )
        // Password
        OutlinedPasswordTextField(
            passwordTextField = viewState.password,
            labelId = R.string.password,
            enabled = !viewState.isLoading,
            onPasswordTextChanged = onPasswordTextChanged
        )
        // Confirm Password
        OutlinedPasswordTextField(
            passwordTextField = viewState.confirmPassword,
            labelId = R.string.confirm_password,
            enabled = !viewState.isLoading,
            onPasswordTextChanged = onConfirmPasswordTextChanged
        )

        PrimaryButton(
            textId = R.string.register,
            isLoading = viewState.isLoading,
            onRegisterClicked
        )
        SecondaryButton(
            textId = R.string.login,
            onClick = onLoginClicked,
            isEnabled = !viewState.isLoading
        )
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewState.errorMessage) {
        if (!viewState.errorMessage.isNullOrEmpty()) {
            Toast.makeText(context, viewState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}