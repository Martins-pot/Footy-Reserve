package com.mertswork.footyreserve.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platforModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
//        single<ImagePicker> { AndroidImagePicker(androidContext()) }
//        single<ImagePicker> { (activity: ComponentActivity) -> AndroidImagePicker(activity) }


    }