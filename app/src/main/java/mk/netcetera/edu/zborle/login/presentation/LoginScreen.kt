package mk.netcetera.edu.zborle.login.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.compose.*

/**
 * Composable function representing the Login screen.
 *
 * @param onEmailTextChanged Lambda function to be invoked when the email text changes.
 * @param onPasswordTextChanged Lambda function to be invoked when the password text changes.
 * @param onLoginClick Lambda function to be invoked when the user clicks the login button.
 * @param onRegisterClick Lambda function to be invoked when the user clicks the register button.
 * @param onBack Lambda function to be invoked when the user presses the back button.
 */
@Composable
fun LoginScreen(
    viewState: LoginViewState,
    onEmailTextChanged: (String) -> Unit,
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
        ZborleHeader()

        // email
        OutlinedTextField(
            textField = viewState.email,
            labelId = R.string.email,
            enabled = !viewState.isLoading,
            onTextChanged = onEmailTextChanged,
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) }
        )
        // password
        OutlinedPasswordTextField(
            passwordTextField = viewState.password,
            labelId = R.string.password,
            enabled = !viewState.isLoading,
            showLeadingIcon = true,
            onPasswordTextChanged = onPasswordTextChanged
        )

        // login button
        PrimaryButton(
            textId = R.string.login,
            isLoading = viewState.isLoading,
            onClick = onLoginClick
        )
        // register button
        SecondaryButton(
            textId = R.string.register,
            onClick = onRegisterClick,
            isEnabled = !viewState.isLoading
        )
    }

    val errorMessage = viewState.errorMessageId?.let { stringResource(id = it) }
    val context = LocalContext.current
    LaunchedEffect(key1 = viewState.errorMessageId) {
        if (viewState.errorMessageId != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}