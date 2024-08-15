package mk.netcetera.edu.zborle.network.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthenticationResponse(
  @SerializedName("access_token")
  val accessToken: String,
  @SerializedName("refresh_token")
  val refreshToken: String
): Serializable