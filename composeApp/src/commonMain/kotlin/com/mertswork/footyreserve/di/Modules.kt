package com.mertswork.footyreserve.di




import com.mertswork.footyreserve.core.data.HttpClientFactory

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

enum class Platform {
    ANDROID, IOS, UNKNOWN
}

expect val platforModule : Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }






}