package com.mertswork.footyreserve.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mertswork.footyreserve.core.data.model.AuthData
import com.mertswork.footyreserve.core.data.model.User
import com.mertswork.footyreserve.core.domain.storage.TokenStorage
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AndroidUserStorage(context: Context) : UserStorage {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "user_data",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun saveAuthData(authData: AuthData) {
        val authDataJson = json.encodeToString(authData)
        sharedPreferences.edit()
            .putString(AUTH_DATA_KEY, authDataJson)
            .putString(ACCESS_TOKEN_KEY, authData.accessToken)
            .putString(REFRESH_TOKEN_KEY, authData.refreshToken)
            .putBoolean(IS_LOGGED_IN_KEY, true)
            .apply()
    }

    override suspend fun getAuthData(): AuthData? {
        val authDataJson = sharedPreferences.getString(AUTH_DATA_KEY, null)
        return if (authDataJson != null) {
            try {
                json.decodeFromString<AuthData>(authDataJson)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    override suspend fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    override suspend fun getCurrentUser(): User? {
        return getAuthData()?.user
    }

    override suspend fun updateUser(user: User) {
        val currentAuthData = getAuthData()
        if (currentAuthData != null) {
            val updatedAuthData = currentAuthData.copy(user = user)
            saveAuthData(updatedAuthData)
        }
    }

    override suspend fun clearAuthData() {
        sharedPreferences.edit()
            .remove(AUTH_DATA_KEY)
            .remove(ACCESS_TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .putBoolean(IS_LOGGED_IN_KEY, false)
            .apply()
    }

    override suspend fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false) &&
                !getAccessToken().isNullOrBlank()
    }

    companion object {
        private const val AUTH_DATA_KEY = "auth_data"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val IS_LOGGED_IN_KEY = "is_logged_in"
    }
}



//class AndroidTokenStorage(context: Context) : TokenStorage {
//
//    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//
//    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
//        "auth_tokens",
//        masterKeyAlias,
//        context,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
//
//    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
//        sharedPreferences.edit()
//            .putString(ACCESS_TOKEN_KEY, accessToken)
//            .putString(REFRESH_TOKEN_KEY, refreshToken)
//            .apply()
//    }
//
//    override suspend fun getAccessToken(): String? {
//        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
//    }
//
//    override suspend fun getRefreshToken(): String? {
//        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
//    }
//
//    override suspend fun clearTokens() {
//        sharedPreferences.edit()
//            .remove(ACCESS_TOKEN_KEY)
//            .remove(REFRESH_TOKEN_KEY)
//            .apply()
//    }
//
//    companion object {
//        private const val ACCESS_TOKEN_KEY = "access_token"
//        private const val REFRESH_TOKEN_KEY = "refresh_token"
//    }
//}