package mk.netcetera.edu.zborle.home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import mk.netcetera.edu.zborle.data.TokenManager
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

class ZborleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ZborleViewModel by viewModels {
            ZborleViewModel.provideFactory(this)
        }

        viewModel.init(getToken())

        setContent {
            ZborleTheme {
                Surface {
                    ZborleScreen(
                        viewState = viewModel.viewState.collectAsState().value,
                        onLetterEntered = viewModel::onLetterEntered,
                        onEnterClicked = { viewModel.onEnterClicked(getToken()) },
                        onBackspaceClicked = viewModel::onBackspaceClicked,
                        onStatisticsClicked = { viewModel.onStatisticsDialogClicked(getToken()) },
                        onDismissDialog = viewModel::onDismissDialog
                    )
                }
            }
        }
    }

    private fun getToken() = TokenManager.getToken(this)

}