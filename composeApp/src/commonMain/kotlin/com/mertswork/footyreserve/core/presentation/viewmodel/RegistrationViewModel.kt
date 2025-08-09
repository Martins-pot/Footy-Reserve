package com.mertswork.footyreserve.core.presentation.viewmodel


import com.mertswork.footyreserve.core.data.model.UserData
import com.mertswork.footyreserve.core.domain.model.UserRegistration
import com.mertswork.footyreserve.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegistrationViewModel(
    private val userRepository: UserRepository
) {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    suspend fun registerUser(userInfo: UserRegistration) {
        if (!isFormValid(userInfo)) {
            _uiState.value = _uiState.value.copy(
                error = "Please fill in all required fields"
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        try {
            val result = userRepository.registerUser(userInfo)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                successMessage = result.message,
                registeredUser = result.user
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message ?: "Registration failed. Please try again."
            )
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            error = null,
            successMessage = null
        )
    }

    private fun isFormValid(userInfo: UserRegistration): Boolean {
        return userInfo.firstName.isNotBlank() &&
                userInfo.lastName.isNotBlank() &&
                userInfo.email.isNotBlank() &&
                userInfo.password.isNotBlank() &&
                userInfo.country.isNotBlank() &&
                isValidEmail(userInfo.email)
    }
}

expect fun isValidEmail(email: String): Boolean

data class RegistrationUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val registeredUser: UserData? = null
)
