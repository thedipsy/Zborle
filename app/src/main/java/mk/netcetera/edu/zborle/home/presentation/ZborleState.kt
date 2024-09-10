package mk.netcetera.edu.zborle.home.presentation

import androidx.annotation.StringRes

/**
 * Represents the state of a word in the Zborle game.
 *
 * @property word The current word being guessed.
 * @property correctLetters A list containing guessed letters and their [LetterStatus].
 */
data class WordState(
    val word: String,
    val correctLetters: List<Pair<String, LetterStatus>>
)

/**
 * Represents the current word state in the Zborle game.
 *
 * @property word The current word being guessed.
 * @property isComplete A boolean indicating whether the word has been completely inserted.
 */
data class CurrentWordState(
    val word: String,
    val isComplete: Boolean
)

/**
 * Type alias representing a list of word attempts.
 */
typealias WordAttempts = List<WordState>

/**
 * Type alias representing a list of word examples.
 */
typealias WordExamples = List<WordState>


/**
 * Represents the state of a Zborle game.
 *
 * @property wordExamples A list of example words used in the 'How to play dialog'.
 * @property wordAttempts A list of word attempts made by the player.
 * @property gameStatus The current status of the game represented by [GameStatus].
 * @property letterInputState A list containing letters entered by the player and their [LetterStatus].
 * @property dialog The current dialog state, which could be null or one of the [DialogState]s.
 * @property errorId An optional string resource for an errorId message.
 */
data class ZborleState(
    val wordExamples: WordExamples,
    val wordAttempts: WordAttempts,
    val gameStatus: GameStatus,
    val letterInputState: List<Pair<String, LetterStatus>>,
    val dialog: DialogState?,
    @StringRes val errorId: Int? = null
)

/**
 * Enum representing the possible statuses of the Zborle game.
 */
enum class GameStatus {

    /** Indicates the game is still in progress. */
    IN_PROGRESS,

    /** Indicates the game has been won by the player. */
    WON,


    /** Indicates the game has been lost by the player. */
    LOST
}

/**
 * Enum representing the possible statuses of a letter in the Zborle game.
 */
enum class LetterStatus {

    /**
     * Indicates that the letter is correctly placed.
     */
    CORRECT,

    /**
     * Indicates that the letter is correct but it is in the wrong position.
     */
    PARTIALLY_CORRECT,

    /**
     * Indicates that the letter is incorrect.
     */
    NOT_CORRECT,

    /**
     * Indicates neutral status of the letter.
     */
    DEFAULT;

    /**
     * Maps the status to [LetterStatus].
     */
    companion object {
        fun getStatus(status: String) = entries.find { it.name == status } ?: DEFAULT
    }

}


/**
 * Sealed interface representing different dialog states in the Zborle game.
 */
sealed interface DialogState {
    /** Represents the game-over dialog state. */
    data object GameOver : DialogState

    /** Represents the dialog state when the word has already been guessed. */
    data object AlreadyGuessed : DialogState

    /** Represents the congratulations dialog state for winning the game. */
    data object Congratulations : DialogState

    /**
     * Represents the state of the statistics dialog, showing game performance details.
     *
     * @property gamesPlayed The number of games played.
     * @property gamesWon The number of games won.
     * @property winPercentage The win percentage of the player.
     * @property averageAttempts The average number of attempts per game.
     */
    data class StatisticsDialogState(
        val gamesPlayed: String? = null,
        val gamesWon: String? = null,
        val winPercentage: String? = null,
        val averageAttempts: String? = null
    ) : DialogState
}

