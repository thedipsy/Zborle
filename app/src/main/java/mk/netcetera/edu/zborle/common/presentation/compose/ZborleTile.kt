package mk.netcetera.edu.zborle.common.presentation.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mk.netcetera.edu.zborle.home.presentation.LetterStatus
import mk.netcetera.edu.zborle.home.presentation.WordState
import mk.netcetera.edu.zborle.ui.theme.DarkGray
import mk.netcetera.edu.zborle.ui.theme.LightGray
import mk.netcetera.edu.zborle.ui.theme.White
import mk.netcetera.edu.zborle.ui.theme.ZborleTheme

@Composable
fun ZborleWord(wordState: WordState) =
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(5) {
            val (letter, status) = wordState.correctLetters.getOrNull(it)
                ?: ("" to LetterStatus.DEFAULT)
            ZborleTile(letter = letter, letterStatus = status)
        }
    }

@Composable
private fun RowScope.ZborleTile(letter: String, letterStatus: LetterStatus) {
    var isAttempted by remember { mutableStateOf(letterStatus != LetterStatus.DEFAULT) }
    val rotationX = remember { Animatable(0f) }

    var currentStatus by remember { mutableStateOf(LetterStatus.DEFAULT) }
    val backgroundColor = backgroundColor(letterStatus = currentStatus)

    val textColor = if (currentStatus == LetterStatus.DEFAULT) DarkGray else White
    val modifier = if (currentStatus == LetterStatus.DEFAULT) {
        Modifier.border(1.dp, color = LightGray, shape = RoundedCornerShape(4.dp))
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .graphicsLayer {
                this.rotationX = rotationX.value
            }
            .padding(2.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(4.dp))
            .then(modifier),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    // rotate text in opposite direction so the user does not see the letters flipped.
                    this.rotationX = -rotationX.value
                },
            text = letter.uppercase(),
            fontSize = 24.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }

    LaunchedEffect(isAttempted) {
        if (isAttempted) {
            // Animate to 90f
            rotationX.animateTo(
                targetValue = 90f,
                animationSpec = tween(durationMillis = 250, easing = LinearEasing)
            )
            // Update status when reaching 90f
            currentStatus = letterStatus

            // Animate to 180f after updating status
            rotationX.animateTo(
                targetValue = 180f,
                animationSpec = tween(durationMillis = 250, easing = LinearEasing)
            )
        }
    }

    // Update the state when the letterStatus is changed, meaning the user tried an attempt.
    LaunchedEffect(letterStatus) {
        isAttempted = letterStatus != LetterStatus.DEFAULT
    }

}

@Composable
private fun backgroundColor(letterStatus: LetterStatus) = when (letterStatus) {
    LetterStatus.CORRECT -> ZborleTheme.statusColors.correctBackground
    LetterStatus.PARTIALLY_CORRECT -> ZborleTheme.statusColors.partiallyCorrectBackground
    LetterStatus.NOT_CORRECT -> ZborleTheme.statusColors.incorrectBackground
    LetterStatus.DEFAULT -> ZborleTheme.statusColors.defaultBackground
}
