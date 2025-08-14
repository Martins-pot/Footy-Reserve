package com.mertswork.footyreserve.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val user: User
){
    fun toAuthData(): AuthData {
        return AuthData(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = user
        )
    }
}

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String,
    val image: String
)

@Serializable
data class AuthData(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)