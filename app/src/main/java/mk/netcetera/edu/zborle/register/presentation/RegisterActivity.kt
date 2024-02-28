package mk.netcetera.edu.zborle.register.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import mk.netcetera.edu.zborle.login.presentation.LoginActivity
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

class RegisterActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZborleTheme {
        Surface {
          RegisterScreen(
            onLoginClick = ::onLoginClick
          )
        }
      }
    }
  }

  private fun onLoginClick() =
    Intent(this, LoginActivity::class.java)
      .apply { startActivity(this) }

}