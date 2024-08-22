package mk.netcetera.edu.zborle.network.service

sealed interface ApiResponse<out T> {

  object Error : ApiResponse<Nothing>

  data class Complete<T>(val value: T) : ApiResponse<T>

}