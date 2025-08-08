package com.mertswork.footyreserve.di

import com.mertswork.footyreserve.home.data.dto.ImagePicker
import com.mertswork.footyreserve.utils.IOSImagePicker
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platforModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
//        single { HttpClient(get()) } // Provide HttpClient using the above engine

        single { KtorRemoteFieldReserveDataSource(get()) } // Provide the remote data source

        single<ImagePicker> { IOSImagePicker() }

        single<FieldReserveRepository> {
            FieldReserveRepositoryImpl(remoteDataSource = get())
        }


    }