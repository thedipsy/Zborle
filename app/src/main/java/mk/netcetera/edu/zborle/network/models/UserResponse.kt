package mk.netcetera.edu.zborle.network.models

import java.io.Serializable

data class User(
  val firstname: String,
  val lastname: String,
  val email: String,
  val password: String
) : Serializable
