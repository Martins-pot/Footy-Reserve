package com.mertswork.footyreserve.di




import com.mertswork.footyreserve.core.data.HttpClientFactory
import com.mertswork.footyreserve.core.data.remote.AuthApiService
import com.mertswork.footyreserve.core.data.repository.AuthRepository
import com.mertswork.footyreserve.core.domain.manager.UserManager
import com.mertswork.footyreserve.core.domain.repository.AuthRepositoryInterface
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import com.mertswork.footyreserve.core.presentation.login.LoginViewModel

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

enum class Platform {
    ANDROID, IOS, UNKNOWN
}

expect val platforModule : Module

val sharedModule = module {
    // NEtwork
    single { HttpClientFactory.create(get()) }
    single { AuthApiService(get()) }

    // Storage & Manager
    single { UserManager(get<UserStorage>()) }

    // Repository
    single<AuthRepositoryInterface> { AuthRepository(get(), get()) }

    // ViewModels
    factory { LoginViewModel(get()) }






}