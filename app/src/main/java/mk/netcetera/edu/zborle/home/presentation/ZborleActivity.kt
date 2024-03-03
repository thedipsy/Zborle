package mk.netcetera.edu.zborle.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

class ZborleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZborleTheme {
        Surface {
          ZborleScreen()
        }
      }
    }
  }

}