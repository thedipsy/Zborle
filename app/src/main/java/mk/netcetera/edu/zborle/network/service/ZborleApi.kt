package mk.netcetera.edu.zborle.network.service

import mk.netcetera.edu.zborle.network.models.request.LoginRequest
import mk.netcetera.edu.zborle.network.models.request.RegisterRequest
import mk.netcetera.edu.zborle.network.models.response.AuthenticationResponse
import mk.netcetera.edu.zborle.network.models.response.StatisticsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ZborleApi {

  @POST("auth/register")
  suspend fun register(@Body request: RegisterRequest) : Response<AuthenticationResponse>

  @POST("auth/login")
  suspend fun login(@Body request: LoginRequest) : Response<AuthenticationResponse>

  @GET("game/user-statistics")
  suspend fun getStatistics() : Response<StatisticsResponse>
}