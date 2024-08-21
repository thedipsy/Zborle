package mk.netcetera.edu.zborle.home.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mk.netcetera.edu.zborle.home.domain.GetUserStatisticsUseCase
import mk.netcetera.edu.zborle.network.service.ApiResponse
import java.util.LinkedHashMap

class ZborleViewModel(context: Context) : ViewModel() {

    companion object {
        private const val ATTEMPTS = 6
        private val EMPTY_ROW = WordState("", List(5) { "" to LetterStatus.DEFAULT })

        fun provideFactory(
            context: Context,
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
                    return ZborleViewModel(context) as T
                }
            }
    }

    private val getWordExamples = GetWordExamples()

    private val wordExamples = getWordExamples()

    private val getUserStatistics by lazy { GetUserStatisticsUseCase(context) }

    private val currentWord: MutableStateFlow<CurrentWordState> =
        MutableStateFlow(CurrentWordState(word = "", isComplete = false))
    private val wordAttempts: MutableStateFlow<WordAttempts> = MutableStateFlow(listOf())
    private val letterInputState: MutableStateFlow<LinkedHashMap<String, LetterStatus>> =
        MutableStateFlow(getInitialLetterInputState())
    private val statisticsDialog = MutableStateFlow(StatisticsDialogState(show = false))


    val viewState = combine(
        currentWord,
        wordAttempts,
        letterInputState,
        statisticsDialog
    ) { currentWordState, wordAttempts, linkedHashMap, statisticsDialog ->
        createViewState(
            currentWordState,
            wordAttempts,
            linkedHashMap,
            wordExamples,
            statisticsDialog
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ZborleState(
            wordAttempts = List(6) { EMPTY_ROW },
            letterInputState = letterInputState.value.toList(),
            gameStatus = GameStatus.IN_PROGRESS,
            wordExamples = wordExamples,
            statisticsDialogState = statisticsDialog.value
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

    fun onStatisticsDialogClicked() {
        viewModelScope.launch {

            when (val outcome = getUserStatistics()) {
                is ApiResponse.Loading -> { /* do nothing */
                }

                is ApiResponse.Complete -> {
                    statisticsDialog.update {
                        val statistics = outcome.value
                        it.copy(
                            show = true,
                            gamesPlayed = "${statistics.gamesPlayed}",
                            averageAttempts = "${statistics.averageAttempts}",
                            gamesWon = "${statistics.gamesWon}",
                            winPercentage = "${statistics.winPercentage}%"
                        )
                    }
                }

                is ApiResponse.Error -> {

                }
            }

        }
    }

    fun onDismissStatisticsDialog() {
        statisticsDialog.update {
            it.copy(show = false)
        }
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
        wordExamples: WordAttempts,
        statisticsDialogState: StatisticsDialogState
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
            wordExamples = wordExamples,
            statisticsDialogState = statisticsDialogState
        )
    }
}

