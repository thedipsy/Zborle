package mk.netcetera.edu.zborle.home.presentation

class GetWordExamples {

  operator fun invoke() =
    listOf(
      WordState(
        word = "ПИЛОТ",
        correctLetters = listOf(
          "П" to LetterStatus.CORRECT,
          "И" to LetterStatus.DEFAULT,
          "Л" to LetterStatus.DEFAULT,
          "О" to LetterStatus.DEFAULT,
          "Т" to LetterStatus.DEFAULT
        )
      ),
      WordState(
        word = "ПИЛОТ",
        correctLetters = listOf(
          "Ч" to LetterStatus.DEFAULT,
          "О" to LetterStatus.DEFAULT,
          "Р" to LetterStatus.PARTIALLY_CORRECT,
          "Б" to LetterStatus.DEFAULT,
          "А" to LetterStatus.DEFAULT
        )
      ),
      WordState(
        word = "ПИЛОТ",
        correctLetters = listOf(
          "Ш" to LetterStatus.DEFAULT,
          "А" to LetterStatus.DEFAULT,
          "П" to LetterStatus.DEFAULT,
          "К" to LetterStatus.NOT_CORRECT,
          "А" to LetterStatus.DEFAULT
        )
      )
    )


}
