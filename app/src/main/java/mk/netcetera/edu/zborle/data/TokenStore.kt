package mk.netcetera.edu.zborle.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenManager {

    private const val JWT_TOKEN = "jwt_token"
    private const val BEARER = "Bearer"
    private const val FILE_NAME = "encrypted_prefs"

    fun storeToken(context: Context, token: String) {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        sharedPreferences.edit().putString(JWT_TOKEN, token).apply()
    }

    fun getToken(context: Context): String {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val token = sharedPreferences.getString(JWT_TOKEN, "").orEmpty()
        return "$BEARER $token"
    }

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}