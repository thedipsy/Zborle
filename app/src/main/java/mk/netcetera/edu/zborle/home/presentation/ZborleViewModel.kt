package mk.netcetera.edu.zborle.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class ZborleViewModel : ViewModel() {

  companion object {
    private const val ATTEMPTS = 6
    private val EMPTY_ROW = WordState("", List(5) { "" to LetterStatus.DEFAULT })
  }

  private val getWordExamples = GetWordExamples()

  private val wordExamples = getWordExamples()

  private val currentWord: MutableStateFlow<CurrentWordState> =
    MutableStateFlow(CurrentWordState(word = "", isComplete = false))
  private val wordAttempts: MutableStateFlow<WordAttempts> = MutableStateFlow(listOf())
  private val letterInputState: MutableStateFlow<LinkedHashMap<String, LetterStatus>> =
    MutableStateFlow(getInitialLetterInputState())

  val viewState = combine(
    currentWord,
    wordAttempts,
    letterInputState
  ) { currentWordState, wordAttempts, linkedHashMap ->
    createViewState(currentWordState, wordAttempts, linkedHashMap, wordExamples)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = ZborleState(
      wordAttempts = List(6) { EMPTY_ROW },
      letterInputState = letterInputState.value.toList(),
      gameStatus = GameStatus.IN_PROGRESS,
      wordExamples = wordExamples
    )
  )

  fun onLetterEntered(letter: String) {
    if (currentWord.value.word.length == 5) return

    val updatedCurrentWord = currentWord.value.word.plus(letter)
    currentWord.value = currentWord.value.copy(
      word = updatedCurrentWord,
      isComplete = updatedCurrentWord.length == 5
    )
  }

  fun onBackspaceClicked() {
    if (currentWord.value.word.isEmpty()) return

    val updatedCurrentWord = currentWord.value.word.dropLast(1)
    currentWord.update {
      it.copy(
        word = updatedCurrentWord,
        isComplete = updatedCurrentWord.length == 5
      )
    }
  }

  fun onEnterClicked() {
    if (wordAttempts.value.size == ATTEMPTS) return
    if (!currentWord.value.isComplete) return

    val wordAttemptsList = wordAttempts.value.toMutableList()

    // todo get result from backend
    val newWord = WordState(
      currentWord.value.word,
      currentWord.value.word.map { it.toString() to LetterStatus.values().random() }
    )
    wordAttemptsList.add(newWord)

    wordAttempts.value = wordAttemptsList
    updateLetterInputState(newWord)
    clearCurrentWord()

  }

  private fun updateLetterInputState(newWord: WordState) =
    newWord.correctLetters.forEach { (newLetter, newStatus) ->
      letterInputState.value.computeIfPresent(newLetter) { _, currentStatus ->
        newStatus.takeIf { it != currentStatus } ?: currentStatus
      }
    }

  private fun clearCurrentWord() {
    currentWord.value = CurrentWordState("", isComplete = false)
  }

  private fun getInitialLetterInputState(): LinkedHashMap<String, LetterStatus> {
    val macedonianAlphabet = listOf(
      "Љ", "Њ", "Е", "Р", "Т", "S", "У", "И", "О", "П", "Ш", "Ѓ", "Ж",
      "А", "С", "Д", "Ф", "Г", "Х", "Ј", "К", "Л", "Ч", "Ќ",
      "З", "Џ", "Ц", "В", "Б", "Н", "М"
    )
    return LinkedHashMap(macedonianAlphabet.associateWith { LetterStatus.DEFAULT })
  }


  private fun createViewState(
    currentWord: CurrentWordState,
    wordAttempts: WordAttempts,
    letterInputState: LinkedHashMap<String, LetterStatus>,
    wordExamples: WordAttempts
  ): ZborleState {
    val overallAttempts = wordAttempts.toMutableList()

    val newWord = WordState(
      currentWord.word,
      currentWord.word.map { it.toString() to LetterStatus.DEFAULT }
    )
    overallAttempts.add(newWord)

    val emptyRow = WordState("", List(5) { "" to LetterStatus.DEFAULT })

    while (overallAttempts.size < 6) {
      overallAttempts.add(emptyRow)
    }

    return ZborleState(
      wordAttempts = overallAttempts,
      gameStatus = GameStatus.IN_PROGRESS,
      letterInputState = letterInputState.toList(),
      wordExamples = wordExamples
    )
  }
}

