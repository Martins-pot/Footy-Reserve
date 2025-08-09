package com.mertswork.footyreserve.di.utils

import com.mertswork.footyreserve.core.data.api.createHttpClient
import com.mertswork.footyreserve.core.data.repository.UserRepositoryImpl
import com.mertswork.footyreserve.core.domain.repository.UserRepository
import com.mertswork.footyreserve.core.presentation.viewmodel.RegistrationViewModel


object AppModule {
    private val httpClient by lazy { createHttpClient() }

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(httpClient)
    }

    fun provideRegistrationViewModel(): RegistrationViewModel {
        return RegistrationViewModel(userRepository)
    }
}