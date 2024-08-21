package mk.netcetera.edu.zborle.network.models.response

import java.io.Serializable

data class StatisticsResponse(
    val gamesPlayed: Int,
    val gamesWon: Int,
    val winPercentage: Int,
    val averageAttempts: Int
): Serializable