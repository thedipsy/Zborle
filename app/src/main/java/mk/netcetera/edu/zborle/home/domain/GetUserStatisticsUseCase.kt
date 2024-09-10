package mk.netcetera.edu.zborle.home.domain

import mk.netcetera.edu.zborle.network.models.response.StatisticsResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class GetUserStatisticsUseCase(
    private val api: ZborleApi = RetrofitService.apiService
) {

    suspend operator fun invoke(token: String): ApiResponse<StatisticsResponse> {
        return backendCall { api.getStatistics(token) }
    }
}