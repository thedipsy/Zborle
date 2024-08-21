package mk.netcetera.edu.zborle.home.domain

import android.content.Context
import mk.netcetera.edu.zborle.data.TokenManager
import mk.netcetera.edu.zborle.network.models.response.StatisticsResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class GetUserStatisticsUseCase(
    private val context: Context,
    private val api: ZborleApi = RetrofitService.apiService
) {

    suspend operator fun invoke(): ApiResponse<StatisticsResponse> {
        val token = TokenManager.getToken(context)
        return backendCall { api.getStatistics(token) }
    }
}