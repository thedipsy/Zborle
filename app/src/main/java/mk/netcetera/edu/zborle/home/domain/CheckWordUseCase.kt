package mk.netcetera.edu.zborle.home.domain

import android.content.Context
import mk.netcetera.edu.zborle.data.TokenManager
import mk.netcetera.edu.zborle.network.models.request.CheckWordRequest
import mk.netcetera.edu.zborle.network.models.response.CheckWordResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class CheckWordUseCase(
    private val context: Context,
    private val api: ZborleApi = RetrofitService.apiService
) {

    suspend operator fun invoke(guess: String): ApiResponse<CheckWordResponse> {
        val token = TokenManager.getToken(context)
        val request = CheckWordRequest(guess = guess.toLowerCase())
        return backendCall { api.checkWord(token = token, request = request) }
    }
}