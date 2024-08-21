package mk.netcetera.edu.zborle.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme
import mk.netcetera.edu.zborle.utils.collectLatest

class ZborleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel : ZborleViewModel by viewModels()

    setContent {
      ZborleTheme {
        Surface {
          ZborleScreen(
            viewState = viewModel.viewState.collectAsState().value,
            onLetterEntered = viewModel::onLetterEntered,
            onEnterClicked = viewModel::onEnterClicked,
            onBackspaceClicked = viewModel::onBackspaceClicked,
            onStatisticsClicked = viewModel::onStatisticsDialogClicked,
            onStatisticsDialogDismiss = viewModel::onDismissStatisticsDialog
          )
        }
      }
    }
  }

}