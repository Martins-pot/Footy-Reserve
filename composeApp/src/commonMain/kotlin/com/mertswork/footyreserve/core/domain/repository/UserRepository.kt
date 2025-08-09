package com.mertswork.footyreserve.core.domain.repository

import com.mertswork.footyreserve.core.data.model.ApiResponse
import com.mertswork.footyreserve.core.data.model.UserData
import com.mertswork.footyreserve.core.domain.model.UserRegistration


interface UserRepository {
    suspend fun registerUser(userInfo: UserRegistration): ApiResponse<UserData>
}