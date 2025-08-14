package com.mertswork.footyreserve.core.data.remote

import com.mertswork.footyreserve.core.data.model.LoginRequest
import com.mertswork.footyreserve.core.data.model.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApiService(private val httpClient: HttpClient) {

    suspend fun login(request: LoginRequest): LoginResponse {
        return httpClient.post("https://footy-reserve-api.onrender.com/user/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}