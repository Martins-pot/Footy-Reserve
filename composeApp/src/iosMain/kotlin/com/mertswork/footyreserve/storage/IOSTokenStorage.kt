package com.mertswork.footyreserve.storage

import com.mertswork.footyreserve.core.data.model.AuthData
import com.mertswork.footyreserve.core.data.model.User
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults
import platform.Security.*



class IOSUserStorage : UserStorage {

    private val json = Json { ignoreUnknownKeys = true }
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override suspend fun saveAuthData(authData: AuthData) {
        // Save full auth data to UserDefaults (for non-sensitive data)
        val authDataJson = json.encodeToString(authData)
        userDefaults.setObject(authDataJson, AUTH_DATA_KEY)

        // Save tokens securely in Keychain
        saveToKeychain(ACCESS_TOKEN_KEY, authData.accessToken)
        saveToKeychain(REFRESH_TOKEN_KEY, authData.refreshToken)

        // Save login status
        userDefaults.setBool(true, IS_LOGGED_IN_KEY)
        userDefaults.synchronize()
    }

    override suspend fun getAuthData(): AuthData? {
        val authDataJson = userDefaults.stringForKey(AUTH_DATA_KEY)
        return if (authDataJson != null) {
            try {
                val authData = json.decodeFromString<AuthData>(authDataJson)
                // Update tokens from keychain (more secure)
                val accessToken = getFromKeychain(ACCESS_TOKEN_KEY)
                val refreshToken = getFromKeychain(REFRESH_TOKEN_KEY)

                if (accessToken != null && refreshToken != null) {
                    authData.copy(
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    )
                } else {
                    authData
                }
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        return getFromKeychain(ACCESS_TOKEN_KEY)
    }

    override suspend fun getRefreshToken(): String? {
        return getFromKeychain(REFRESH_TOKEN_KEY)
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
        // Clear UserDefaults
        userDefaults.removeObjectForKey(AUTH_DATA_KEY)
        userDefaults.setBool(false, IS_LOGGED_IN_KEY)
        userDefaults.synchronize()

        // Clear Keychain
        deleteFromKeychain(ACCESS_TOKEN_KEY)
        deleteFromKeychain(REFRESH_TOKEN_KEY)
    }

    override suspend fun isLoggedIn(): Boolean {
        return userDefaults.boolForKey(IS_LOGGED_IN_KEY) &&
                !getAccessToken().isNullOrBlank()
    }

    private fun saveToKeychain(key: String, value: String) {
        val data = value.encodeToByteArray().toNSData()

        val query = mapOf<Any?, Any?>(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecValueData to data
        )

        // Delete existing item
        SecItemDelete(query as CFDictionaryRef)

        // Add new item
        SecItemAdd(query as CFDictionaryRef, null)
    }

    private fun getFromKeychain(key: String): String? {
        val query = mapOf<Any?, Any?>(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne
        )

        val result = memScoped {
            val ref = alloc<CFTypeRefVar>()
            val status = SecItemCopyMatching(query as CFDictionaryRef, ref.ptr)

            if (status == errSecSuccess) {
                val data = ref.value as NSData
                data.toByteArray().decodeToString()
            } else {
                null
            }
        }

        return result
    }

    private fun deleteFromKeychain(key: String) {
        val query = mapOf<Any?, Any?>(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to key
        )

        SecItemDelete(query as CFDictionaryRef)
    }

    companion object {
        private const val AUTH_DATA_KEY = "auth_data"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val IS_LOGGED_IN_KEY = "is_logged_in"
    }
}

// Helper extension for NSData conversion
fun ByteArray.toNSData(): NSData {
    return NSData.create(bytes = this.refTo(0), length = this.size.toULong())
}

fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), bytes, length)
        }
    }
}
//class IOSTokenStorage : TokenStorage {
//
//    private val userDefaults = NSUserDefaults.standardUserDefaults
//
//    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
//        saveToKeychain(ACCESS_TOKEN_KEY, accessToken)
//        saveToKeychain(REFRESH_TOKEN_KEY, refreshToken)
//    }
//
//    override suspend fun getAccessToken(): String? {
//        return getFromKeychain(ACCESS_TOKEN_KEY)
//    }
//
//    override suspend fun getRefreshToken(): String? {
//        return getFromKeychain(REFRESH_TOKEN_KEY)
//    }
//
//    override suspend fun clearTokens() {
//        deleteFromKeychain(ACCESS_TOKEN_KEY)
//        deleteFromKeychain(REFRESH_TOKEN_KEY)
//    }
//
//    private fun saveToKeychain(key: String, value: String) {
//        val query = mapOf<Any?, Any?>(
//            kSecClass to kSecClassGenericPassword,
//            kSecAttrAccount to key,
//            kSecValueData to value.encodeToByteArray().toNSData()
//        )
//
//        SecItemDelete(query as CFDictionaryRef)
//        SecItemAdd(query as CFDictionaryRef, null)
//    }
//
//    private fun getFromKeychain(key: String): String? {
//        val query = mapOf<Any?, Any?>(
//            kSecClass to kSecClassGenericPassword,
//            kSecAttrAccount to key,
//            kSecReturnData to kCFBooleanTrue,
//            kSecMatchLimit to kSecMatchLimitOne
//        )
//
//        val result = SecItemCopyMatching(query as CFDictionaryRef, null)
//        return if (result == errSecSuccess) {
//            // Convert NSData to String (implementation depends on your setup)
//            // This is a simplified version
//            null // Return actual converted string
//        } else {
//            null
//        }
//    }
//
//    private fun deleteFromKeychain(key: String) {
//        val query = mapOf<Any?, Any?>(
//            kSecClass to kSecClassGenericPassword,
//            kSecAttrAccount to key
//        )
//
//        SecItemDelete(query as CFDictionaryRef)
//    }
//
//    companion object {
//        private const val ACCESS_TOKEN_KEY = "access_token"
//        private const val REFRESH_TOKEN_KEY = "refresh_token"
//    }
//}