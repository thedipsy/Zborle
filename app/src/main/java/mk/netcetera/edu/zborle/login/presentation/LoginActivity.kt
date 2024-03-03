package mk.netcetera.edu.zborle.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import mk.netcetera.edu.zborle.home.presentation.ZborleActivity
import mk.netcetera.edu.zborle.register.presentation.RegisterActivity
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZborleTheme {
        Surface {
          LoginScreen(
            onLoginClick = ::onLoginClick,
            onRegisterClick = ::onRegisterClick
          )
        }
      }
    }
  }

  private fun onLoginClick() =
    Intent(this, ZborleActivity::class.java)
      .apply { startActivity(this) }


  private fun onRegisterClick() =
    Intent(this, RegisterActivity::class.java)
    .apply { startActivity(this) }

}