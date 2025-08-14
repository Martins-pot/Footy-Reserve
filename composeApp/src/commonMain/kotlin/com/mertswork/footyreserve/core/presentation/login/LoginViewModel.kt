package com.mertswork.footyreserve.core.presentation.login

import com.mertswork.footyreserve.core.domain.repository.AuthRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepositoryInterface
) {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    suspend fun login() {
        val currentState = _uiState.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Please fill all fields")
            return
        }

        _uiState.value = currentState.copy(isLoading = true, errorMessage = null)


        try {
            val result = authRepository.login(currentState.email, currentState.password)

            result.fold(
                onSuccess = { response ->
//                    authRepository.saveTokens(response.accessToken, response.refreshToken)
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        isLoginSuccessful = true,
                        user = response.user
                    )
                },
                onFailure = { exception ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Login failed"
                    )
                }
            )
        }
        catch (e : Exception){
            _uiState.value = currentState.copy(
                isLoading = false,
                errorMessage = e.message ?: "Login failed, Please try again.")
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}