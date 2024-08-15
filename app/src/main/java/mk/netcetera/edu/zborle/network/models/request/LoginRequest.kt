package mk.netcetera.edu.zborle.network.models.request

data class LoginRequest(
  val email: String,
  val password: String
)