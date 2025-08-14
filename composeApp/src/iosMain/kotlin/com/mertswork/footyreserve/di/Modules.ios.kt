package com.mertswork.footyreserve.di

import com.mertswork.footyreserve.core.domain.storage.TokenStorage
import com.mertswork.footyreserve.core.domain.storage.UserStorage
import com.mertswork.footyreserve.storage.IOSUserStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platforModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<UserStorage> { IOSUserStorage() }

    }