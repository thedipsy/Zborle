package mk.netcetera.edu.zborle.register.domain

import mk.netcetera.edu.zborle.network.models.request.RegisterRequest
import mk.netcetera.edu.zborle.network.models.response.AuthenticationResponse
import mk.netcetera.edu.zborle.network.service.ApiResponse
import mk.netcetera.edu.zborle.network.service.RetrofitService
import mk.netcetera.edu.zborle.network.service.ZborleApi
import mk.netcetera.edu.zborle.utils.backendCall

class RegisterUserUseCase(private val api: ZborleApi = RetrofitService.apiService) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): ApiResponse<AuthenticationResponse> {
        val request = RegisterRequest(firstName, lastName, email, password, "USER")
        return backendCall {
            api.register(request = request)
        }
    }
}