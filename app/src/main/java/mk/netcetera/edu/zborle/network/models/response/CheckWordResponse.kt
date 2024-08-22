package mk.netcetera.edu.zborle.network.models.response

data class CheckWordResponse(
    val userGuessResponses: Array<UserGuessResponses>,
    val message: String,
    val guessess: Array<String>
)


data class UserGuessResponses(
    val letter: String,
    val answer: String,
    val characterOrder: String
)