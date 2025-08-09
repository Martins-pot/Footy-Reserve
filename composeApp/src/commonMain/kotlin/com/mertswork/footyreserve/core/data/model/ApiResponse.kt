package com.mertswork.footyreserve.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: String,
    val user: T? = null
)

@Serializable
data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String,
    val image: String,
    @SerialName("_id") val id: String,
    val createdAt: String
)


@Serializable
data class RegisterUserJson(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val country: String,
    val imageBase64: ByteArray? = null
)
