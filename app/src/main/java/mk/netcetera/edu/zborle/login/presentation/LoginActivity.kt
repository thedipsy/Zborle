package mk.netcetera.edu.zborle.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import mk.netcetera.edu.zborle.utils.collectLatest
import mk.netcetera.edu.zborle.home.presentation.ZborleActivity
import mk.netcetera.edu.zborle.register.presentation.RegisterActivity
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

/**
 * Activity for Login.
 */
class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel: LoginViewModel by viewModels()
    viewModel.navigationEvent.collectLatest(this, ::navigate)

    setContent {
      ZborleTheme {
        Surface {
          LoginScreen(
            viewState = viewModel.viewState.collectAsState().value,
            onUsernameTextChanged = viewModel::onUsernameTextChanged,
            onPasswordTextChanged = viewModel::onPasswordTextChanged,
            onLoginClick = viewModel::onLoginClicked,
            onRegisterClick = viewModel::onRegisterClicked,
            onBack = ::finish
          )
        }
      }
    }
  }

  private fun navigate(event: LoginEvent) =
    when (event) {
      LoginEvent.OpenRegister -> navigateToRegister()
      LoginEvent.OpenZborle -> navigateToZborle()
    }

  private fun navigateToZborle() =
    Intent(this, ZborleActivity::class.java)
      .apply { startActivity(this) }
      .also { finish() }

  private fun navigateToRegister() =
    Intent(this, RegisterActivity::class.java)
      .apply { startActivity(this) }
}