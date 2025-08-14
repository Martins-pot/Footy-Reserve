package com.mertswork.footyreserve.core.domain.storage

import com.mertswork.footyreserve.core.data.model.AuthData
import com.mertswork.footyreserve.core.data.model.User

interface UserStorage {
    // Token operations
    suspend fun saveAuthData(authData: AuthData)
    suspend fun getAuthData(): AuthData?
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?

    // User operations
    suspend fun getCurrentUser(): User?
    suspend fun updateUser(user: User)

    // Clear operations
    suspend fun clearAuthData()
    suspend fun isLoggedIn(): Boolean
}