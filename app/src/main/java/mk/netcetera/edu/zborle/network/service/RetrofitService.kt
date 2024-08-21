package mk.netcetera.edu.zborle.network.service

import android.content.Context
import mk.netcetera.edu.zborle.data.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

  private const val BASE_URL = "http://10.0.2.2:8080/api/"

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val apiService by lazy {
    retrofit.create(ZborleApi::class.java)
  }
}


