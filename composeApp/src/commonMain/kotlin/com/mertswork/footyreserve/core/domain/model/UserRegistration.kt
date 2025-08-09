package com.mertswork.footyreserve.core.domain.model


data class UserRegistration(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val country: String = "",
    val imageUri: String? = null
)