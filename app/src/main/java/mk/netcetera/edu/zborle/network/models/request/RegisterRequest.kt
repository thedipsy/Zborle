package mk.netcetera.edu.zborle.network.models.request

data class RegisterRequest(
  val firstname: String,
  val lastname: String,
  val email: String,
  val password: String,
  val role: String
)