package com.mertswork.footyreserve.core.data.repository

import com.mertswork.footyreserve.core.data.model.LoginRequest
import com.mertswork.footyreserve.core.data.model.LoginResponse
import com.mertswork.footyreserve.core.data.model.User
import com.mertswork.footyreserve.core.data.remote.AuthApiService
import com.mertswork.footyreserve.core.domain.manager.UserManager
import com.mertswork.footyreserve.core.domain.repository.AuthRepositoryInterface
import com.mertswork.footyreserve.core.domain.storage.TokenStorage


class AuthRepository(
    private val apiService: AuthApiService,
    private val userManager: UserManager
) : AuthRepositoryInterface {

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)

            // Save user data using UserManager
            userManager.loginUser(response)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccessToken(): String? {
        return userManager.getAccessToken()
    }

    override suspend fun logout() {
        userManager.logout()
    }

    override suspend fun getCurrentUser(): User? {
        return userManager.currentUser.value
    }

    override suspend fun isLoggedIn(): Boolean {
        return userManager.checkAuthStatus()
    }
}
//class AuthRepository(
//    private val apiService: AuthApiService,
//    private val tokenStorage: TokenStorage
//) : AuthRepositoryInterface {
//
//    override suspend fun login(email: String, password: String): Result<LoginResponse> {
//        return try {
//            val request = LoginRequest(email, password)
//            val response = apiService.login(request)
//            Result.success(response)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
//        tokenStorage.saveTokens(accessToken, refreshToken)
//    }
//
//    override suspend fun getAccessToken(): String? {
//        return tokenStorage.getAccessToken()
//    }
//
//    override suspend fun logout() {
//        tokenStorage.clearTokens()
//    }
//}