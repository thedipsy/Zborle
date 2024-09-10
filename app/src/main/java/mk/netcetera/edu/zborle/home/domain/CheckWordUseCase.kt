package mk.netcetera.edu.zborle.home.domain

import mk.netcetera.edu.zborle.network.models.request.CheckWordRequest
import mk.netcetera.edu.zborle.network.models.response.CheckWordResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall
import java.util.Locale

class CheckWordUseCase(
    private val api: ZborleApi = RetrofitService.apiService
) {

    suspend operator fun invoke(guess: String, token: String): ApiResponse<CheckWordResponse> {
        val request = CheckWordRequest(guess = guess.lowercase(Locale.ROOT))
        return backendCall { api.checkWord(token = token, request = request) }
    }
}