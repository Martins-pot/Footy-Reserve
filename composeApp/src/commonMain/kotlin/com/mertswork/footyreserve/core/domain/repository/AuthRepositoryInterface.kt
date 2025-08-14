package com.mertswork.footyreserve.core.domain.repository

import com.mertswork.footyreserve.core.data.model.LoginResponse
import com.mertswork.footyreserve.core.data.model.User



interface AuthRepositoryInterface {
    suspend fun login(email: String, password: String): Result<LoginResponse>
    suspend fun getAccessToken(): String?
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    suspend fun isLoggedIn(): Boolean
}

//interface AuthRepositoryInterface {
//    suspend fun login(email: String, password: String): Result<LoginResponse>
//    suspend fun saveTokens(accessToken: String, refreshToken: String)
//    suspend fun getAccessToken(): String?
//    suspend fun logout()
//}