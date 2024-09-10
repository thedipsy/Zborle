package mk.netcetera.edu.zborle.login.domain

import mk.netcetera.edu.zborle.network.models.request.LoginRequest
import mk.netcetera.edu.zborle.network.models.response.AuthenticationResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService.apiService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class LoginUseCase(private val api: ZborleApi = apiService) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): ApiResponse<AuthenticationResponse> {
        val request = LoginRequest(email, password)
        return backendCall { api.login(request = request) }
    }
}