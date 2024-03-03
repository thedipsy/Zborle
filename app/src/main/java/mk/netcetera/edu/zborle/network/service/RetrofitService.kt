//package mk.netcetera.edu.zborle.network.service
//
//import com.google.gson.GsonBuilder
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class RetrofitService {
//
//  companion object {
//
//    @Volatile
//    private var INSTANCE: WeatherApi? = null
//
//    @JvmStatic
//    fun api(): WeatherApi {
//      return INSTANCE ?: synchronized(this) {
//        val instance = createWeatherApi()
//        INSTANCE = instance
//        instance
//      }
//    }
//
//    private const val BASE_URL = "https://api.openweathermap.org/data/"
//
//    private fun createWeatherApi(): WeatherApi {
//      val gson = GsonBuilder()
//        .setLenient()
//        .create()
//      val gsonConverterFactory = GsonConverterFactory.create(gson)
//
//      return Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(gsonConverterFactory)
//        .build()
//        .create(WeatherApi::class.java)
//    }
//  }
//}