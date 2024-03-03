package mk.netcetera.edu.zborle

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import mk.netcetera.edu.zborle.login.presentation.LoginActivity
import mk.netcetera.edu.zborle.login.presentation.LoginScreen
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
  }

}