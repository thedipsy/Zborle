package mk.netcetera.edu.zborle.utils

import mk.netcetera.edu.zborle.network.service.ApiResponse
import retrofit2.Response

suspend fun <T> backendCall(apiCall: suspend () -> Response<T>) : ApiResponse<T> {
    return try {
        apiCall().let { response ->
            return if (response.isSuccessful) {
                response.body()?.let { result ->
                    ApiResponse.Complete(result)
                } ?: ApiResponse.Error
            } else {
                ApiResponse.Error
            }
        }
    } catch (e: Exception) {
        ApiResponse.Error
    }
}