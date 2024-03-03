package mk.netcetera.edu.zborle.common.presentation

import androidx.annotation.StringRes

data class TextField(
  val text: String,
  @StringRes val errorMessageId: Int? = null
) {

  fun isValid() = errorMessageId == null
}