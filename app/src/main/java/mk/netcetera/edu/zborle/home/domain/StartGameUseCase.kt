package mk.netcetera.edu.zborle.home.domain

import mk.netcetera.edu.zborle.network.models.response.StartGameResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class StartGameUseCase(
    private val api: ZborleApi = RetrofitService.apiService
) {

    suspend operator fun invoke(token: String): ApiResponse<StartGameResponse> {
        return backendCall { api.startGame(token) }
    }
}