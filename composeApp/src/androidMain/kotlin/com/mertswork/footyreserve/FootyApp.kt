package com.mertswork.footyreserve

import android.app.Application
import com.mertswork.footyreserve.di.initKoin
import com.mertswork.footyreserve.utils.ContextProvider
import org.koin.android.ext.koin.androidContext

class FootyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@FootyApp)
            ContextProvider.initialize(this@FootyApp)
        }
    }
}