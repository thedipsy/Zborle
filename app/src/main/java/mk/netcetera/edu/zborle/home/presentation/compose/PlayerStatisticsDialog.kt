package mk.netcetera.edu.zborle.home.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.compose.ZborleDialog
import mk.netcetera.edu.zborle.home.presentation.StatisticsDialogState
import mk.netcetera.edu.zborle.ui.theme.Black

@Composable
fun PlayerStatisticsDialog(
    statisticsDialogState: StatisticsDialogState,
    onDismissRequest: () -> Unit
) = ZborleDialog(
    titleId = R.string.statistics,
    onDismissRequest = onDismissRequest
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PercentageInfo(statisticsDialogState.gamesPlayed.orEmpty(), R.string.overall_attempts_text)
        PercentageInfo(statisticsDialogState.winPercentage.orEmpty(), R.string.success_rate)
    }
}


@Composable
fun RowScope.PercentageInfo(value: String, @StringRes textId: Int) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Black
        )
        Text(
            text = stringResource(textId),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Black,
            lineHeight = 14.sp
        )
    }
}

