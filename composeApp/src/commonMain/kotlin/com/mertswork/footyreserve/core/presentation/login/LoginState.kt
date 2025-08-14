package com.mertswork.footyreserve.core.presentation.login

import com.mertswork.footyreserve.core.data.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)