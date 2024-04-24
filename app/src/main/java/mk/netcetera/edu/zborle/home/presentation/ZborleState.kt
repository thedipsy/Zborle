package mk.netcetera.edu.zborle.home.presentation

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

data class CurrentWordState(
  val word: String,
  val isComplete: Boolean
)

typealias WordAttempts = List<WordState>
typealias WordExamples = List<WordState>


/**
 * Represents the state of a Zborle game.
 *
 * @property gameState The [GameState] representing the overall game state.
 * @property letterInputState A HashMap containing entered letters and their [LetterStatus].
 */
data class ZborleState(
  val wordExamples: WordExamples,
  val wordAttempts: WordAttempts,
  val gameStatus: GameStatus,
  val letterInputState: List<Pair<String, LetterStatus>>
)

/**
 * Enum representing the possible statuses of the Zborle game.
 */
enum class GameStatus {
  IN_PROGRESS,
  WON,
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
  INCORRECT,

  DEFAULT
}