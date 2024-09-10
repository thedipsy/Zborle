package mk.netcetera.edu.zborle.network.models.response

import com.google.gson.annotations.SerializedName

data class CheckWordResponse(
    @SerializedName("current_response")
    val userGuessResponse: Array<UserGuessResponses>?,
    val message: String,
    val guessess: Array<Guess>
)

data class UserGuessResponses(
    val letter: String,
    val answer: String,
    @SerializedName("character_order")
    val characterOrder: String
)

data class Guess(
    val guess: String,
    @SerializedName("status")
    val userGuessResponses: Array<UserGuessResponses>
)