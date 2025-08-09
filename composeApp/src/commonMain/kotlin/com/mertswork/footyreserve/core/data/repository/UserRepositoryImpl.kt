package com.mertswork.footyreserve.core.data.repository


import com.mertswork.footyreserve.core.data.model.ApiResponse
import com.mertswork.footyreserve.core.data.model.RegisterUserJson
import com.mertswork.footyreserve.core.data.model.UserData
import com.mertswork.footyreserve.core.domain.model.UserRegistration
import com.mertswork.footyreserve.core.domain.repository.UserRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UserRepositoryImpl(
    private val httpClient: HttpClient
) : UserRepository {

    override suspend fun registerUser(userInfo: UserRegistration): ApiResponse<UserData> {
        // Get image bytes first (inside coroutine)
        val imageBytes = userInfo.imageUri?.let { uri ->
            getImageBytes(uri)
        }

        val response = httpClient.submitFormWithBinaryData(
            url = "https://footy-reserve-api.onrender.com/user/signup",
            formData = formData {
                append("firstName", userInfo.firstName)
                append("lastName", userInfo.lastName)
                append("email", userInfo.email)
                append("password", userInfo.password)
                append("country", userInfo.country)

                imageBytes?.let {
                    append("image", it, Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(HttpHeaders.ContentDisposition, "filename=\"profile.jpg\"")
                    })
                }
            }
        )

        return response.body()
    }



//    @OptIn(ExperimentalEncodingApi::class)
//    override suspend fun registerUser(userInfo: UserRegistration): ApiResponse<UserData> {
//        val imageBytes = userInfo.imageUri?.let { uri ->
//            getImageBytes(uri)
//        }
//
//        val requestBody = RegisterUserJson(
//            firstName = userInfo.firstName,
//            lastName = userInfo.lastName,
//            email = userInfo.email,
//            password = userInfo.password,
//            country = userInfo.country,
//            imageBase64 = imageBytes
//        )
//
//        val response: HttpResponse = httpClient.post("https://footy-reserve-api.onrender.com/user/signup") {
//            contentType(ContentType.Application.Json)
//            setBody(requestBody)
//        }
//
//        if (response.status.isSuccess()) {
//            return response.body()
//        } else {
//            throw Exception("Server error: ${response.status}, ${response.bodyAsText()}")
//        }
//    }





//    override suspend fun registerUser(userInfo: UserRegistration): ApiResponse<UserData> {
//        val response = httpClient.submitFormWithBinaryData(
//            url = "https://footy-reserve-api.onrender.com/user/signup",
//            formData = formData {
//                append("firstName", userInfo.firstName)
//                append("lastName", userInfo.lastName)
//                append("email", userInfo.email)
//                append("password", userInfo.password)
//                append("country", userInfo.country)
//
//
//                userInfo.imageUri?.let { uri ->
//                    val imageBytes = getImageBytes(uri)
//                    append("file", imageBytes, Headers.build {
//                        append(HttpHeaders.ContentType, "image/*")
//                        append(HttpHeaders.ContentDisposition, "filename=\"profile.jpg\"")
//                    })
//                }
//            }
//        )
//
//        return response.body()
//    }
}

// This should be implemented in platform-specific modules
expect suspend fun getImageBytes(uri: String): ByteArray
