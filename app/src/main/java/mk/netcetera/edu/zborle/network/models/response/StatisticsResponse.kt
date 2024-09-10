package mk.netcetera.edu.zborle.network.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StatisticsResponse(
    @SerializedName("games_played")
    val gamesPlayed: Int,
    @SerializedName("games_won")
    val gamesWon: Int,
    @SerializedName("win_percentage")
    val winPercentage: Double,
    @SerializedName("average_attempts")
    val averageAttempts: Double
): Serializable