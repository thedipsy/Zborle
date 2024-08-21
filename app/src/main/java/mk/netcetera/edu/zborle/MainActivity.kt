package mk.netcetera.edu.zborle

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import mk.netcetera.edu.zborle.login.presentation.LoginActivity
import mk.netcetera.edu.zborle.network.service.RetrofitService

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

   // RetrofitService.initialize(this)
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
  }

}