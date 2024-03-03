package mk.netcetera.edu.zborle.register.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import mk.netcetera.edu.zborle.home.presentation.ZborleActivity
import mk.netcetera.edu.zborle.login.presentation.LoginActivity
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme
import mk.netcetera.edu.zborle.utils.collectLatest

class RegisterActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel: RegisterViewModel by viewModels()
    viewModel.navigationEvent.collectLatest(this, ::navigate)

    setContent {
      ZborleTheme {
        Surface {
          RegisterScreen(
            viewState = viewModel.viewState.collectAsState().value,
            onNameTextChanged = viewModel::onNameTextChanged,
            onSurnameTextChanged = viewModel::onSurnameTextChanged,
            onEmailTextChanged = viewModel::onEmailTextChanged,
            onPasswordTextChanged = viewModel::onPasswordTextChanged,
            onConfirmPasswordTextChanged = viewModel::onConfirmPasswordTextChanged,
            onRegisterClicked = viewModel::onRegisterClicked,
            onLoginClicked = viewModel::onLoginClicked,
            onBack = ::finish
          )
        }
      }
    }
  }

  private fun navigate(event: RegisterEvent) =
    when(event) {
      RegisterEvent.OpenLogin -> navigateToLogin()
      RegisterEvent.OpenZborle -> navigateToZborle()
    }

  private fun navigateToZborle() =
    Intent(this, ZborleActivity::class.java)
      .apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivity(this) }
      .also { finish() }

  private fun navigateToLogin() =
    Intent(this, LoginActivity::class.java)
      .apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivity(this) }
      .also { finish() }

}