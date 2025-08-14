package com.mertswork.footyreserve.core.domain.manager

import com.mertswork.footyreserve.core.data.model.AuthData
import com.mertswork.footyreserve.core.data.model.LoginResponse
import com.mertswork.footyreserve.core.data.model.User
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserManager(private val userStorage: UserStorage) {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _authData = MutableStateFlow<AuthData?>(null)
    val authData: StateFlow<AuthData?> = _authData.asStateFlow()

    // Initialize user manager - call this on app start
    suspend fun initialize() {
        val authData = userStorage.getAuthData()
        if (authData != null) {
            _authData.value = authData
            _currentUser.value = authData.user
            _isLoggedIn.value = true
        }
    }

    // Login and save user data
    suspend fun loginUser(loginResponse: LoginResponse) {
        val authData = loginResponse.toAuthData()
        userStorage.saveAuthData(authData)

        _authData.value = authData
        _currentUser.value = authData.user
        _isLoggedIn.value = true
    }

    // Update user info
    suspend fun updateUserInfo(user: User) {
        val currentAuthData = _authData.value
        if (currentAuthData != null) {
            val updatedAuthData = currentAuthData.copy(user = user)
            userStorage.saveAuthData(updatedAuthData)

            _authData.value = updatedAuthData
            _currentUser.value = user
        }
    }

    // Get access token
    suspend fun getAccessToken(): String? {
        return userStorage.getAccessToken()
    }

    // Get refresh token
    suspend fun getRefreshToken(): String? {
        return userStorage.getRefreshToken()
    }

    // Logout user
    suspend fun logout() {
        userStorage.clearAuthData()

        _authData.value = null
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    // Check if user is logged in
    suspend fun checkAuthStatus(): Boolean {
        val isLoggedIn = userStorage.isLoggedIn()
        _isLoggedIn.value = isLoggedIn

        if (isLoggedIn) {
            val authData = userStorage.getAuthData()
            _authData.value = authData
            _currentUser.value = authData?.user
        }

        return isLoggedIn
    }
}