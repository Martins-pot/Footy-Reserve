package com.mertswork.footyreserve.di

import com.mertswork.footyreserve.core.domain.storage.TokenStorage
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import com.mertswork.footyreserve.storage.AndroidUserStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platforModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        // User Storage - MUST be available before UserManager
        single<UserStorage> { AndroidUserStorage(androidContext()) }


        // single<ImagePicker> { AndroidImagePicker(androidContext()) }
//        single<ImagePicker> { (activity: ComponentActivity) -> AndroidImagePicker(activity) }


    }