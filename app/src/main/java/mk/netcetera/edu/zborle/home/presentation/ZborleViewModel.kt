package mk.netcetera.edu.zborle.home.presentation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mk.netcetera.edu.zborle.R
import mk.netcetera.edu.zborle.home.domain.CheckWordUseCase
import mk.netcetera.edu.zborle.home.domain.GetUserStatisticsUseCase
import mk.netcetera.edu.zborle.home.domain.StartGameUseCase
import mk.netcetera.edu.zborle.network.service.ApiResponse
import java.util.LinkedHashMap
import java.util.Locale

class ZborleViewModel : ViewModel() {

    companion object {
        private const val ATTEMPTS = 6
        private val EMPTY_ROW = WordState("", List(5) { "" to LetterStatus.DEFAULT })

        fun provideFactory(
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return ZborleViewModel() as T
                }
            }
    }

    private val getWordExamples = GetWordExamples()

    private val wordExamples = getWordExamples()

    private val getUserStatistics by lazy { GetUserStatisticsUseCase() }
    private val startGame by lazy { StartGameUseCase() }
    private val checkWord by lazy { CheckWordUseCase() }

    private val currentWord: MutableStateFlow<CurrentWordState> =
        MutableStateFlow(CurrentWordState(word = "", isComplete = false))
    private val wordAttempts: MutableStateFlow<WordAttempts> = MutableStateFlow(listOf())
    private val letterInputState: MutableStateFlow<LinkedHashMap<String, LetterStatus>> =
        MutableStateFlow(getInitialLetterInputState())
    private val dialog: MutableStateFlow<DialogState?> = MutableStateFlow(null)
    private val error: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val gameStatus = MutableStateFlow(GameStatus.IN_PROGRESS)
    private var isGameInitialized = false

    val viewState = combine(
        currentWord,
        wordAttempts,
        letterInputState,
        dialog,
        gameStatus
    ) { currentWordState, wordAttempts, linkedHashMap, dialog, gameStatus ->
        createViewState(
            currentWordState,
            wordAttempts,
            linkedHashMap,
            wordExamples,
            dialog,
            gameStatus
        )
    }.combine(error) { viewState, error ->
        viewState.copy(errorId = error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ZborleState(
            wordAttempts = List(6) { EMPTY_ROW },
            letterInputState = letterInputState.value.toList(),
            gameStatus = GameStatus.IN_PROGRESS,
            wordExamples = wordExamples,
            dialog = dialog.value
        )
    )

    fun init(token: String) {
        if (!isGameInitialized) {
            viewModelScope.launch {
                when (val outcome = startGame(token)) {
                    is ApiResponse.Complete -> {
                        isGameInitialized = true

                        val wordAttemptsList = wordAttempts.value.toMutableList()

                        outcome.value.guesses.forEach { currentGuess ->
                            val newWord = WordState(
                                currentGuess.guess,
                                currentGuess.userGuessResponses.map {
                                    it.letter to LetterStatus.getStatus(it.answer)
                                }
                            )
                            wordAttemptsList.add(newWord)
                            updateLetterInputState(newWord)
                        }
                        wordAttempts.value = wordAttemptsList
                        if (checkIfWon()) {
                            gameStatus.value = GameStatus.WON
                            dialog.value = DialogState.AlreadyGuessed
                        }
                        if (checkIfLost()) {
                            gameStatus.value = GameStatus.LOST
                            dialog.value = DialogState.GameOver
                        }
                    }

                    ApiResponse.Error -> {
                        error.value = R.string.error_message
                    }
                }
            }
        }
    }

    fun onLetterEntered(letter: String) {
        if (gameStatus.value != GameStatus.IN_PROGRESS || currentWord.value.word.length == 5) return

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

    fun onEnterClicked(token: String) {
        error.value = null
        if (gameStatus.value != GameStatus.IN_PROGRESS) return
        if (!currentWord.value.isComplete) {
            error.value = R.string.error_not_enough_letters
            return
        }

        viewModelScope.launch {
            when (val outcome = checkWord(guess = currentWord.value.word, token = token)) {
                is ApiResponse.Complete -> {
                    val wordAttemptsList = wordAttempts.value.toMutableList()
                    if (outcome.value.userGuessResponse.isNullOrEmpty()) {
                        if (outcome.value.message == "Your guessed word does not exist")
                            error.value = R.string.not_existing_word
                        return@launch
                    }

                    val newWord = WordState(
                        currentWord.value.word,
                        outcome.value.userGuessResponse.map {
                            it.letter to LetterStatus.getStatus(it.answer)
                        }
                    )
                    wordAttemptsList.add(newWord)
                    wordAttempts.value = wordAttemptsList
                    updateLetterInputState(newWord)
                    clearCurrentWord()
                    if (checkIfWon()) {
                        gameStatus.value = GameStatus.WON
                        dialog.value = DialogState.Congratulations
                    }
                    if (checkIfLost()) {
                        gameStatus.value = GameStatus.LOST
                        dialog.value = DialogState.GameOver
                    }
                }

                ApiResponse.Error -> {
                    error.value = R.string.error_message
                }
            }
        }
    }

    private fun checkIfLost() =
        wordAttempts.value.size == ATTEMPTS &&
                wordAttempts.value.last().correctLetters.all {
                    it.second != LetterStatus.CORRECT
                }

    private fun checkIfWon() = wordAttempts.value.lastOrNull()?.correctLetters?.all {
        it.second == LetterStatus.CORRECT
    } ?: false

    fun onStatisticsDialogClicked(token: String) {
        viewModelScope.launch {

            when (val outcome = getUserStatistics(token = token)) {

                is ApiResponse.Complete -> {
                    val statistics = outcome.value
                    dialog.value = DialogState.StatisticsDialogState(
                        gamesPlayed = "${statistics.gamesPlayed}",
                        averageAttempts = "${statistics.averageAttempts}",
                        gamesWon = "${statistics.gamesWon}",
                        winPercentage = "${statistics.winPercentage}%"
                    )
                }

                is ApiResponse.Error -> {
                    error.value = R.string.failed_to_load_statistics
                }
            }

        }
    }

    fun onDismissDialog() {
        dialog.value = null
    }

    private fun updateLetterInputState(newWord: WordState) =
        newWord.correctLetters.forEach { (newLetter, newStatus) ->
            letterInputState.value.computeIfPresent(newLetter.uppercase(Locale.ROOT)) { _, currentStatus ->
                newStatus.takeIf {
                    it != currentStatus &&
                            (currentStatus == LetterStatus.DEFAULT && it == LetterStatus.NOT_CORRECT) ||
                            (currentStatus == LetterStatus.DEFAULT && it == LetterStatus.PARTIALLY_CORRECT) ||
                            (currentStatus == LetterStatus.DEFAULT && it == LetterStatus.CORRECT) ||
                            (currentStatus == LetterStatus.PARTIALLY_CORRECT && it == LetterStatus.CORRECT)
                } ?: currentStatus
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
        wordExamples: WordAttempts,
        dialogState: DialogState?,
        gameStatus: GameStatus
    ): ZborleState {
        val overallAttempts = wordAttempts.toMutableList()

        if (currentWord.word.isNotEmpty()) {
            val newWord = WordState(
                currentWord.word,
                currentWord.word.map { it.toString() to LetterStatus.DEFAULT }
            )
            overallAttempts.add(newWord)
        }

        val emptyRow = WordState("", List(5) { "" to LetterStatus.DEFAULT })

        while (overallAttempts.size < 6) {
            overallAttempts.add(emptyRow)
        }

        return ZborleState(
            wordAttempts = overallAttempts,
            gameStatus = gameStatus,
            letterInputState = letterInputState.toList(),
            wordExamples = wordExamples,
            dialog = dialogState
        )
    }
}

