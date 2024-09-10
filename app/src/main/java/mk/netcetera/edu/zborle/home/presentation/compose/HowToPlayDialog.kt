package mk.netcetera.edu.zborle.home.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.common.presentation.compose.ZborleDialog
import mk.netcetera.edu.zborle.common.presentation.compose.ZborleWord
import mk.netcetera.edu.zborle.home.presentation.WordAttempts
import mk.netcetera.edu.zborle.home.presentation.WordState
import mk.netcetera.edu.zborle.ui.theme.Gray
import mk.netcetera.edu.zborle.utils.parseBold

@Composable
fun HowToPlayDialog(
    wordExamples: WordAttempts,
    onDismissRequest: () -> Unit
) = ZborleDialog(
    titleId = R.string.how_to_play,
    onDismissRequest = { onDismissRequest() },
) {
    HowToPlayDescription()
    Spacer(modifier = Modifier.size(8.dp))

    HowToPlayExample(wordExamples)
    Spacer(modifier = Modifier.size(8.dp))

    DescriptionText(textId = R.string.new_zborle_daily)
}

@Composable
private fun HowToPlayExample(wordExamples: WordAttempts) {
    Divider()
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        text = stringResource(id = R.string.examples),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )

    if (wordExamples.size == 3) {
        PositionExample(wordExamples[0], R.string.correct_position_example)
        PositionExample(wordExamples[1], R.string.partially_correct_example)
        PositionExample(wordExamples[2], R.string.incorrect_example)
    }
    Divider()
}

@Composable
private fun PositionExample(wordState: WordState, @StringRes textId: Int) {
    ZborleWord(wordState)

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 12.dp),
        text = stringResource(textId).parseBold(),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Gray
    )
}

@Composable
private fun HowToPlayDescription() {
    DescriptionText(textId = R.string.descriptionTitle)
    DescriptionText(textId = R.string.descriptionOne)
    DescriptionText(textId = R.string.descriptionTwo)
}

@Composable
private fun DescriptionText(@StringRes textId: Int) = Text(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
    text = stringResource(id = textId).parseBold(),
    fontSize = 16.sp,
    color = Gray
)
